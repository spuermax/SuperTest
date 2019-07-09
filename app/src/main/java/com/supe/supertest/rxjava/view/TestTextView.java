package com.supe.supertest.rxjava.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.supermax.base.common.log.L;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @Author yinzh
 * @Date 2019/2/23 10:21
 * @Description
 */
public class TestTextView extends AppCompatTextView {
    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.i("RecyclerView","我是子TestTextView   dispatchTouchEvent == " +super.dispatchTouchEvent(event));

        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.i("RecyclerView","我是子TestTextView   onTouchEvent==" +  super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
