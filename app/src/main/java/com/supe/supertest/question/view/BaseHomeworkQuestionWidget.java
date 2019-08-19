package com.supe.supertest.question.view;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.module.HomeworkQuestionBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @Author yinzh
 * @Date 2019/8/13 14:56
 * @Description
 */
public abstract class BaseHomeworkQuestionWidget extends RelativeLayout {

    protected TextView tvType;
    protected TextView tvNumber;
    protected ConstraintLayout clChild;
    protected TextView tvMaterialStem;
    protected TextView tvChildType;

    protected TextView tvStem;
    protected AppCompatCheckBox checkShowAnalysis;
    protected TextView tvQuestionAnalysis;
    protected LinearLayout llQuestionAnalysis;
    protected ViewStub mAnalysisVS;

    protected Context mContext;

    protected HomeworkQuestionBean mQuestion;
    protected HomeworkQuestionBean mChildQuestion;

    //当前题目编号
    protected int mIndex;
    //总题数
    protected int mTotalNum;

    public static final String[] CHOICE_ANSWER = {
            "A", "B", "C",
            "D", "E", "F",
            "G", "H", "I",
            "J", "K", "L"
    };


    public BaseHomeworkQuestionWidget(Context context) {
        super(context);
        mContext = context;
        initView(null);
    }

    public BaseHomeworkQuestionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    protected abstract void initView(AttributeSet attrs);

    /**
     * 初始化
     */
    protected void invalidateData() {
        tvType = this.findViewById(R.id.tv_type);
        tvNumber = this.findViewById(R.id.tv_number);
        clChild = this.findViewById(R.id.cl_child);
        tvMaterialStem = this.findViewById(R.id.tv_material_stem);
        tvChildType = this.findViewById(R.id.tv_child_type);

        tvStem = this.findViewById(R.id.question_stem);
        llQuestionAnalysis = this.findViewById(R.id.ll_question_analysis);
        checkShowAnalysis = this.findViewById(R.id.check_show_analysis);
        tvQuestionAnalysis = this.findViewById(R.id.tv_question_analysis);

        showQuestionTopTitle();

        tvStem.setText(mChildQuestion.getStem());

        tvQuestionAnalysis.setText(TextUtils.isEmpty(mChildQuestion.getAnalysis()) ? "暂无解析" : Html.fromHtml(mChildQuestion.getAnalysis()));
        checkShowAnalysis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llQuestionAnalysis.isShown()) {
                    llQuestionAnalysis.setVisibility(GONE);

                    llQuestionAnalysis.setFocusable(false);
                    llQuestionAnalysis.setFocusableInTouchMode(false);
                } else {
                    llQuestionAnalysis.setVisibility(VISIBLE);

                    llQuestionAnalysis.setFocusable(true);
                    llQuestionAnalysis.setFocusableInTouchMode(true);
                    llQuestionAnalysis.requestFocus();
                }
            }
        });
    }

    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        mIndex = index;
        mQuestion = question;
        if (mQuestion.getType() == HomeworkQuestionBean.HomeworkQuestionTypeBean.material) {
            mChildQuestion = question.getItems().get(0);
        } else {
            mChildQuestion = question;
        }
        mTotalNum = totalNum;
    }



    /**
     * 显示题型、分数、说明、题数
     */
    private void showQuestionTopTitle() {
        String text = String.format("%d/%d", mIndex, mTotalNum);
        SpannableString spannableString = new SpannableString(text);
        int color = getResources().getColor(R.color.yellow);
        int length = getNumberLength(mIndex);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(18, true), 0, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvType.setText(mQuestion.getType().title());
        tvNumber.setText(spannableString);
    }


    private int getNumberLength(int number) {
        int length = 1;
        while (number >= 10) {
            length++;
            number = number / 10;
        }
        return length;
    }



    protected void initResultAnalysis(View view){
        LinearLayout llChoice = view.findViewById(R.id.ll_choice);
        LinearLayout llFill = view.findViewById(R.id.ll_fill);
        LinearLayout llEssay = view.findViewById(R.id.ll_essay);

        View divider = this.findViewById(R.id.divider);
        divider.setVisibility(GONE);

        checkShowAnalysis.setVisibility(GONE);
        llQuestionAnalysis.setVisibility(VISIBLE);

        if (mChildQuestion.getType() == HomeworkQuestionBean.HomeworkQuestionTypeBean.essay) {
            llChoice.setVisibility(GONE);
            llFill.setVisibility(GONE);
            llEssay.setVisibility(VISIBLE);

            TextView tvRight = view.findViewById(R.id.tv_right);
            tvRight.setText(TextUtils.isEmpty(mChildQuestion.getAnalysis()) ? "暂无答案" : Html.fromHtml(mChildQuestion.getAnswer().get(0)));
        } else if (mChildQuestion.getType() == HomeworkQuestionBean.HomeworkQuestionTypeBean.fill) {
            llChoice.setVisibility(GONE);
            llFill.setVisibility(VISIBLE);
            llEssay.setVisibility(GONE);

            for (int i = 0; i < mChildQuestion.getAnswer().size(); i++) {
                View item = LayoutInflater.from(mContext).inflate(R.layout.item_homework_question_fill_analysis, null);
                TextView tvMask = item.findViewById(R.id.tv_mask);
                TextView tvAnswer = item.findViewById(R.id.tv_answer);

                tvMask.setText(String.format("（%d）的标准答案：", i + 1));
                tvAnswer.setText(mChildQuestion.getAnswer().get(i));
                llFill.addView(item);
            }
        } else {
            llChoice.setVisibility(VISIBLE);
            llFill.setVisibility(GONE);
            llEssay.setVisibility(GONE);

            TextView tvRightAnswer = view.findViewById(R.id.tv_right_anwer);
            TextView tvMyAnswer = view.findViewById(R.id.tv_my_anwer);
            tvRightAnswer.setText(listToStr(mChildQuestion.getAnswer()));
            String myAnswer;
            if (null == mChildQuestion.getResult().status || "noAnswer".equals(mChildQuestion.getResult().status)) {
                myAnswer = "未作答";
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.yellow));
            } else if ("right".equals(mChildQuestion.getResult().status)) {
                myAnswer = listToStr(mChildQuestion.getResult().answer);
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.es_green));
            } else {
                myAnswer = listToStr(mChildQuestion.getResult().answer);
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            tvMyAnswer.setText(myAnswer);
        }

    }


    protected String listToStr(ArrayList<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String answer : arrayList) {
            if (TextUtils.isEmpty(answer)) {
                continue;
            }
            stringBuilder.append(getAnswerByType(mChildQuestion.getType(), answer));
            stringBuilder.append("、");
        }
        int length = stringBuilder.length();
        if (length > 0) {
            stringBuilder.delete(length - 1, length);
        }
        return stringBuilder.toString();
    }



    protected String getAnswerByType(HomeworkQuestionBean.HomeworkQuestionTypeBean qt, String answer) {
        switch (qt) {
            case choice:
            case single_choice:
            case uncertain_choice:
                return parseAnswer(answer);
            case determine:
                return parseDetermineAnswer(answer);
            case essay:
            case fill:
                return answer;
            default:
                break;
        }

        return "";
    }

    private String parseAnswer(String answer) {
        int index;
        try {
            index = Integer.parseInt(answer);
        } catch (Exception e) {
            index = 0;
        }
        return CHOICE_ANSWER[index];
    }

    private String parseDetermineAnswer(String answer) {
        int index;
        index = Integer.parseInt(answer);

        if (index == 0) {
            return "B";
        } else {
            return "A";
        }
    }


}
