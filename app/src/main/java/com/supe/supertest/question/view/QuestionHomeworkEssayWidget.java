package com.supe.supertest.question.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.QuestionActivity;
import com.supe.supertest.question.event.MessageEvent;
import com.supe.supertest.question.module.HomeworkQuestionBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/8/19 14:07
 * @Description 简答题
 */
public class QuestionHomeworkEssayWidget extends BaseHomeworkQuestionWidget{

    private EditText etReply;

    public QuestionHomeworkEssayWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkEssayWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        etReply = findViewById(R.id.et_reply);
        etReply.addTextChangedListener(onTextChangedListener);

        super.invalidateData();

        //正确解析
        Context context = etReply.getContext();
        if (context instanceof QuestionActivity) {
            QuestionActivity testpaperActivity = (QuestionActivity) getContext();
//            if (testpaperActivity.mDoType == HOMEWORK_PARSE && mChildQuestion.getResult() != null) {// doType 字段意思是练习类型
            if ( mChildQuestion.getResult() != null) {
                etReply.setEnabled(false);
                mAnalysisVS = this.findViewById(R.id.quetion_choice_analysis);
                mAnalysisVS.setOnInflateListener(new ViewStub.OnInflateListener() {
                    @Override
                    public void onInflate(ViewStub viewStub, View view) {
                        initResultAnalysis(view);
                        etReply.setText(mChildQuestion.getResult().answer.get(0));
                    }
                });
                mAnalysisVS.inflate();

            }
        }
    }



    private TextWatcher onTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int index, int i2, int i3) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", mIndex - 1);
            bundle.putSerializable("QuestionType", mChildQuestion.getType());
            ArrayList<String> data = new ArrayList<>();
            data.add(etReply.getText().toString());

            bundle.putStringArrayList("data", data);
            EventBus.getDefault().post(new MessageEvent<>(bundle, MessageEvent.EXAM_CHANGE_ANSWER));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
