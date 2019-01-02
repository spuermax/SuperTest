package com.supe.supertest.viewpageractivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.supe.supertest.R;
import com.supe.supertest.common.button.FloatLogoMenu;
import com.supe.supertest.common.wdiget.SonnyJackDragView;
import com.supe.supertest.viewpageractivity.fragment.CenterFragment;
import com.supe.supertest.viewpageractivity.fragment.HomeFragment;
import com.supe.supertest.viewpageractivity.fragment.TestFragment;
import com.supe.supertest.viewpageractivity.fragment.UserFragment;
import com.supermax.base.common.log.L;
import com.supermax.base.common.model.QsModel;
import com.supermax.base.mvp.QsViewPagerActivity;
import com.supermax.base.mvp.model.QsModelPager;

/**
 * @Author yinzh
 * @Date   2018/10/17 14:09
 * @Description
 */
public class HomeActivity extends QsViewPagerActivity{


    @Override
    public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = new HomeFragment();
        modelPager1.title = "首页";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = new TestFragment();
        modelPager2.title = "测试";
        modelPager2.position = 1;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new CenterFragment();
        modelPager3.title = "中间";
        modelPager3.position = 2;

        QsModelPager modelPager4 = new QsModelPager();
        modelPager4.fragment = new UserFragment();
        modelPager4.title = "用户";
        modelPager4.position = 3;

        return new QsModelPager[]{modelPager1, modelPager2, modelPager3,modelPager4};
    }

    /**
     * 可自定义TAB 类型
     * @return
     */
    @Override
    public int getTabItemLayout() {
        return 0;
    }

    @Override
    public void initTab(View view, QsModelPager qsModelPager) {

    }

    @Override
    public void initData(Bundle bundle) {


    }

}
