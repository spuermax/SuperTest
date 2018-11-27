package com.supe.supertest.common.wdiget.bookpage.animation;

import android.graphics.Canvas;
import android.view.View;

/**
 * @Author yinzh
 * @Date 2018/11/26 15:59
 * @Description
 */
public class NonePageAnim extends HorizonPageAnim{

    public NonePageAnim(int w, int h, View view, OnPageChangeListener listener) {
        super(w, h, view, listener);
    }

    @Override
    public void drawStatic(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, 0, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, 0, null);
        }
    }

    @Override
    public void drawMove(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, 0, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, 0, null);
        }
    }

    @Override
    public void startAnim() {
    }
}
