package com.supe.supertest.paint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.paint.module.PaintValueBean;

import androidx.annotation.Nullable;

/**
 * @Author yinzh
 * @Date 2019/9/11 14:19
 * @Description
 */
public class PaintScrollView extends View {

    public PaintScrollView(Context context) {
        super(context);
        init();
    }

    public PaintScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(paintValueBean.startX, paintValueBean.startY);
        path.lineTo(paintValueBean.endX, paintValueBean.endY);
//        canvas.drawLine(paintValueBean.startX, paintValueBean.startY, paintValueBean.endX, paintValueBean.endY, paint);
//        canvas.drawColor(Color.TRANSPARENT);
//        canvas.save();
//        canvas.drawPath(path, paint);

        canvas.drawLine(110, 40, 190, 80, paint);// 斜线



//        canvas.drawCircle(5,100,5, paint);
//
        canvas.drawLine(ScreenUtils.pxToDp((int) paintValueBean.startX), ScreenUtils.pxToDp((int) paintValueBean.startY), ScreenUtils.pxToDp((int) paintValueBean.endX), ScreenUtils.pxToDp((int) paintValueBean.endY), paint);
    }

    private Path path;
    private Paint paint;
    private PaintValueBean paintValueBean;

    public void setPaintValueBean(PaintValueBean paintValueBean) {
        this.paintValueBean = paintValueBean;
    }

    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }
}
