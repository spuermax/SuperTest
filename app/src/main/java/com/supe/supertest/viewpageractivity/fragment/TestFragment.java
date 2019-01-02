package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;

import com.supe.supertest.test.MagicalLabelFragment;
import com.supermax.base.mvp.fragment.QsViewPagerFragment;
import com.supermax.base.mvp.model.QsModelPager;

/**
 * @Author yinzh
 * @Date 2019/1/2 17:18
 * @Description
 */
public class TestFragment extends QsViewPagerFragment{

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = new MagicalLabelFragment();
        modelPager1.title = "自定义标签";
        modelPager1.position = 0;
        return new QsModelPager[]{modelPager1};
    }
}
