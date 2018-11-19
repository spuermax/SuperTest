package com.supe.supertest.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.supe.supertest.R;
import com.supe.supertest.home.adapter.YDNAdapter;
import com.supe.supertest.home.presenter.YaDianNaPresenter;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.model.QsModel;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.fragment.QsFragment;
import com.supermax.base.mvp.fragment.QsPullListFragment;

/*
 * @Author yinzh
 * @Date   2018/10/17 20:32
 * @Description
 */
public class YaDianNaFragment extends QsPullListFragment<YaDianNaPresenter, Item> {

    @Override
    public void initData(Bundle bundle) {
        getPresenter().requestData(false);
    }

    @Override
    public QsListAdapterItem<Item> getListAdapterItem(int i) {
        return new YDNAdapter();
    }

    @Override
    public void onRefresh() {
        QsToast.show("刷新");
        getPresenter().requestData(false);
    }

    @Override
    public void onLoad() {
        QsToast.show("加载更多");
        getPresenter().requestData(true);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        QsToast.show(position + "");
        getPresenter().requestAuthCode();
    }
}
