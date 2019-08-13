package com.supe.supertest.question;

import android.os.Bundle;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsABActivity;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2019/8/12 21:10
 * @Description
 */
public class QuestionActivity extends QsABActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

    @Override
    public int layoutId() {
        return R.layout.activity_question;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
