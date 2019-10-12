package com.supe.supertest.paint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.supe.supertest.paint.module.PaintValueBean;

/**
 * @Author yinzh
 * @Date 2019/9/10 19:44
 * @Description 画板View
 */
public class PaintView extends View {

    private OnClickValue onClickValue;

    public void setOnClickValue(OnClickValue onClickValue) {
        this.onClickValue = onClickValue;
    }

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path path;
    private Paint paint;

    private void init() {
        path = new Path();
        paint = new Paint();
        paintValueBean = new PaintValueBean();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    float startX;
    float startY;
    float endX;
    float endY;
    float centerX;
    float centerY;

    private PaintValueBean paintValueBean;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                paintValueBean.startX = event.getX();
                paintValueBean.startY = event.getY();
                path.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();
                paintValueBean.endX = event.getX();
                paintValueBean.endY = event.getY();
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    public void reset() {
        onClickValue.onClick(paintValueBean);
        path.reset();
        invalidate();
    }

    public interface OnClickValue {
        void onClick(PaintValueBean paintValueBean);
    }
}
