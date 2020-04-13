package com.supe.supertest.homework.view;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.supe.supertest.R;
import com.supe.supertest.homework.QuestionActivity;
import com.supe.supertest.homework.html.EduHtml;
import com.supe.supertest.homework.html.EduImageGetterHandler;
import com.supe.supertest.homework.html.EduTagHandler;
import com.supe.supertest.homework.module.HomeworkAnswerBean;
import com.supe.supertest.homework.module.HomeworkQuestionBean;
import com.supe.supertest.homework.module.HomeworkQuestionTypeBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Pattern;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.supe.supertest.homework.html.EduTagHandler.parseInt;

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

    protected Pattern stemPattern = Pattern.compile("(\\[\\[[^\\[\\]]+]])", Pattern.DOTALL);

    public static final String[] CHOICE_ANSWER = {
            "A", "B", "C",
            "D", "E", "F",
            "G", "H", "I",
            "J", "K", "L"
    };

    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        mIndex = index;
        mQuestion = question;
        if (mQuestion.getType() == HomeworkQuestionTypeBean.material) {
            mChildQuestion = question.getItems().get(0);
        } else {
            mChildQuestion = question;
        }
        mTotalNum = totalNum;
    }

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

        SpannableStringBuilder spanned = (SpannableStringBuilder) getQuestionStem(mChildQuestion.getStem());
        spanned = EduHtml.addImageClickListener(spanned, tvStem, mContext);
        tvStem.setText(setHtmlContent(spanned));

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


        if (getContext() instanceof QuestionActivity) {
            QuestionActivity testpaperActivity = (QuestionActivity) getContext();
            ArrayList<HomeworkAnswerBean> answerList = testpaperActivity.answerList;
            //页面切换 回填
            setAnswer(mIndex - 1, answerList);
        }
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

        SpannableStringBuilder spanned = (SpannableStringBuilder) getMaterialQuestionStem(mQuestion.getStem());
        spanned = EduHtml.addImageClickListener(spanned, tvStem, mContext);
        tvMaterialStem.setText(spanned);
        tvChildType.setText(mChildQuestion.getType().title());

        if (mQuestion.getType() == HomeworkQuestionTypeBean.material) {
            clChild.setVisibility(VISIBLE);
        } else {
            clChild.setVisibility(GONE);
        }
    }

    private int getNumberLength(int number) {
        int length = 1;
        while (number >= 10) {
            length++;
            number = number / 10;
        }
        return length;
    }

    /**
     * 复用时 重新回填
     */
    private void setAnswer(int index, ArrayList<HomeworkAnswerBean> answerList) {
        if (answerList == null) {
            return;
        }
        HomeworkAnswerBean answer = answerList.get(index);
        if (answer != null && answer.data != null) {
            restoreResult(answer.data);
        }
        return;
    }

    /**
     * 恢复数据
     *
     * @param resultData
     */
    protected abstract void restoreResult(ArrayList<String> resultData);

    /**
     * 材料题题干
     */
    protected Spanned getMaterialQuestionStem(String strStem) {
        return Html.fromHtml(removeHtml_P(removeHtml(strStem)), new EduImageGetterHandler(mContext, tvStem), new EduTagHandler());
    }

    /**
     * 获取题干
     */
    protected Spanned getQuestionStem(String strStem) {
        String stem = String.format("%d、%s",
                mIndex, removeHtml_P(removeHtml(strStem))
        );
        return Html.fromHtml(stem, new EduImageGetterHandler(mContext, tvStem), new EduTagHandler());
    }

    protected void enable(ViewGroup viewGroup, boolean isEnable) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(isEnable);
        }
    }

    protected String listToStr(ArrayList<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                int l = parseInt(lhs);
                int r = parseInt(rhs);
                return l - r;
            }
        });
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

    protected String getAnswerByType(HomeworkQuestionTypeBean qt, String answer) {
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

    private String parseDetermineAnswer(String answer) {
        int index;
        index = Integer.parseInt(answer);

        if (index == 0) {
            return "B";
        } else {
            return "A";
        }
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

    protected ArrayList<String> coverResultAnswer(Object answer) {
        ArrayList<String> answerList;
        if (answer == null) {
            return new ArrayList<>();
        }
        if (answer instanceof LinkedTreeMap) {
            LinkedTreeMap answerMap = (LinkedTreeMap) answer;
            answerList = new ArrayList<>();
            ArrayList<String> keyList = new ArrayList<>(answerMap.keySet());
            Collections.sort(keyList);
            for (String key : keyList) {
                answerList.add(answerMap.get(key).toString());
            }
            return answerList;
        } else if (answer instanceof ArrayList) {
            return (ArrayList<String>) answer;
        }

        return new ArrayList<>();
    }


    /**
     * 初始化 答案解析
     *
     * @param view
     */
    private ArrayList<String> arrayList = new ArrayList<String>();

    private ArrayList<String> createAnsew() {
        arrayList.clear();
        arrayList.addAll((ArrayList<String>) mChildQuestion.getResult().answer);

        return arrayList;
    }

    protected void initResultAnalysis(View view) {
        LinearLayout llChoice = view.findViewById(R.id.ll_choice);
        LinearLayout llFill = view.findViewById(R.id.ll_fill);
        LinearLayout llEssay = view.findViewById(R.id.ll_essay);

        View divider = this.findViewById(R.id.divider);
        divider.setVisibility(GONE);

        checkShowAnalysis.setVisibility(GONE);
        llQuestionAnalysis.setVisibility(VISIBLE);


        if (mChildQuestion.getType() == HomeworkQuestionTypeBean.essay) {
            llChoice.setVisibility(GONE);
            llFill.setVisibility(GONE);
            llEssay.setVisibility(VISIBLE);

            TextView tvRight = view.findViewById(R.id.tv_right);
            tvRight.setText(TextUtils.isEmpty(mChildQuestion.getAnalysis()) ? "暂无答案" : Html.fromHtml(mChildQuestion.getAnswer().get(0)));
        } else if (mChildQuestion.getType() == HomeworkQuestionTypeBean.fill) {
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
                myAnswer = mContext.getResources().getString(R.string.unanswered);
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.yellow));
            } else if ("right".equals(mChildQuestion.getResult().status)) {
                createAnsew();
                myAnswer = listToStr(arrayList);
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.es_green));
            } else {
                createAnsew();
                myAnswer = listToStr(arrayList);
                tvMyAnswer.setTextColor(mContext.getResources().getColor(R.color.red));
            }

            Log.i("AAAAAAAA", myAnswer + "-------000000--------");
            tvMyAnswer.setText(myAnswer);
        }
    }

    /**
     * 查看解析 选项赋值
     */
    protected void initQuestionResult(RadioGroup radioGroup) {
        ArrayList<String> arrayList = createAnsew();
        switch (mChildQuestion.getType()) {
            case choice:
            case uncertain_choice:
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    View child = radioGroup.getChildAt(i);
                    //选中 我的答案
                    if (null != mChildQuestion.getResult().status
                            && !"noAnswer".equals(mChildQuestion.getResult().status)) {
                        for (String myAnswer : arrayList) {
                            if (myAnswer.equals(String.valueOf(i))) {
                                TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                                TextView tvContent = child.findViewById(R.id.tv_content);
                                tvMetaNum.setBackgroundResource(R.drawable.rect_4_solid_orange);
                                tvMetaNum.setTextColor(mContext.getResources().getColor(R.color.es_font_white));
                                tvContent.setTextColor(mContext.getResources().getColor(R.color.red));
                                break;
                            }
                        }
                    }
                    //选中 正确答案
                    for (String answer : mChildQuestion.getAnswer()) {
                        if (answer.equals(String.valueOf(i))) {
                            TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                            TextView tvContent = child.findViewById(R.id.tv_content);
                            tvMetaNum.setBackgroundResource(R.drawable.sel_homework_checkbox_bg);
                            tvMetaNum.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_text_color));
                            tvContent.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_content_text_color));
                            tvMetaNum.setSelected(true);
                            tvContent.setSelected(true);
                            break;
                        }
                    }
                }
                break;
            case single_choice:
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    View child = radioGroup.getChildAt(i);
                    //选中 我的答案
                    if (null != mChildQuestion.getResult().status
                            && !"noAnswer".equals(mChildQuestion.getResult().status)) {
                        for (String myAnswer : arrayList) {
                            if (myAnswer.equals(String.valueOf(i))) {
                                TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                                TextView tvContent = child.findViewById(R.id.tv_content);
                                tvMetaNum.setBackgroundResource(R.drawable.oval_34_solid_orange);
                                tvMetaNum.setTextColor(mContext.getResources().getColor(R.color.es_font_white));
                                tvContent.setTextColor(mContext.getResources().getColor(R.color.red));
                                break;
                            }
                        }
                    }
                    //选中 正确答案
                    for (String answer : mChildQuestion.getAnswer()) {
                        if (answer.equals(String.valueOf(i))) {
                            TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                            TextView tvContent = child.findViewById(R.id.tv_content);
                            tvMetaNum.setBackgroundResource(R.drawable.sel_homework_radiobutton_bg);
                            tvMetaNum.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_text_color));
                            tvContent.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_content_text_color));
                            tvMetaNum.setSelected(true);
                            tvContent.setSelected(true);
                            break;
                        }
                    }
                }
                break;
            case determine:
                //选中 我的答案
                if (null != mChildQuestion.getResult().status
                        && !"noAnswer".equals(mChildQuestion.getResult().status)) {
                    View child;
                    if ("1".equals(arrayList.get(0))) {
                        child = radioGroup.getChildAt(0);
                    } else {
                        child = radioGroup.getChildAt(1);
                    }
                    TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                    TextView tvContent = child.findViewById(R.id.tv_content);
                    tvMetaNum.setBackgroundResource(R.drawable.oval_34_solid_orange);
                    tvMetaNum.setTextColor(mContext.getResources().getColor(R.color.es_font_white));
                    tvContent.setTextColor(mContext.getResources().getColor(R.color.red));

                }
                //选中 正确答案
                View child;
                if ("1".equals(mChildQuestion.getAnswer().get(0))) {
                    child = radioGroup.getChildAt(0);
                } else {
                    child = radioGroup.getChildAt(1);
                }
                TextView tvMetaNum = child.findViewById(R.id.tv_meta_num);
                TextView tvContent = child.findViewById(R.id.tv_content);
                tvMetaNum.setBackgroundResource(R.drawable.sel_homework_radiobutton_bg);
                tvMetaNum.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_text_color));
                tvContent.setTextColor(mContext.getResources().getColorStateList(R.color.sel_homework_checkbox_content_text_color));
                tvMetaNum.setSelected(true);
                tvContent.setSelected(true);
                break;
            default:
                break;

        }

    }


    /**
     * 去掉由于Html.fromHtml产生的'\n'
     *
     * @param spanned
     * @return
     */
    public static CharSequence setHtmlContent(Spanned spanned) {
        if (spanned == null) {
            return "";
        }
        if (spanned.length() > 2 && spanned.subSequence(spanned.length() - 2, spanned.length()).toString().equals("\n\n")) {
            return spanned.subSequence(0, spanned.length() - 2);
        }
        return spanned;
    }


    /**
     * 去掉头部、末尾产生的"\n"
     */
    public static String removeHtml(String strHtml) {
        if(strHtml == null)
            return "";
        if (strHtml.length() > 0 && strHtml.contains("\n")) {
            if (strHtml.substring(0, 1).equals("\n")) {
                strHtml = strHtml.substring(1, strHtml.length() - 1);
                return removeHtml(strHtml);
            }
            if (strHtml.substring(strHtml.length() - 1, strHtml.length()).equals("\n")) {
                strHtml = strHtml.substring(0, strHtml.length() - 1);
                return removeHtml(strHtml);
            }
        }
        return strHtml;
    }

    /**
     * 去掉全部"<p></p>"
     */
    public static String removeHtml_P(String strHtml) {
        strHtml = strHtml.replace("<p>", "");
        strHtml = strHtml.replace("</p>", "");
        return strHtml;
    }


}
