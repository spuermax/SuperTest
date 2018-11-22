package com.supe.supertest.common.wdiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.supe.supertest.R;

/**
 * @Author yinzh
 * @Date 2018/11/21 15:05
 * @Description
 */
public class MyButton extends AbastractDragFloatActionButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_test_rela_view;//拿到你自己定义的悬浮布局
    }

    @Override
    public void renderView(View view) {
        //初始化那些布局
    }
}