package com.supe.supertest.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.indicator.MagicIndicator;
import com.supermax.base.common.widget.indicator.ViewPagerIndicatorHelper;
import com.supermax.base.common.widget.indicator.indicator.IPagerIndicator;
import com.supermax.base.common.widget.indicator.indicator.LinePagerIndicator;
import com.supermax.base.common.widget.indicator.navigator.CommonNavigator;
import com.supermax.base.common.widget.indicator.navigator.adapter.CommonNavigatorAdapter;
import com.supermax.base.common.widget.indicator.navigator.impl.IPagerTitleView;
import com.supermax.base.common.widget.indicator.navigator.view.SimplePagerTitleView;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.common.widget.viewpager.ViewPagerHelper;
import com.supermax.base.mvp.QsActivity;
/**
 * @Author yinzh
 * @Date 2018/11/15 10:23
 * @Description
 */
public class MagicIndicatorActivity extends QsActivity {

    @Bind(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @Bind(R.id.pager)
    ViewPager pager;

    private String[] arrStr = new String[]{"Super", "Max", "Man", "SuperMax", "SuperText"};
    private ViewPagerAdaper adaper = new ViewPagerAdaper(arrStr);
    @Override
    public int layoutId() {
        return R.layout.activity_magic_indicator;
    }

    @Override
    public void initData(Bundle bundle) {

        pager.setAdapter(adaper);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return arrStr.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(arrStr[i]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
                simplePagerTitleView.setSelectedColor(R.color.color_rose_red);
                simplePagerTitleView.setTextSize(12);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QsToast.show(arrStr[i]);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });

        magic_indicator.setNavigator(commonNavigator);
        ViewPagerIndicatorHelper.bind(magic_indicator, pager);
    }
}
