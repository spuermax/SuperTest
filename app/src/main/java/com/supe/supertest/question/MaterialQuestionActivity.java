package com.supe.supertest.question;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.supe.supertest.R;
import com.supe.supertest.question.adapter.MaterialAdapter;
import com.supe.supertest.question.module.HomeworkQuestionBean;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author yinzh
 * @Date 2019/8/20 14:55
 * @Description
 */
public class MaterialQuestionActivity extends QsActivity {

    @Bind(R.id.material_recycler)
   RecyclerView recyclerView;

    private MaterialAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_material;
    }

    @Override
    protected View initView() {
        return super.initView();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        Log.i("AAAAAAA","AAAAAAAAA");
        ArrayList<HomeworkQuestionBean> arrayList = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Log.i("AAAAAAAAa","i = "+ i);
            HomeworkQuestionBean homeworkQuestionBean = new HomeworkQuestionBean();
            homeworkQuestionBean.setItemType(i);
            arrayList.add(homeworkQuestionBean);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        View view = getLayoutInflater().inflate(R.layout.item1, null);


        adapter = new MaterialAdapter(arrayList);

        adapter.addHeaderView(view);


        recyclerView.setAdapter(adapter);


    }
}
