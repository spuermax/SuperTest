package com.supe.supertest.paint;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.paint.module.PaintValueBean;
import com.supe.supertest.paint.view.PaintScrollView;
import com.supe.supertest.paint.view.PaintView;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;

/**
 * @Author yinzh
 * @Date 2019/9/10 19:43
 * @Description
 */
public class PaintActivity extends QsActivity {

    @Bind(R.id.paintView)
    PaintView paintView;

    @Bind(R.id.ll_layout)
    RelativeLayout linearLayout;

    @Override
    public int layoutId() {
        return R.layout.paint_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        paintView.setOnClickValue(paintValueBean -> {
            QsToast.show(paintValueBean.startX+"");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ScreenUtils.dpToPx(200), ScreenUtils.dpToPx(100));
            PaintScrollView paintScrollView = new PaintScrollView(getContext());
            layoutParams.leftMargin = (int) paintValueBean.startX;
            layoutParams.topMargin = (int) paintValueBean.startY;
            paintScrollView.setPaintValueBean(paintValueBean);
            paintScrollView.setLayoutParams(layoutParams);
            linearLayout.addView(paintScrollView);
        });
    }
}
