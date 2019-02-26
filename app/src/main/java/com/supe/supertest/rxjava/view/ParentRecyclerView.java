package com.supe.supertest.rxjava.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.supermax.base.common.log.L;

/**
 * @Author yinzh
 * @Date 2019/2/23 10:07
 * @Description
 */
public class ParentRecyclerView extends RecyclerView{

    public ParentRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.i("RecyclerView","我是父RecyclerView   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        L.i("RecyclerView","我是父RecyclerView   onInterceptTouchEvent==" + super.onInterceptTouchEvent(e));
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        L.i("RecyclerView","我是父RecyclerView   onTouchEvent===" +  super.onTouchEvent(e));
        return super.onTouchEvent(e);
    }

}
