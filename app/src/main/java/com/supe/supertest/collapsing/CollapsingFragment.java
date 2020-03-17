package com.supe.supertest.collapsing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.rxjava.RxJavaActivity;
import com.supe.supertest.rxjava.view.NestRecyclerView;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.fragment.QsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author yinzh
 * @Date 2020/3/17 17:17
 * @Description
 */
public class CollapsingFragment extends QsFragment {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    List<String> data = new ArrayList<>();


    @Override
    public int layoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int i = 'A'; i < 'z'; i++) {
            data.add("" + (char) i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Adapter(data));
    }


    class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public Adapter(@Nullable List<String> data) {
            super(R.layout.item_center_adapter, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_name, item);
        }


    }

}
