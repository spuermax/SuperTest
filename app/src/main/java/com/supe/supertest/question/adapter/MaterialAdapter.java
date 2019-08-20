package com.supe.supertest.question.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.question.module.HomeworkQuestionBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author yinzh
 * @Date 2019/8/20 15:02
 * @Description
 */
public class MaterialAdapter extends BaseMultiItemQuickAdapter<HomeworkQuestionBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MaterialAdapter(List<HomeworkQuestionBean> data) {
        super(data);
        addItemType(1, R.layout.item0);
        addItemType(2, R.layout.item1);
        addItemType(3, R.layout.item0);
        addItemType(4, R.layout.item1);
        addItemType(5, R.layout.item0);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeworkQuestionBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                helper.setText(R.id.tv_title,"1.我是单选题你知道吗？");
                break;
            case 2:
                helper.setText(R.id.tv_title,"1.我是单选题你知道吗？");
                break;
            case 3:
                helper.setText(R.id.tv_title,"1.我是单选题你知道吗？");
                break;
            case 4:
                helper.setText(R.id.tv_title,"1.我是单选题你知道吗？");
                break;
            case 5:
                helper.setText(R.id.tv_title,"1.我是单选题你知道吗？");
                break;

        }

    }

}
