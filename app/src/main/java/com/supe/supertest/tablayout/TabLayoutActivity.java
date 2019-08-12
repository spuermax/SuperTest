package com.supe.supertest.tablayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.supe.supertest.R;
import com.supe.supertest.tablayout.fragment.TabFourFragment;
import com.supe.supertest.tablayout.fragment.TabOneFragment;
import com.supe.supertest.tablayout.fragment.TabThreeFragment;
import com.supe.supertest.tablayout.fragment.TabTwoFragment;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.viewpager.PagerSlidingTabStrip;
import com.supermax.base.mvp.QsActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;

import javax.crypto.spec.RC2ParameterSpec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2019/8/3 17:01
 * @Description
 */
public class TabLayoutActivity extends QsActivity {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tab_layout1)
    XTabLayout xTab_layout;

    @Bind(R.id.tab_layout2)
    PagerSlidingTabStrip tab_layout2;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();



    @Override
    public int layoutId() {
        return R.layout.activity_tablayout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        titles.add("Tab1");
        titles.add("Tab2");
        titles.add("Tab3");
        titles.add("Tab4");

        fragments.add(new TabOneFragment());
        fragments.add(new TabTwoFragment());
        fragments.add(new TabThreeFragment());
        fragments.add(new TabFourFragment());

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextColor(getResources().getColor(R.color.tab_selector));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextColor(getResources().getColor(R.color.black));
                title.setTypeface(Typeface.DEFAULT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.setupWithViewPager(viewPager);
        xTab_layout.setupWithViewPager(viewPager);
        tab_layout2.setViewPager(viewPager);

    }


    private IWXAPI api;

//    private void regToWx() {
//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, "", true);
//
//        // 将应用的appId注册到微信
//        api.registerApp("");
//
//        //建议动态监听微信启动广播进行注册到微信
//        registerReceiver(new BroadcastReceiver() {
//            private Context context;
//            private Intent intent;
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                this.context = context;
//                this.intent = intent;
//
//                // 将该app注册到微信
//                api.registerApp("");
//            }
//        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
//
//    }
}
