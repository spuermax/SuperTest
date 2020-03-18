package com.supe.supertest.homework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Author yinzh
 * @Date 2020/3/18 16:55
 * @Description
 */
public class FixHeightGridView extends GridView {

    public FixHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixHeightGridView(Context context) {
        super(context);
    }

    public FixHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
