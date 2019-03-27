package com.supe.supertest.rxjava.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.toast.QsToast;

/**
 * @Author yinzh
 * @Date 2019/2/22 13:49
 * @Description
 */
public class NestRecyclerView extends RecyclerView {

    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private float mLastY = 0;// 记录上次Y位置
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;

    public NestRecyclerView(Context context) {
        this(context, null);
    }

    public NestRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }*/

    float x, y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.i("RecyclerView", "我是子RecyclerView   onTouchEvent===" + super.onTouchEvent(event));
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                L.i("RecyclerView", "我是点击事件DOWN");
                mLastY = event.getY();
                y = event.getY();
                //不允许父View拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getY();
                // 判断是子Recycler还是父Recycler进行事件的消费
                isIntercept(nowY);
                if (isBottomToTop || isTopToBottom) {// 说明子RecyclerView已经到底部  需要父RecyclerView进行事件消费
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = nowY;

                // 判断是当前Recycler进行事件的消费还是子View
//
//                if (nowY > y + 5) {
//                    QsToast.show("我是滑动事件");
//                }
//
//                if (nowY < y - 5) {
//                    QsToast.show("我是滑动事件");
//                }


                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void isIntercept(float nowY) {

        isTopToBottom = false;
        isBottomToTop = false;

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }
        //得到当前界面可见数据的大小
        int visibleItemCount = layoutManager.getChildCount();
        //得到RecyclerView对应所有数据的大小
        int totalItemCount = layoutManager.getItemCount();
        L.d("nestScrolling", "onScrollStateChanged");
        if (visibleItemCount > 0) {
            if (lastVisibleItemPosition == totalItemCount - 1) {
                //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                L.d("nestScrolling", "触底了lastVisibleItemPosition" + lastVisibleItemPosition);
                if (NestRecyclerView.this.canScrollVertically(-1) && nowY < mLastY) {
                    // 不能向上滑动
                    L.d("nestScrolling", "不能向上滑动");
                    isBottomToTop = true;
                } else {
                    L.d("nestScrolling", "向下滑动");
                }
            } else if (firstVisibleItemPosition == 0) {
                //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                L.d("nestScrolling", "触顶了 firstVisibleItemPosition" + firstVisibleItemPosition);
                if (NestRecyclerView.this.canScrollVertically(1) && nowY > mLastY) {
                    // 不能向下滑动
                    L.d("nestScrolling", "不能向下滑动");
                    isTopToBottom = true;
                } else {
                    L.d("nestScrolling", "向上滑动");
                }
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.i("RecyclerView", "我是子RecyclerView   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    boolean intercept;
    float y1, y2;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                Log.i("ACTIVONOO","========"+e.getY());
                y1 = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("ACTIVONOO","-------"+e.getY());
                if (e.getY() > y1 + 5) {
                    intercept = true;
                }

                if (e.getY() < y1 - 5) {
                    intercept = true;
                }

                if (y1 - 5 < e.getY() && e.getY() < y1 + 5) {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;

        }
        L.i("RecyclerView", "我是子RecyclerView   onInterceptTouchEvent==" + intercept);
        return intercept;
    }

}

