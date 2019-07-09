package com.supe.supertest.nestedscrolling;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.supermax.base.common.log.L;

import java.util.Arrays;

/**
 * @Author yinzh
 * @Date 2019/2/26 11:58
 * @Description
 */
public class NestedScrollingChildView extends View implements NestedScrollingChild {

    private static final String TAG= "NestedScrollingChildView";

    private float mLastX;//手指在屏幕上最后的x位置
    private float mLastY;//手指在屏幕上最后的y位置

    private float mDownX;//手指第一次落下时的x位置（忽略）
    private float mDownY;//手指第一次落下时的y位置


    private int[] consumed = new int[2];//消耗的距离
    private int[] offsetInWindow = new int[2];//窗口偏移

    private NestedScrollingChildHelper mScrollingChildHelper;

    public NestedScrollingChildView(Context context) {
        super(context);
    }

    public NestedScrollingChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollingChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {

                mDownX = x;
                mDownY = y;
                mLastX = x;
                mLastY = y;
                //当开始滑动的时候，告诉父view
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL | ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                /*
                mDownY:293.0
                mDownX:215.0
                 */

                int dy = (int) (y - mDownY);
                int dx = (int) (x - mDownX);

                //分发触屏事件给父类处理
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)) {
                    //减掉父类消耗的距离
                    dx -= consumed[0];
                    dy -= consumed[1];
                    L.d(TAG, Arrays.toString(offsetInWindow));
                }

                offsetTopAndBottom(dy);
                offsetLeftAndRight(dx);

                break;
            }

            case MotionEvent.ACTION_UP: {
                stopNestedScroll();
                break;
            }
        }
        mLastX = x;
        mLastY = y;
        return true;
    }


    /**
     * 设置是否允许嵌套滑动
     *
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    /**
     * 是否允许嵌套滑动
     *
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return mScrollingChildHelper.isNestedScrollingEnabled();
    }

    /**
     * 告诉开始嵌套滑动流程，调用这个函数的时候会去找嵌套滑动的父控件。如果找到了父控件并且父控件说可以滑动就返回true，否则返回false
     * (一般ACTION_DOWN里面调用)
     *
     * @param axes:支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return true 父控件说可以滑动，false 父控件说不可以滑动
     */
    @Override
    public boolean startNestedScroll(int axes) {
        return mScrollingChildHelper.startNestedScroll(axes);
    }


    /**
     * 停止嵌套滑动流程(一般ACTION_UP里面调用)
     */
    @Override
    public void stopNestedScroll() {
        mScrollingChildHelper.stopNestedScroll();
    }


    /**
     * 是否有嵌套滑动对应的父控件
     *
     * @return
     */
    @Override
    public boolean hasNestedScrollingParent() {
        return mScrollingChildHelper.hasNestedScrollingParent();
    }

    /**
     * 在嵌套滑动的子View滑动之后再调用该函数向父View汇报滑动情况。
     *
     * @param dxConsumed     子View水平方向滑动的距离
     * @param dyConsumed     子View垂直方向滑动的距离
     * @param dxUnconsumed   子View水平方向没有滑动的距离
     * @param dyUnconsumed   子View垂直方向没有滑动的距离
     * @param offsetInWindow 出参 如果父View滑动导致子View的窗口发生了变化（子View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，才能保证下一次的touch事件的计算是正确的。
     * @return true 如果父View有滑动做了相应的处理, false 父View没有滑动.
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    /**
     * 在嵌套滑动的子View滑动之前，告诉父View滑动的距离，让父View做相应的处理。
     *
     * @param dx             告诉父View水平方向需要滑动的距离
     * @param dy             告诉父View垂直方向需要滑动的距离
     * @param consumed       出参. 如果不是null, 则告诉子View父View滑动的情况， consumed[0]父View告诉子View水平方向滑动的距离(dx)
     *                       consumed[1]父View告诉子View垂直方向滑动的距离(dy).
     * @param offsetInWindow 可选 length=2的数组，如果父View滑动导致子View的窗口发生了变化（子View的位置发生了变化）
     *                       该参数返回x(offsetInWindow[0]) y(offsetInWindow[1])方向的变化
     *                       如果你记录了手指最后的位置，需要根据参数offsetInWindow计算偏移量，才能保证下一次的touch事件的计算是正确的。
     * @return true 父View滑动了，false 父View没有滑动。
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    /**
     * 在嵌套滑动的子View fling之后再调用该函数向父View汇报fling情况。
     *
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed  true 如果子View fling了, false 如果子View没有fling
     * @return true 如果父View fling了
     */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    /**
     * 在嵌套滑动的子View fling之前告诉父View fling的情况。
     *
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return 如果父View fling了
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScrollingChildHelper.onDetachedFromWindow();
    }


}
