package com.supe.supertest.Recycler2Recycler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.adapter.LeftAdapter;
import com.supe.supertest.Recycler2Recycler.adapter.RightAdapter;
import com.supe.supertest.Recycler2Recycler.module.LeftModule;
import com.supe.supertest.Recycler2Recycler.module.RightModule;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Bind(R.id.ll_layout)
    LinearLayout ll_layout;

    private LinearLayoutManager mLeftManager;
    private LinearLayoutManager mRightManager;
    private RightAdapter rightAdapter;
    private LeftAdapter leftAdapter;
    private ArrayList<RightModule> rightModules;


    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;

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
        mLeftManager.setOrientation(RecyclerView.VERTICAL);
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
                targetPosition = position;
                setChecked(position, true);
            }
        });


        mRightManager = new LinearLayoutManager(this);
        mRightManager.setOrientation(RecyclerView.VERTICAL);
        rv_right.setLayoutManager(mRightManager);
        rightModules = new ArrayList<>();
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


    private void setChecked(int position, boolean isMoved) {
        if (isMoved) {
            int count = 0;
            count += position * 4;
            setRightData(count);
        }

    }

    private void setRightData(int position) {
        rv_right.stopScroll();
        smoothMoveToPosition(position);
    }

    int count = 0;
    private void smoothMoveToPosition(int position) {
        int firstItem = mRightManager.findFirstVisibleItemPosition();
        int lastItem = mRightManager.findLastVisibleItemPosition();
        Log.d("SortActivity", "first --- >" + String.valueOf(firstItem));
        Log.d("SortActivity", "last --- >" + String.valueOf(lastItem));
        Log.d("SortActivity", "total---->" + rv_right.getChildCount());
        Log.d("SortActivity", "size---->" + rightModules.size());
        Log.d("SortActivity", "position---->" + position);

        if (position <= firstItem) {
            rv_right.scrollToPosition(position);
        } else if (position <= lastItem) {
            Log.d("SortActivity", "pos---->" + String.valueOf(position) + "VS" + firstItem);
            int top = rv_right.getChildAt(position - firstItem).getTop();
            Log.d("SortActivity", "top---->" + String.valueOf(top));
            rv_right.scrollBy(0, top);

            if (lastItem == rightModules.size() - 1) {
                count++;
                if (count > 1) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 564);
                    textView.setLayoutParams(params);
                    ll_layout.addView(textView);
                }
            }


        } else {
            rv_right.scrollToPosition(position);
        }


    }
}
