package com.supe.supertest.Recycler2Recycler.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.module.RightModule;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/6/14 14:29
 * @Description
 */
public class RightAdapter extends BaseMultiItemQuickAdapter<RightModule, BaseViewHolder> {

    private int checkedPosition;


    public RightAdapter(List<RightModule> data) {
        super(data);
        addItemType(RightModule.TITLE, R.layout.adapter_right_head);
        addItemType(RightModule.CONTENT, R.layout.adapter_right_content);
    }


    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, RightModule item) {
        switch (helper.getItemViewType()) {
            case RightModule.TITLE:
                helper.setText(R.id.tv_sore_name, item.title);
                break;
            case RightModule.CONTENT:
                helper.setText(R.id.tv_child_name, item.name);
                break;

        }
    }
}
