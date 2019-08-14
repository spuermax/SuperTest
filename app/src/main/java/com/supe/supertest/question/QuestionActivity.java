package com.supe.supertest.question;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.adapter.QuestionPagerAdapter;
import com.supe.supertest.question.module.HomeworkQuestionBean;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsABActivity;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2019/8/12 21:10
 * @Description
 */
public class QuestionActivity extends QsABActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_card)
    LinearLayout ll_card;

    @Bind(R.id.ll_finish)
    LinearLayout ll_finish;


    //当前题目下标
    protected int mCurrentIndex;


    private QuestionPagerAdapter mAdapter;


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
        tv_title.setText("第一章（练习二）");

        ArrayList<String> metas = new ArrayList<>();
        metas.add("宪法");
        metas.add("法律");
        metas.add("行政法规");
        metas.add("行政规章");
        HomeworkQuestionBean homeworkQuestionBean1 = new HomeworkQuestionBean();
        homeworkQuestionBean1.type = HomeworkQuestionBean.HomeworkQuestionTypeBean.choice;
        homeworkQuestionBean1.setMetas(metas);
        homeworkQuestionBean1.setStem("1、下列发的形式中，由全国人民代表大会及其常务委员会经一定立法程序制定颁布，" +
                "调整国家、社会和公民生活中基本社会关系的是（）。");


        ArrayList<String> metas1 = new ArrayList<>();
        metas1.add("应当按照多数仲裁员的意见作出裁决");
        metas1.add("应当有仲裁庭达成一致意见作出在裁决");
        metas1.add("按照首席仲裁员的意见作出裁决");
        metas1.add("提请仲裁委员会作出裁决");
        HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
        homeworkQuestionBean2.type = HomeworkQuestionBean.HomeworkQuestionTypeBean.choice;
        homeworkQuestionBean2.setMetas(metas1);
        homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                "裁决的下列表述中，符合法律规定的是（）。");



        ArrayList<HomeworkQuestionBean> homeworkQuestionBeans = new ArrayList<>();
        homeworkQuestionBeans.add(homeworkQuestionBean1);
        homeworkQuestionBeans.add(homeworkQuestionBean2);


        setStartExamData(homeworkQuestionBeans);

        initListener();


    }


    private void setStartExamData(ArrayList<HomeworkQuestionBean> results) {
        mAdapter = new QuestionPagerAdapter(getContext(),results);
        viewPager.setAdapter(mAdapter);

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private boolean flag;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        flag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        //判断是不是最后一页，同是是不是拖的状态
                        if (viewPager.getCurrentItem() == mAdapter.getCount() - 1 && !flag) {
                            QsToast.show("已经是最后一题");
                        }
                        flag = true;
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @OnClick({R.id.ll_back})
    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

        }
    }
}
