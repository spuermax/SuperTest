package com.supe.supertest.test;

import android.os.Bundle;

import com.supe.supertest.R;
import com.supe.supertest.common.wdiget.MagicalLabelView;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.fragment.QsFragment;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/1/2 17:31
 * @Description
 */
public class MagicalLabelFragment extends QsFragment{

    @Bind(R.id.magical_label)
    MagicalLabelView magical_label;
    private ArrayList<String> label = new ArrayList<>();


    @Override
    public int layoutId() {
        return R.layout.fragment_margiclabel;
    }

    @Override
    public void initData(Bundle bundle) {
        label.add("全部");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        magical_label.setLabels(label);
    }
}
