package com.supe.supertest.home;

import android.os.Bundle;
import android.widget.ImageView;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.fragment.QsFragment;

/*
 * @Author yinzh
 * @Date   2018/10/17 20:31
 * @Description
 */public class ZhuGeFragment extends QsFragment{

    @Bind(R.id.iv_image)
    ImageView iv_image;


    @Override
    public int layoutId() {
        return R.layout.user_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        iv_image.setImageResource(R.drawable.zhugelaign);

    }
}
