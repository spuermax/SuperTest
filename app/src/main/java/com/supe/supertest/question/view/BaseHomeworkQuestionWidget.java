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
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.question.module.HomeworkQuestionBean;

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
        tvType.setText("单选题");
        tvNumber.setText("1/20");
    }


    private int getNumberLength(int number) {
        int length = 1;
        while (number >= 10) {
            length++;
            number = number / 10;
        }
        return length;
    }



}
