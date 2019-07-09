package com.supe.supertest.Recycler2Recycler.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.module.LeftModule;
import com.supermax.base.common.log.L;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Author yinzh
 * @Date 2019/6/13 16:07
 * @Description
 */
public class LeftAdapter extends BaseQuickAdapter<LeftModule, BaseViewHolder> {

    private int checkedPosition;
    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public LeftAdapter(@Nullable List<LeftModule> data) {
        super(R.layout.left_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeftModule item) {
        helper.setText(R.id.tv_name, item.name);

      TextView textView = helper.getView(R.id.tv_name);
        L.d("LeftAdapter",helper.getLayoutPosition() + "");


        if (helper.getLayoutPosition() == checkedPosition) {
            textView.setBackgroundColor(Color.parseColor("#f3f3f3"));
            textView.setTextColor(Color.parseColor("#0068cf"));
        } else {
            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textView.setTextColor(Color.parseColor("#1e1d1d"));
        }
    }

}
