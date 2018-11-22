package com.supe.supertest.viewpageractivity.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.adapter.QsRecycleAdapterItem;

/**
 * @Author yinzh
 * @Date   2018/10/17 19:55
 * @Description
 */public class UserAdapter extends QsRecycleAdapterItem<Item>{

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_age)
    TextView tv_age;

    public UserAdapter(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent);
    }

    @Override
    protected int itemViewLayoutId() {
        return R.layout.item_center_adapter;
    }

    @Override
    protected void onBindItemData(Item item, int i, int i1) {
//        tv_name.setText(item.name);
//        tv_age.setText(String.valueOf(item.age));
    }

}
