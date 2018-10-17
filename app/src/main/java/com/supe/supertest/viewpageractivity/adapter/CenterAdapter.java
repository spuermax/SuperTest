package com.supe.supertest.viewpageractivity.adapter;

import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.adapter.QsListAdapterItem;

/*
 * @Author yinzh
 * @Date   2018/10/17 16:46
 * @Description
 */public class CenterAdapter extends QsListAdapterItem<Item> {

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_age)
    TextView tv_age;


    @Override
    public int getItemLayout() {
        return R.layout.item_center_adapter;
    }

    @Override
    public void bindData(Item item, int i, int i1) {
        tv_name.setText(item.name);
        tv_age.setText(String.valueOf(item.age));
    }
}
