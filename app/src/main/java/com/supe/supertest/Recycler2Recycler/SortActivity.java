package com.supe.supertest.Recycler2Recycler;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.adapter.LeftAdapter;
import com.supe.supertest.Recycler2Recycler.adapter.RightAdapter;
import com.supe.supertest.Recycler2Recycler.module.LeftModule;
import com.supe.supertest.Recycler2Recycler.module.RightModule;
import com.supe.supertest.common.utils.ScreenUtils;
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
    private LinearLayoutManager mRightManager;
    private RightAdapter rightAdapter;
    private LeftAdapter leftAdapter;

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
        leftAdapter = new LeftAdapter(leftModules);
        rv_left.setAdapter(leftAdapter);


        //scrollToPosition 会把不在屏幕的 Item 移动到屏幕上.
        // 原来在上方的 Item 移动到 可见 Item 的第一项，
        // 在下方的移动到屏幕可见 Item 的最后一项。已经显示的 Item 不会移动。

        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftAdapter.setCheckedPosition(position);
//                rv_right.scrollTo(0, ScreenUtils.dpToPx(200));
                mRightManager.scrollToPositionWithOffset(45,0);
//                rv_right.smoothScrollToPosition(18);
            }
        });


        mRightManager = new LinearLayoutManager(this);
        mRightManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_right.setLayoutManager(mRightManager);
        ArrayList<RightModule> rightModules = new ArrayList<>();
        for (int i = 0; i < 30; i++) {

            if (i % 2 == 0) {
                RightModule rightModule = new RightModule();
                rightModule.id = i;
                rightModule.name = "Super";
                rightModule.title = i + "蛋";
                rightModule.type = 1;
                rightModules.add(rightModule);
            } else {
                for (int i1 = 0; i1 < 3; i1++) {
                    RightModule rightModule = new RightModule();
                    rightModule.id = i;
                    rightModule.name = "Super" + i1;
                    rightModule.title = i + "蛋";
                    rightModule.type = 2;
                    rightModules.add(rightModule);
                }
            }

        }

        rightAdapter = new RightAdapter(rightModules);
        rv_right.setAdapter(rightAdapter);


    }
}
