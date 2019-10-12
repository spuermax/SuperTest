package com.supe.supertest.test;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Description:
 * @Author:  wangzs
 * @Date:    2018-09-02 15:57
 * @Version:
 * @Email    wangzs@yuntongxun.com
 */
public class RaiseHandsFloatWindow extends AppCompatImageView {
    private int mTouchSlop; // 系统认为的最小滑动距离
    private int screenWidth;
    private int screenHeight;

    private MyCountDownTimer countDownTimer;
    private long millisInFuture = 2500;
    private long countDownInterval = 500;

    private float toAlpha = 0.3f;

    // 可以移动的范围
    private int minLeftMargin;
    private int maxLeftMargin;
    private int minTopMargin;
    private int maxTopMargin;

    public RaiseHandsFloatWindow(Context context) {
        this(context, null);
    }

    public RaiseHandsFloatWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RaiseHandsFloatWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        inflate(context, R.layout.plugin_raise_hands_view,null);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        // metrics.heightPixels(屏幕高度) = 屏幕空白处高度 + 顶部状态栏(不包括底部导航栏)
        screenHeight = metrics.heightPixels - getStatusHeight();

//        countDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
//        countDownTimer.start();
    }

    float lastX;
    float lastY;
    float rawX;
    float rawY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currX = (int) event.getRawX();
        float currY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawX = lastX = currX;
                rawY = lastY = currY;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = currX - lastX;
                float offsetY = currY - lastY;
                moveView(offsetX, offsetY);
                lastX = currX;
                lastY = currY;
                break;
            case MotionEvent.ACTION_UP:
//                countDownTimer.start();
                // 处理点击事件和移动事件
                if (Math.abs(lastX - rawX) < mTouchSlop &&
                        Math.abs(lastY - rawY) < mTouchSlop) {
                    performClick();
                }

                // 停止移动时，使 View 贴边.
                LinearLayout.MarginLayoutParams lp = (LinearLayout.MarginLayoutParams) getLayoutParams();
                int fromLeftMargin = lp.leftMargin;
                int fromTopMargin = lp.topMargin;
//
//                if (ConferenceService.getInstance().orientation_landscape){
//                    if ((getTop() + getMeasuredHeight() / 2) <= screenHeight / 2) {
//                        lp.topMargin = 50;
//                    } else {
//                        lp.topMargin = screenHeight;
//                    }
//                    ValueAnimator valueAnimator = ValueAnimator.ofInt(fromTopMargin,  lp.topMargin);
//                    valueAnimator.setDuration(300);
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            int animatedValue = (int) animation.getAnimatedValue();
//                            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) RaiseHandsFloatWindow.this.getLayoutParams();
//                            lp.topMargin = animatedValue;
//                            RaiseHandsFloatWindow.this.requestLayout();
//
//                        }
//                    });
//                    valueAnimator.start();
//                }else {
//                    if ((getLeft() + getMeasuredWidth() / 2) <= screenWidth / 2) {
//                        lp.leftMargin = minLeftMargin;
//                    } else {
//                        lp.leftMargin = maxLeftMargin;
//                    }
//                    ValueAnimator valueAnimator = ValueAnimator.ofInt(fromLeftMargin,  lp.leftMargin);
//                    valueAnimator.setDuration(300);
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            int animatedValue = (int) animation.getAnimatedValue();
//                            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) RaiseHandsFloatWindow.this.getLayoutParams();
//                            lp.leftMargin = animatedValue;
//                            RaiseHandsFloatWindow.this.requestLayout();
//                        }
//                    });
//                    valueAnimator.start();
//                }
                break;
        }

        return true;
    }

    private void moveView(float offsetX, float offsetY) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        float left = params.leftMargin + offsetX;
        float top = params.topMargin + offsetY;

        left = left <= minLeftMargin ? minLeftMargin : left;
        left = left >= maxLeftMargin ? maxLeftMargin : left;
        top = top <= minTopMargin ? minTopMargin : top;
        top = top >= maxTopMargin ? maxTopMargin : top;

        params.leftMargin = (int) left;
        params.topMargin = (int) top;
        setLayoutParams(params);
        requestLayout();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        minLeftMargin = 0;
        maxLeftMargin = screenWidth - getMeasuredWidth();
        minTopMargin = getStatusHeight();
//        maxTopMargin = screenHeight -DensityUtil.dip2px(50);
    }

    private int getStatusHeight() {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 使 View 能够执行属性动画的 包装类.
     */
    class Wrapper {
        private View mTarget;

        public Wrapper(View view) {
            mTarget = view;
        }

        public int getLeftMargin() {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTarget.getLayoutParams();
            return lp.leftMargin;
        }

        public void setLeftMargin(int leftMargin) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTarget.getLayoutParams();
            lp.leftMargin = leftMargin;
            mTarget.requestLayout();
        }

        public int getTopMargin() {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTarget.getLayoutParams();
            return lp.topMargin;
        }

        public void setTopMargin(int topMargin) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTarget.getLayoutParams();
            lp.topMargin = topMargin;
            mTarget.requestLayout();
        }
    }

    class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    倒计时时间:
         *                          调用 start() 方法开始倒计时，经过 millisInFuture 秒时间(倒计时结束),
         *                          倒计时结束后调用 onFinish() 方法.
         * @param countDownInterval 在倒计时(millisInFuture)这段时间里,每隔 countDownInterval 段时间执行一次 onTick() 方法.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {}

        @Override
        public void onFinish() {
            setAlpha(toAlpha);
        }
    }
}