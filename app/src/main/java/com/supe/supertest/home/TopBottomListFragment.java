package com.supe.supertest.home;

import android.os.Bundle;

import com.supe.supertest.home.adapter.TopBottomAdapter;
import com.supe.supertest.home.presenter.TopBottomPresenter;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.fragment.QsTopBottomLoadListFragment;

/**
 * @Author yinzh
 * @Date 2019/4/3 14:23
 * @Description
 */
public class TopBottomListFragment extends QsTopBottomLoadListFragment<TopBottomPresenter, Item>{

    @Override
    public QsListAdapterItem getListAdapterItem(int type) {
        return new TopBottomAdapter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().requestData(false);
    }

    @Override
    public void onTopLoading() {
        QsToast.show("111Top");
    }

    @Override
    public void onBottomLoading() {
        QsToast.show("111Bottom");
    }


}
