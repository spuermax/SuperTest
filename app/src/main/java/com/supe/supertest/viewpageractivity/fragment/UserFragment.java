package com.supe.supertest.viewpageractivity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.supe.supertest.R;
import com.supe.supertest.viewpageractivity.adapter.UserAdapter;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supe.supertest.viewpageractivity.persenter.UserPresenter;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsRecycleAdapterItem;
import com.supermax.base.mvp.fragment.QsRecyclerFragment;

/*
 * @Author yinzh
 * @Date   2018/10/17 14:15
 * @Description
 */public class UserFragment extends QsRecyclerFragment<UserPresenter, Item> {

//    @Override
//    public int layoutId() {
//        return R.layout.user_fragment;
//    }

    @Override
    public void initData(Bundle bundle) {
        getPresenter().requestData();

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
    public QsRecycleAdapterItem<Item> getRecycleAdapterItem(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new UserAdapter(layoutInflater, viewGroup);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QsToast.show("" + position);
        super.onItemClick(parent, view, position, id);
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.view_header_layout;
    }

    @Override
    public int getFooterLayout() {
        return R.layout.view_footer_layout;
    }

}
