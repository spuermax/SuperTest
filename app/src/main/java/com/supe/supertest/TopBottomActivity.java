package com.supe.supertest;

import android.os.Bundle;

import com.supe.supertest.home.TopBottomListFragment;
import com.supermax.base.mvp.QsActivity;

/**
 * @Author yinzh
 * @Date 2019/4/3 14:33
 * @Description
 */
public class TopBottomActivity extends QsActivity{
    @Override
    public void initData(Bundle savedInstanceState) {
        commitFragment(new TopBottomListFragment());
    }
}
