package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.home.AKeFragment;
import com.supe.supertest.home.ShouYueFragment;
import com.supe.supertest.home.YaDianNaFragment;
import com.supe.supertest.home.ZhuGeFragment;
import com.supe.supertest.realm.TestModel;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.fragment.QsViewPagerFragment;
import com.supermax.base.mvp.model.QsModelPager;


/**
 * @Author yinzh
 * @Date 2018/10/17 14:14
 * @Description
 */
public class HomeFragment extends QsViewPagerFragment {

//    @Override
//    public int layoutId() {
//        return R.layout.main_fragment;
//    }

    @Override
    public void initData(Bundle bundle) {


//        Realm realm = Realm.getDefaultInstance();
//
//        TestModel testModel = realm.where(TestModel.class).findFirst();

//        QsToast.show(testModel.getName());


    }

//    @OnClick({R.id.bt_main})
//    public void onViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_main:
//                QsToast.show("首页");
//                break;
//        }
//    }

    @Override
    public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = new ShouYueFragment();
        modelPager1.title = "守约";
        modelPager1.position = 0;
        modelPager1.icon = R.mipmap.icon_classroom_video_tab_select;
        modelPager1.iconDefault = R.mipmap.icon_classroom_video_tab;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = new YaDianNaFragment();
        modelPager2.title = "雅典娜";
        modelPager2.icon = R.mipmap.icon_classroom_question_tab_select;
        modelPager2.iconDefault = R.mipmap.icon_classroom_question_tab;
        modelPager2.position = 1;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new ZhuGeFragment();
        modelPager3.title = "诸葛亮";
        modelPager3.icon = R.mipmap.icon_classroom_live_tab_select;
        modelPager3.iconDefault = R.mipmap.icon_classroom_live_tab;
        modelPager3.position = 2;

        QsModelPager modelPager4 = new QsModelPager();
        modelPager4.fragment = new AKeFragment();
        modelPager4.title = "阿珂";
        modelPager4.icon = R.mipmap.icon_classroom_file_tab_select;
        modelPager4.iconDefault = R.mipmap.icon_classroom_file_tab;
        modelPager4.position = 3;


        return new QsModelPager[]{modelPager1, modelPager2, modelPager3, modelPager4};
    }

    @Override
    public int getTabItemLayout() {
        return R.layout.tab_layout_home;
    }

    @Override
    public void initTab(View view, QsModelPager modelPager) {
        TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
        ImageView iv_tab_selected = (ImageView) view.findViewById(R.id.iv_tab_selected);
        ImageView iv_tab_unselected = (ImageView) view.findViewById(R.id.iv_tab_unselected);
        tv_tab.setText(modelPager.title);
        iv_tab_selected.setImageResource(modelPager.icon);
        iv_tab_unselected.setImageResource(modelPager.iconDefault);

        setTabState(view, modelPager.position == 0);
    }

    private void setTabState(View view, boolean isSelected) {
        if (view != null) {
            TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
            tv_tab.setTextColor(QsHelper.getInstance().getColor(isSelected ? R.color.colorAccent : R.color.color_dark_grey));
            view.findViewById(R.id.iv_tab_selected).setVisibility(isSelected ? View.VISIBLE : View.GONE);
            view.findViewById(R.id.iv_tab_unselected).setVisibility(isSelected ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected(View currentTabItem, View oldTabItem, int position, int oldPosition) {
        super.onPageSelected(currentTabItem, oldTabItem, position, oldPosition);
        setTabState(oldTabItem, false);
        setTabState(currentTabItem, true);
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
    }
}
