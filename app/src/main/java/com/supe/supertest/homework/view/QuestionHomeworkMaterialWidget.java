package com.supe.supertest.homework.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.homework.adapter.MaterialAdapter;
import com.supe.supertest.homework.module.HomeworkQuestionBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author yinzh
 * @Date 2019/8/20 12:00
 * @Description
 */
public class QuestionHomeworkMaterialWidget extends BaseHomeworkQuestionWidget implements View.OnClickListener {

    protected RecyclerView recyclerView;
    private MaterialAdapter adapter;
    private TextView tv_name1;

    public QuestionHomeworkMaterialWidget(Context context) {
        super(context);
    }

    public QuestionHomeworkMaterialWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setData(HomeworkQuestionBean question, int index, int totalNum) {
        super.setData(question, index, totalNum);
        invalidateData();
    }

    @Override
    protected void invalidateData() {
        recyclerView = findViewById(R.id.recycler);
        initRecyclerView();

        super.invalidateData();
    }

    @Override
    protected void initView(AttributeSet attrs) {
    }

    private void initRecyclerView() {
        ArrayList<HomeworkQuestionBean> arrayList = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Log.e("AAAAAAAAa", "i = " + i);
            HomeworkQuestionBean homeworkQuestionBean = new HomeworkQuestionBean();
            homeworkQuestionBean.setItemType(i);
            arrayList.add(homeworkQuestionBean);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item1, null);


        adapter = new MaterialAdapter(arrayList);

        adapter.addHeaderView(view);


        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {

    }
}
