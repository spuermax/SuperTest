package com.supe.supertest.question.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.event.MessageEvent;
import com.supe.supertest.question.module.HomeworkQuestionBean;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/8/14 11:12
 * @Description 单选题
 */
public class QuestionHomeworkSingleChoiceWidget extends BaseHomeworkQuestionWidget implements View.OnClickListener {

    protected RadioGroup radioGroup;

    public QuestionHomeworkSingleChoiceWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkSingleChoiceWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        super.setData(question, index, totalNum);
        invalidateData();

    }

    @Override
    protected void invalidateData() {
        radioGroup = this.findViewById(R.id.quetion_choice_group);

        ArrayList<String> metas = mChildQuestion.getMetas();
        if (metas != null) {
            int size = metas.size();
            for (int i = 0; i < size; i++) {
                View view = initRadioButton(metas.get(i), i);
                LinearLayout.LayoutParams paramsRb = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                view.setOnClickListener(this);
                radioGroup.addView(view, paramsRb);
            }
        }
        super.invalidateData();


    }


    /**
     * 初始化选项
     */
    private View initRadioButton(String text, int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homework_question_checkbox, null);
        TextView tvMetaNum = view.findViewById(R.id.tv_meta_num);
        TextView tvContent = view.findViewById(R.id.tv_content);

        tvContent.setText(text);

        tvMetaNum.setBackgroundResource(R.drawable.sel_homework_radiobutton_bg);
        tvMetaNum.setText(CHOICE_ANSWER[index]);
        return view;
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
            TextView tvContent = child.findViewById(R.id.tv_content);
            child.setSelected(false);
            tvMetaNum.setSelected(false);
            tvContent.setSelected(false);
        }

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

    /**
     * 选择选项事件触发
     */
    protected void sendMsgToTestpaper() {
        Bundle bundle = new Bundle();
        bundle.putInt("index", mIndex - 1);
        bundle.putSerializable("QuestionType", mChildQuestion.getType());

        int count = radioGroup.getChildCount();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = radioGroup.getChildAt(i);
            if (view.isSelected()) {
                data.add(i + "");
            }
        }
        bundle.putStringArrayList("data", data);
        EventBus.getDefault().post(new MessageEvent<>(bundle, MessageEvent.EXAM_NEXT_QUESTION));
    }

    @Override
    protected void initView(AttributeSet attrs) {

    }
}
