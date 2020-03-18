package com.supe.supertest.homework.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.homework.event.MessageEvent;
import com.supe.supertest.homework.module.HomeworkQuestionBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/8/19 15:01
 * @Description 判断题
 */
public class QuestionHomeworkDetermineWidget extends BaseHomeworkQuestionWidget implements View.OnClickListener{

    protected RadioGroup radioGroup;

    public QuestionHomeworkDetermineWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkDetermineWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView(AttributeSet attrs) {

    }

    @Override
    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        super.setData(question, index, totalNum);
        invalidateData();
    }

    @Override
    protected void invalidateData() {
        radioGroup = this.findViewById(R.id.quetion_choice_group);
        super.invalidateData();


        for (int i = 0; i < 2; i++) {
            View view = initRadioButton(i);
            LinearLayout.LayoutParams paramsRb = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setOnClickListener(this);
            radioGroup.addView(view, paramsRb);
        }
    }


    /**
     * 初始化选项
     */
    private View initRadioButton(int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homework_question_checkbox, null);
        TextView tvMetaNum = view.findViewById(R.id.tv_meta_num);
        TextView tvContent = view.findViewById(R.id.tv_content);
        if (index == 0) {
            tvContent.setText("正确");
        } else {
            tvContent.setText("错误");
        }

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


    protected void sendMsgToTestpaper() {
        Bundle bundle = new Bundle();
        bundle.putInt("index", mIndex - 1);
        bundle.putSerializable("HomeworkQuestionTypeBean", mChildQuestion.getType());
        int count = radioGroup.getChildCount();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = radioGroup.getChildAt(i);
            if (view.isSelected()) {
                if (i == 0) {
                    data.add("1");
                } else {
                    data.add("0");
                }
            }
        }
        bundle.putStringArrayList("data", data);
        EventBus.getDefault().post(new MessageEvent<>(bundle, MessageEvent.EXAM_NEXT_QUESTION));
    }
}
