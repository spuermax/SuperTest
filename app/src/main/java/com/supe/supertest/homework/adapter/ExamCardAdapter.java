package com.supe.supertest.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.homework.module.HomeworkAnswerBean;
import com.supe.supertest.homework.module.HomeworkItemResultBean;
import com.supe.supertest.homework.module.HomeworkQuestionBean;
import com.supe.supertest.homework.module.HomeworkQuestionTypeBean;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2020/3/18 17:01
 * @Description
 */
public class ExamCardAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<HomeworkQuestionBean> mList;
    private ArrayList<HomeworkAnswerBean> mAnswers;

    //展示答题结果
    private boolean isShowResult;

    public ExamCardAdapter(Context context, ArrayList<HomeworkQuestionBean> list,
                               ArrayList<HomeworkAnswerBean> answers, boolean isShowResult) {
        mList = list;
        mContext = context;
        mAnswers = answers;
        this.isShowResult = isShowResult;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHolder itemHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_question_card_gridview, null);
            itemHolder = new ItemHolder();
            itemHolder.mText = view.findViewById(R.id.tv_num);
            view.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        itemHolder.mText.setText(i + 1 + "");

        HomeworkQuestionBean question = mList.get(i);
        if (question.getType() == HomeworkQuestionTypeBean.material) {
            question = question.getItems().get(0);
        }

        //是否已选择
        if (mAnswers != null) {
            HomeworkAnswerBean answer = mAnswers.get(i);
            itemHolder.mText.setSelected(answer.isAnswer);
        }

        if (isShowResult) {
            //答题结果展示
            HomeworkItemResultBean testResult = question.getResult();
            if (testResult != null) {
                if ("right".equals(testResult.status)) {
                    itemHolder.mText.setBackgroundResource(R.drawable.oval_28_solid_green);
                } else if ("noAnswer".equals(testResult.status) || null == testResult.status) {
                    itemHolder.mText.setBackgroundResource(R.drawable.oval_28_solid_gray);
                } else {
                    itemHolder.mText.setBackgroundResource(R.drawable.oval_28_solid_orange);
                }
            }
        }
        return view;
    }

    class ItemHolder {
        TextView mText;
    }
}
