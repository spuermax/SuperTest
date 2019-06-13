package com.supe.supertest.Recycler2Recycler;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.adapter.LeftAdapter;
import com.supe.supertest.Recycler2Recycler.module.LeftModule;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/6/13 15:58
 * @Description
 */
public class SortActivity extends QsActivity {

    @Bind(R.id.rv_left)
    RecyclerView rv_left;

    @Bind(R.id.rv_right)
    RecyclerView rv_right;

    private LinearLayoutManager mLeftManager;

    @Override
    public int layoutId() {
        return R.layout.activity_sort;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<LeftModule> leftModules = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            LeftModule leftModule = new LeftModule();
            leftModule.id = i;
            leftModule.name = "Super" + i;
            leftModules.add(leftModule);
        }

        mLeftManager = new LinearLayoutManager(this);
        mLeftManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_left.setLayoutManager(mLeftManager);
        LeftAdapter leftAdapter = new LeftAdapter(leftModules);
        rv_left.setAdapter(leftAdapter);

        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftAdapter.setCheckedPosition(position);
            }
        });


    }
}
