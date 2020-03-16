package com.supe.supertest.homework.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @Author yinzh
 * @Date 2020/3/16 14:14
 * @Description 滑动速度
 */
public class FixedViewPagerSpeedScroller extends Scroller {

    public int mDuration=1500;
    public FixedViewPagerSpeedScroller(Context context) {
        super(context);
    }

    public FixedViewPagerSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedViewPagerSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX,startY,dx,dy,mDuration);
    }

    @Override public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        //管你 ViewPager 传来什么时间，我完全不鸟你
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int duration) {
        mDuration = duration;
    }
}
