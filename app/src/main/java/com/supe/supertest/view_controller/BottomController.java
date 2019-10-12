package com.supe.supertest.view_controller;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @Author yinzh
 * @Date 2019/9/24 14:19
 * @Description
 */
public class BottomController extends ValueAnimatorController {

    public BottomController(View view) {
        super(view);
    }

    @Override
    protected int getShowTarget() {
        return 0;
    }

    @Override
    protected int getHideTarget() {
        return view.getHeight();
    }

    @Override
    protected void onShiftChanged(int shift) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, -shift);
        view.setLayoutParams(params);
    }
}