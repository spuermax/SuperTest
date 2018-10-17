package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.View;

import com.supe.supertest.R;
import com.supe.supertest.viewpageractivity.adapter.CenterAdapter;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supe.supertest.viewpageractivity.persenter.CenterPresenter;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.fragment.QsFragment;
import com.supermax.base.mvp.fragment.QsListFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/17 14:15
 * @Description
 */
public class CenterFragment extends QsListFragment <CenterPresenter, Item>{
//    @Override
//    public int layoutId() {
//        return R.layout.center_fragment;
//    }

    @Override
    public void initData(Bundle bundle) {
      getPresenter().requestData();
      showContentView();
      }

//    @OnClick({R.id.bt_center})
//    public void onViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_center:
//                QsToast.show("中间");
//                break;
//        }
//    }

    @Override
    public QsListAdapterItem getListAdapterItem(int i) {
        return new CenterAdapter();
    }


}
