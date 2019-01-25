package com.supe.supertest.abactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.indicator.MagicIndicator;
import com.supermax.base.common.widget.indicator.indicator.IPagerIndicator;
import com.supermax.base.common.widget.indicator.navigator.CommonNavigator;
import com.supermax.base.common.widget.indicator.navigator.adapter.CommonNavigatorAdapter;
import com.supermax.base.common.widget.indicator.navigator.impl.IPagerTitleView;
import com.supermax.base.common.widget.indicator.navigator.view.SimplePagerTitleView;
import com.supermax.base.mvp.QsABActivity;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/10/17 13:44
 * @Description
 */
public class ActionBarActivity extends QsActivity {

    private static String[] strArr = new String[]{"SuperMax", "Han", "NaTie"};
    private List<String> list = Arrays.asList(strArr);
    @Bind(R.id.indicator)
    MagicIndicator indicator;

//    @Override
//    public int actionbarLayoutId() {
//        return R.layout.actionbar_title_back;
//    }

    @Override
    public int layoutId() {
        return R.layout.activity_magic_indicator;
    }

    @Override
    public boolean onKeyDown(KeyEvent keyEvent, int i) {
        return false;
    }

    @Override
    public boolean isTransparentStatusBar() {
        return true;
    }

    @Override
    public void initData(Bundle bundle) {

        getNotchParams();

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        //下面图1
//        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;// 导致刘海区域是黑屏
        //下面图2
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;// 全屏
        //下面图3
//        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
        getWindow().setAttributes(lp);


        MagicIndicator indicator = new MagicIndicator(this);
        indicator.setBackgroundColor(getResources().getColor(R.color.text_main_color));
        CommonNavigator navigator = new CommonNavigator(this);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(list.get(i));
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setTextSize(12);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

    }

    @OnClick({R.id.ll_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }


    @TargetApi(28)
    private void getNotchParams() {

        View decorView = getWindow().getDecorView();

        decorView.post(() -> {
            DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();

            Log.e("NotchParams", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
            Log.e("NotchParams", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
            Log.e("NotchParams", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
            Log.e("NotchParams", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

            List<Rect> rects = displayCutout.getBoundingRects();
            if (rects == null || rects.size() == 0) {
                Log.e("NotchParams", "不是刘海屏");
            } else {
                Log.e("NotchParams", "刘海屏数量:" + rects.size());
                for (Rect rect : rects) {
                    Log.e("NotchParams", "刘海屏区域：" + rect);
                }

            }
        });
        decorView.setPadding(0, 126,0,0);

    }
}
