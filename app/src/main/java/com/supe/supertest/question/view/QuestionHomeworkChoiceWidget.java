package com.supe.supertest.question.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.adapter.QuestionPagerAdapter;
import com.supe.supertest.question.module.HomeworkQuestionBean;
import com.supermax.base.common.log.L;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/8/13 18:23
 * @Description 单选题
 */
public class QuestionHomeworkChoiceWidget extends BaseHomeworkQuestionWidget implements View.OnClickListener {

    protected RadioGroup radioGroup;


    public QuestionHomeworkChoiceWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkChoiceWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        super.setData(question, index, totalNum);
        invalidateData();
    }

    @Override
    protected void initView(AttributeSet attrs) {

    }

    @Override
    protected void invalidateData() {

        radioGroup = findViewById(R.id.quetion_choice_group);

        ArrayList<String> metas = mChildQuestion.getMetas();

        if (metas != null) {
            int size = metas.size();
            for (int i = 0; i < size; i++) {
                View view = initCheckBox(metas.get(i), i);
                LinearLayout.LayoutParams paramsRb = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                view.setOnClickListener(this);
                radioGroup.addView(view, paramsRb);
            }
        }

        super.invalidateData();


        //显示 正确解析
//        Context context = radioGroup.getContext();
//        if (context instanceof QuestionActivity) {
//            QuestionActivity testpaperActivity = (QuestionActivity) getContext();
//            if (testpaperActivity.mDoType == HOMEWORK_PARSE && mChildQuestion.getResult() != null) {
//                enable(radioGroup, false);
//                mAnalysisVS = this.findViewById(R.id.quetion_choice_analysis);
//                mAnalysisVS.setOnInflateListener(new ViewStub.OnInflateListener() {
//                    @Override
//                    public void onInflate(ViewStub viewStub, View view) {
//                        initResultAnalysis(view);
//                        initQuestionResult(radioGroup);
//                    }
//                });
//                mAnalysisVS.inflate();
//            }
//        }

    }


    /**
     * 初始化选项
     */
    private View initCheckBox(String text, int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homework_question_checkbox, null);
        TextView tvMetaNum = view.findViewById(R.id.tv_meta_num);
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(text);
        tvMetaNum.setText(CHOICE_ANSWER[index]);
        return view;
    }

    @Override
    public void onClick(View view) {
        TextView tvMetaNum = view.findViewById(R.id.tv_meta_num);
        TextView tvContent = view.findViewById(R.id.tv_content);
        if (view.isSelected()) {
            view.setSelected(false);
            tvMetaNum.setSelected(false);
            tvContent.setSelected(false);
        } else {
            view.setSelected(true);
            tvMetaNum.setSelected(true);
            tvContent.setSelected(true);
        }

        sendMsgToTestpaper();
    }


    protected void sendMsgToTestpaper() {

        L.i("BaseQuestionWidget", "事件被触发了");


//        Bundle bundle = new Bundle();
//        bundle.putInt("index", mIndex - 1);
//        bundle.putSerializable("QuestionType", mChildQuestion.getType());
//
//        int count = radioGroup.getChildCount();
//        ArrayList<String> data = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            View view = radioGroup.getChildAt(i);
//            if (view.isSelected()) {
//                data.add(i + "");
//            }
//        }
//        bundle.putStringArrayList("data", data);
//        EventBus.getDefault().post(new MessageEvent<>(bundle, MessageEvent.EXAM_CHANGE_ANSWER));
    }


}
