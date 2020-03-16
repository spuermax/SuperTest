package com.supe.supertest.homework.widget;

import android.content.Context;
import android.util.AttributeSet;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2020/3/16 14:11
 * @Description 作业容器
 */
public class HomeWorkViewPager extends ViewPager {

    public HomeWorkViewPager(@NonNull Context context) {
        super(context);
        initView();
    }

    public HomeWorkViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        try {
            Class clazz = Class.forName("android.support.v4.view.ViewPager");
            Field f = clazz.getDeclaredField("mScroller");
            FixedViewPagerSpeedScroller fixedSpeedScroller =
                    new FixedViewPagerSpeedScroller(getContext(), new LinearOutSlowInInterpolator());
            fixedSpeedScroller.setmDuration(300);
            f.setAccessible(true);
            f.set(this, fixedSpeedScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
