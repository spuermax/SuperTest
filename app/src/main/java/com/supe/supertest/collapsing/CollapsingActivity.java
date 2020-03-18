package com.supe.supertest.collapsing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.home.YaDianNaFragment;
import com.supermax.base.common.utils.CommonUtils;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2020/3/17 11:09
 * @Description 悬浮效果
 */
public class CollapsingActivity extends QsActivity {

//    @Bind(R.id.tv_content)
//    TextView tv_content;

    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.viewpager)
    ViewPager viewPager;


    private static final String[] CHANNELS = new String[]{"直播", "视频", "题库", "资料"};
    private static final int[] ICON = new int[]{R.mipmap.icon_classroom_live_tab, R.mipmap.icon_classroom_video_tab, R.mipmap.icon_classroom_question_tab, R.mipmap.icon_classroom_file_tab};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private static final int[] ICON_SELECT = new int[]{R.mipmap.icon_classroom_live_tab_select, R.mipmap.icon_classroom_video_tab_select, R.mipmap.icon_classroom_question_tab_select, R.mipmap.icon_classroom_file_tab_select};


    @Override
    public int layoutId() {
        return R.layout.activity_collapsing;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initViewPager();
        initIndicator();

    }

    private List<Fragment> fragmentList = new ArrayList<>();

    private void initViewPager(){

        CollapsingFragment yaDianNaFragment = new CollapsingFragment();
        CollapsingFragment yaDianNaFragment1 = new CollapsingFragment();
        CollapsingFragment yaDianNaFragment2 = new CollapsingFragment();
        CollapsingFragment yaDianNaFragment3 = new CollapsingFragment();

        fragmentList.add(yaDianNaFragment);
        fragmentList.add(yaDianNaFragment1);
        fragmentList.add(yaDianNaFragment2);
        fragmentList.add(yaDianNaFragment3);

       CollapsingTestAdapter collapsingTestAdapter = new CollapsingTestAdapter(getSupportFragmentManager(), fragmentList);

        viewPager.setAdapter(collapsingTestAdapter);
    }


    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView titleImg = (ImageView) customLayout.findViewById(R.id.iv_icon);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.tv_title);

                titleImg.setImageResource(ICON[index]);
                titleText.setText(mDataList.get(index));
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {

                        titleText.setTextColor(getResources().getColor(R.color.color_323C32));
                        titleText.setTypeface(Typeface.DEFAULT_BOLD);
                        titleText.setTextSize(20);
                        titleImg.setImageResource(ICON_SELECT[index]);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.color_898D89));
                        titleText.setTypeface(Typeface.DEFAULT);
                        titleText.setTextSize(13);
                        Log.i("AAAAAAAAAA", "来了吗 wocoa  你这是第几次了3===" + index);
                        titleImg.setImageResource(ICON[index]);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setRoundRadius(CommonUtils.dip2px(context, 10));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(CommonUtils.dip2px(context, 30));
                indicator.setColors(Color.parseColor("#FF8800"));
                return indicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


    public class CollapsingTestAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList ;


        public CollapsingTestAdapter(FragmentManager fm , List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }


        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }


        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

    }
}
