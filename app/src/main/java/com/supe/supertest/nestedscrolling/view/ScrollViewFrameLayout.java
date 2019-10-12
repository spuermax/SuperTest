package com.supe.supertest.nestedscrolling.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.supe.supertest.common.utils.ScreenUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author yinzh
 * @Date 2019/9/1 16:27
 * @Description
 */
public class ScrollViewFrameLayout extends FrameLayout {
    private float mOriginalRawX;
    private float mOriginalRawY;
    private float mOriginalX;
    private float mOriginalY;

    private float firstX;
    private float firstY;

    private float lastX;
    private float lastY;

    private boolean isLast;
    private boolean isFirst;

    private boolean isTouch;


    private int mScreenWidth;
    private int mScreenHeight;
    //上下边距
    public static final int MARGIN_EDGE_V = 5;

    public ScrollViewFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollViewFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollViewFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScreenHeight = getScreenHeight();
        mScreenWidth = getScreenWidth();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        mScreenHeight = getScreenHeight();
        mScreenWidth = getScreenWidth();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!isFirst){
                    firstX = getX();
                    firstY = getY();
                }

                if (isLast) {
                    lastX = getX();
                    lastY = getY();
                    isLast = false;
                }
                mOriginalX = getX();
                mOriginalY = getY();
                mOriginalRawX = event.getRawX();
                mOriginalRawY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                updateViewPosition(event);
                isFirst  = true;
                break;
        }

        return true;
    }


    private void updateViewPosition(MotionEvent event) {
        // 限制不可超出屏幕宽度（左）
        float desX = mOriginalX + event.getRawX() - mOriginalRawX;
        if (desX <= 0) {
            desX = 0;
        }

        // 限制不可超出屏幕宽度（右）
        if (desX + getWidth() >= mScreenWidth) {
            desX = mScreenWidth - getWidth();
        }

        setX(desX);

        // 限制不可超出屏幕高度
        float desY = mOriginalY + event.getRawY() - mOriginalRawY;
        if (desY <= ScreenUtils.dpToPx(MARGIN_EDGE_V)) {
            desY = ScreenUtils.dpToPx(MARGIN_EDGE_V);
        }
        if (desY > mScreenHeight - getHeight() - ScreenUtils.dpToPx(MARGIN_EDGE_V)) {
            desY = mScreenHeight - getHeight() - ScreenUtils.dpToPx(MARGIN_EDGE_V);
        }
        setY(desY);
    }


    /**
     * 得到屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 得到屏幕高度
     *
     * @return
     */
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void setScreenFull(boolean isScreen) {
        if (isTouch) {
            if (isScreen) {
                setX(firstX);
                setY(firstY);
                isLast = true;
                isTouch = false;
            } else {
                isLast = false;
                setX(lastX);
                setY(lastY);
                isTouch = false;
            }
        }
    }

}
