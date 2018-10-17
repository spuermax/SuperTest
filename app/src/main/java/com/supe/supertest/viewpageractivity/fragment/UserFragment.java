package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.fragment.QsFragment;

/*
 * @Author yinzh
 * @Date   2018/10/17 14:15
 * @Description
 */public class UserFragment extends QsFragment{
    @Override
    public int layoutId() {
        return R.layout.user_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @OnClick({R.id.bt_user})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_user:
                QsToast.show("用户");
                break;
        }
    }
}
