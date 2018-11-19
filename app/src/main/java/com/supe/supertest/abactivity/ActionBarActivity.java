package com.supe.supertest.abactivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author yinzh
 * @Date   2018/10/17 13:44
 * @Description
 */
public class ActionBarActivity extends QsABActivity{

    private static String[] strArr = new String[]{"SuperMax","Han","NaTie"};
    private List<String> list = Arrays.asList(strArr);
    @Bind(R.id.indicator)
    MagicIndicator indicator;

    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

    @Override
    public int layoutId() {
        return R.layout.activity_magic_indicator;
    }

    @Override
    public boolean onKeyDown(KeyEvent keyEvent, int i) {
        return false;
    }

    @Override
    public void initData(Bundle bundle) {
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
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
