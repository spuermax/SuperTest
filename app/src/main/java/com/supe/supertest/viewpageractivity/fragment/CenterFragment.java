package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

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

    @OnClick({R.id.tv_header_view, R.id.tv_footer_view})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_header_view:
                QsToast.show("点击headerView");
                break;
            case R.id.tv_footer_view:
                QsToast.show("点击footerView");
                break;
        }
    }

    @Override
    public QsListAdapterItem getListAdapterItem(int i) {
        return new CenterAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QsToast.show(position + "");
        super.onItemClick(parent, view, position, id);
    }


    /**
     * headerVIew 会作为ListView的一个Item，在 {@link QsListFragment#onItemClick(AdapterView, View, int, long) position 的值需要加一}
     * @return
     */
    @Override
    public int getHeaderLayout() {
       return R.layout.view_header_layout;
    }

    @Override
    public int getFooterLayout() {
        return R.layout.view_footer_layout;
    }
}
