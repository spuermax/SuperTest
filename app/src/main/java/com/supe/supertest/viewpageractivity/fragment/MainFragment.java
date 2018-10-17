package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.View;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.fragment.QsFragment;

/*
 * @Author yinzh
 * @Date   2018/10/17 14:14
 * @Description
 */
public class MainFragment extends QsFragment {

    @Override
    public int layoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @OnClick({R.id.bt_main})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_main:
                QsToast.show("首页");
                break;
        }
    }
}
