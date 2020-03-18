package com.supe.supertest.homework;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.collapsing.demo.dialog.HomeworkCardFragment;
import com.supe.supertest.homework.adapter.QuestionPagerAdapter;
import com.supe.supertest.homework.dialog.SureNoTitleDialog;
import com.supe.supertest.homework.dialog.SureTitleDialog;
import com.supe.supertest.homework.module.HomeworkAnswerBean;
import com.supe.supertest.homework.module.HomeworkQuestionTypeBean;
import com.supe.supertest.homework.event.MessageEvent;
import com.supe.supertest.homework.module.HomeworkQuestionBean;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsABActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author yinzh
 * @Date 2019/8/12 21:10
 * @Description 题库demo 入口
 */
public class QuestionActivity extends QsABActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.tv_title)
    TextView tv_title;

    // 题卡
    @Bind(R.id.ll_card)
    LinearLayout ll_card;

    //交卷
    @Bind(R.id.ll_finish)
    LinearLayout ll_finish;


    //当前题目下标
    protected int mCurrentIndex;


    private QuestionPagerAdapter mAdapter;
    //分类答案
    public ArrayList<HomeworkAnswerBean> answerList = new ArrayList<>();
    public ArrayList<HomeworkQuestionBean> mQuestionList = new ArrayList<>();
    private SureTitleDialog mTitleDialog;
    private SureNoTitleDialog mSubmitDialog;


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

        // 单选题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas = new ArrayList<>();
            metas.add("宪法");
            metas.add("法律");
            metas.add("行政法规");
            metas.add("行政规章");
            HomeworkQuestionBean homeworkQuestionBean1 = new HomeworkQuestionBean();
            homeworkQuestionBean1.type = HomeworkQuestionTypeBean.single_choice;
            homeworkQuestionBean1.setMetas(metas);
            homeworkQuestionBean1.setStem("1、下列发的形式中，由全国人民代表大会及其常务委员会经一定立法程序制定颁布，" +
                    "调整国家、社会和公民生活中基本社会关系的是（）。");
            mQuestionList.add(homeworkQuestionBean1);
        }


        // 填空题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas1 = new ArrayList<>();
            metas1.add("应当按照多数仲裁员的意见作出裁决");
            metas1.add("应当有仲裁庭达成一致意见作出在裁决");
            metas1.add("按照首席仲裁员的意见作出裁决");
            metas1.add("提请仲裁委员会作出裁决");
            HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
            homeworkQuestionBean2.type = HomeworkQuestionTypeBean.fill;
            homeworkQuestionBean2.setMetas(metas1);
            homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                    "裁决的下列表述中，符合法律规定的是（）。");

            mQuestionList.add(homeworkQuestionBean2);
        }


        //判断题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas1 = new ArrayList<>();
            metas1.add("应当按照多数仲裁员的意见作出裁决");
            metas1.add("应当有仲裁庭达成一致意见作出在裁决");
            metas1.add("按照首席仲裁员的意见作出裁决");
            metas1.add("提请仲裁委员会作出裁决");
            HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
            homeworkQuestionBean2.type = HomeworkQuestionTypeBean.determine;
            homeworkQuestionBean2.setMetas(metas1);
            homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                    "裁决的下列表述中，符合法律规定的是（）。");

            mQuestionList.add(homeworkQuestionBean2);
        }


        // 问答题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas1 = new ArrayList<>();
            metas1.add("应当按照多数仲裁员的意见作出裁决");
            metas1.add("应当有仲裁庭达成一致意见作出在裁决");
            metas1.add("按照首席仲裁员的意见作出裁决");
            metas1.add("提请仲裁委员会作出裁决");
            HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
            homeworkQuestionBean2.type = HomeworkQuestionTypeBean.essay;
            homeworkQuestionBean2.setMetas(metas1);
            homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                    "裁决的下列表述中，符合法律规定的是（）。");

            mQuestionList.add(homeworkQuestionBean2);
        }

        // 材料题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas1 = new ArrayList<>();
            metas1.add("应当按照多数仲裁员的意见作出裁决");
            metas1.add("应当有仲裁庭达成一致意见作出在裁决");
            metas1.add("按照首席仲裁员的意见作出裁决");
            metas1.add("提请仲裁委员会作出裁决");
            HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
            homeworkQuestionBean2.type = HomeworkQuestionTypeBean.material;
            homeworkQuestionBean2.setMetas(metas1);
            homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                    "裁决的下列表述中，符合法律规定的是（）。");

            // 材料题子题
            ArrayList<HomeworkQuestionBean> items = new ArrayList<>();
            HomeworkQuestionBean homeworkQuestionBean3 = new HomeworkQuestionBean();
            homeworkQuestionBean3.type = HomeworkQuestionTypeBean.material;
            homeworkQuestionBean3.setMetas(metas1);
            items.add(homeworkQuestionBean3);
            homeworkQuestionBean2.setItems(items);

            mQuestionList.add(homeworkQuestionBean2);
        }


        // 多选题
        for (int i = 0; i < 2; i++) {
            ArrayList<String> metas1 = new ArrayList<>();
            metas1.add("应当按照多数仲裁员的意见作出裁决");
            metas1.add("应当有仲裁庭达成一致意见作出在裁决");
            metas1.add("按照首席仲裁员的意见作出裁决");
            metas1.add("提请仲裁委员会作出裁决");
            HomeworkQuestionBean homeworkQuestionBean2 = new HomeworkQuestionBean();
            homeworkQuestionBean2.type = HomeworkQuestionTypeBean.choice;
            homeworkQuestionBean2.setMetas(metas1);
            homeworkQuestionBean2.setStem("2、甲、乙因合同纠纷申请仲裁，仲裁庭对案件裁决时两位仲裁员支持甲方的请求，但首席仲裁员支持乙的请求，关于该案件仲裁" +
                    "裁决的下列表述中，符合法律规定的是（）。");

            mQuestionList.add(homeworkQuestionBean2);
        }


        setStartExamData(mQuestionList);

        initListener();


        initDialog();

    }


    private void initDialog() {
        mTitleDialog = new SureTitleDialog()
                .setTitle("提交试卷")
                .setMessage("你当前还有题目未做完，确定交卷")
                .setAllBtnText("坚持考完", "交卷")
                .setClickListener(new SureTitleDialog.CallBack() {
                    @Override
                    public void onRightClick(SureTitleDialog dialog, View v) {
                        dialog.dismissDialog(getSupportFragmentManager());

                    }

                    @Override
                    public void onLeftClick(SureTitleDialog dialog, View v) {
                        dialog.dismissDialog(getSupportFragmentManager());
                        QsToast.show("交卷");
                    }
                });


        mSubmitDialog = new SureNoTitleDialog()
                .setMessage("确认交卷")
                .setAllBtnText("确认交卷", "检查一下")
                .setClickListener(new SureNoTitleDialog.CallBack() {
                    @Override
                    public void onRightClick(SureNoTitleDialog dialog, View v) {
                        dialog.dismissDialog(getSupportFragmentManager());
                        QsToast.show("交卷");
                    }

                    @Override
                    public void onLeftClick(SureNoTitleDialog dialog, View v) {
                        dialog.dismissDialog(getSupportFragmentManager());
                    }
                });
    }


    private void setStartExamData(ArrayList<HomeworkQuestionBean> results) {
        mAdapter = new QuestionPagerAdapter(getContext(), results);
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


    @OnClick({R.id.ll_back, R.id.ll_card})
    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_card: //题卡
                if (mQuestionList.size() == 0) return;

                showQuestionCard();
                break;
            default:
                break;

        }
    }

    private void showQuestionCard() {
        HomeworkCardFragment fragment = new HomeworkCardFragment();
        Bundle params = new Bundle();
        params.putBoolean("isShowSubmit", true);
        fragment.setArguments(params);
        fragment.show(getSupportFragmentManager(), "dialog");
    }

    //显示交卷弹窗
    public void showFinishDialog() {
        loading();


        for (int i = 0; i < mQuestionList.size(); i++) {
            HomeworkAnswerBean answer = answerList.get(i);
            if (!answer.isAnswer) {
                loadingClose();
                mTitleDialog.showDialog(getSupportFragmentManager());
                return;
            }
        }

        loadingClose();

        mSubmitDialog.showDialog(getSupportFragmentManager());
    }

    @Subscribe
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getType()) {
            case MessageEvent.EXAM_CHANGE_ANSWER:

                break;
            case MessageEvent.EXAM_NEXT_QUESTION:
                //自动下一题
                if (viewPager.getCurrentItem() == mQuestionList.size() - 1) {
                    QsToast.show("已经是最后一题");
                    return;
                }

                if (viewPager.getCurrentItem() < mQuestionList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    EventBus.getDefault().cancelEventDelivery(messageEvent);
                }
                break;
        }
    }

    @Override
    public boolean isOpenEventBus() {
        return true;
    }
}
