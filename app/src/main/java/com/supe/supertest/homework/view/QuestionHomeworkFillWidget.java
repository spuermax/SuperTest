package com.supe.supertest.homework.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.homework.event.MessageEvent;
import com.supe.supertest.homework.html.EduImageGetterHandler;
import com.supe.supertest.homework.html.EduTagHandler;
import com.supe.supertest.homework.module.HomeworkQuestionBean;
import com.supermax.base.common.log.L;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * @Author yinzh
 * @Date 2020/8/24 13:41
 * @Description
 */
public class QuestionHomeworkFillWidget extends BaseHomeworkQuestionWidget {

    protected LinearLayout fillLayout;

    public QuestionHomeworkFillWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkFillWidget(Context context, AttributeSet attrs) {
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
        fillLayout = this.findViewById(R.id.question_fill_layout);


        Matcher matcher = stemPattern.matcher(mChildQuestion.getStem());
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        fillLayout.removeAllViews();
        for (int i = 1; i <= count; i++) {
            View view = initEditText(i);
            fillLayout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        super.invalidateData();


        //正确解析
//        Context context = fillLayout.getContext();
//        if (context instanceof HomeworkQuestionActivity) {
//            HomeworkQuestionActivity testpaperActivity = (HomeworkQuestionActivity) getContext();
//            if (testpaperActivity.mDoType == HOMEWORK_PARSE && mChildQuestion.getResult() != null) {
//                enable(fillLayout, false);
//                mAnalysisVS = this.findViewById(R.id.quetion_choice_analysis);
//                mAnalysisVS.setOnInflateListener(new ViewStub.OnInflateListener() {
//                    @Override
//                    public void onInflate(ViewStub viewStub, View view) {
//                        initResultAnalysis(view);
//                        initQuestionResult();
//                    }
//                });
//                mAnalysisVS.inflate();
//            }
//        }
    }

    private View initEditText(int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_homework_question_fill, null);
        TextView tvNum = view.findViewById(R.id.tv_num);
        EditText tvContent = view.findViewById(R.id.et_content);

        tvNum.setText(String.format("（%d）", index));
        tvContent.addTextChangedListener(onTextChangedListener);

        return view;
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
            for (int i = 0; i < fillLayout.getChildCount(); i++) {
                View view = fillLayout.getChildAt(i);
                EditText tvContent = view.findViewById(R.id.et_content);
                data.add(tvContent.getText().toString());
            }

            bundle.putStringArrayList("data", data);
            EventBus.getDefault().post(new MessageEvent<>(bundle, MessageEvent.EXAM_CHANGE_ANSWER));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void restoreResult(ArrayList<String> resultData) {
        int count = fillLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = fillLayout.getChildAt(i);
            EditText tvContent = view.findViewById(R.id.et_content);
            tvContent.setText(resultData.get(i));
        }
    }

    @Override
    protected Spanned getQuestionStem(String strStem) {
        String stem = String.format(
                "%d、%s",
                mIndex,
                parseStem(CommonUtil.removeHtml_P(CommonUtil.removeHtml(strStem)))
        );
        return Html.fromHtml(stem, new EduImageGetterHandler(mContext, tvStem), new EduTagHandler());
    }

    private void initQuestionResult() {// 出现过 ClassCastException 但是并没有复现出来 固。。。
        ArrayList<String> answerList;
        if (mChildQuestion.getResult().answer instanceof ArrayList) {
            answerList = (ArrayList<String>) mChildQuestion.getResult().answer;
        } else {
            answerList = new ArrayList<>();
        }


        if (mChildQuestion.getResult().answer == null
                || answerList.size() == 0) {
            for (int i = 0; i < fillLayout.getChildCount(); i++) {
                View view = fillLayout.getChildAt(i);
                TextView tvNum = view.findViewById(R.id.tv_num);
                EditText tvContent = view.findViewById(R.id.et_content);

                tvContent.setText(mContext.getResources().getString(R.string.unanswered));
                tvContent.setEnabled(false);
                tvNum.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
            }
        } else {
            for (int i = 0; i < fillLayout.getChildCount(); i++) {
                View view = fillLayout.getChildAt(i);
                TextView tvNum = view.findViewById(R.id.tv_num);
                EditText tvContent = view.findViewById(R.id.et_content);
                tvContent.setText(TextUtils.isEmpty(answerList.get(i)) ? mContext.getResources().getString(R.string.unanswered) : answerList.get(i));
                tvContent.setEnabled(false);

                if (answerList == null) {
                    tvNum.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
                    tvContent.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
                } else {
                    if (TextUtils.isEmpty(answerList.get(i))
                            || answerList.get(i).equals(mContext.getResources().getString(R.string.unanswered))) {
                        tvNum.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
                        tvContent.setTextColor(mContext.getResources().getColor(R.color.color_FD6700));
                    } else {
                        String[] answers = mChildQuestion.getAnswer().get(i).split("\\|");
                        boolean isTrue = false;
                        for (String answer : answers) {
                            if (answerList.get(i).contains(answer)) {
                                tvNum.setTextColor(mContext.getResources().getColor(R.color.es_green));
                                tvContent.setTextColor(mContext.getResources().getColor(R.color.es_green));
                                isTrue = true;
                                break;
                            }
                        }
                        if (!isTrue) {
                            tvNum.setTextColor(mContext.getResources().getColor(R.color.red));
                            tvContent.setTextColor(mContext.getResources().getColor(R.color.red));
                        }
                    }
                }
            }
        }
    }

    private StringBuffer parseStem(String stem) {
        Matcher matcher = stemPattern.matcher(stem);
        StringBuffer stringBuilder = new StringBuffer();
        int count = 0;
        while (matcher.find()) {
            L.d("Method_parseStem", "find-->" + matcher);
            count++;
            matcher.appendReplacement(stringBuilder, String.format("<font color='#01AE66'>（%d）</font>", count));
        }
        matcher.appendTail(stringBuilder);

        return stringBuilder;
    }
}

