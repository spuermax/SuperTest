package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.View;

import com.supe.supertest.R;
import com.supe.supertest.home.AKeFragment;
import com.supe.supertest.home.ShouYueFragment;
import com.supe.supertest.home.YaDianNaFragment;
import com.supe.supertest.home.ZhuGeFragment;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.fragment.QsFragment;
import com.supermax.base.mvp.fragment.QsViewPagerFragment;
import com.supermax.base.mvp.model.QsModelPager;

/*
 * @Author yinzh
 * @Date   2018/10/17 14:14
 * @Description
 */
public class HomeFragment extends QsViewPagerFragment {

//    @Override
//    public int layoutId() {
//        return R.layout.main_fragment;
//    }

    @Override
    public void initData(Bundle bundle) {

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

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = new YaDianNaFragment();
        modelPager2.title = "雅典娜";
        modelPager2.position = 1;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new ZhuGeFragment();
        modelPager3.title = "诸葛亮";
        modelPager3.position = 2;

        QsModelPager modelPager4 = new QsModelPager();
        modelPager4.fragment = new AKeFragment();
        modelPager4.title = "阿珂";
        modelPager4.position = 3;


        return new QsModelPager[]{modelPager1, modelPager2, modelPager3, modelPager4};
    }


    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
    }
}
