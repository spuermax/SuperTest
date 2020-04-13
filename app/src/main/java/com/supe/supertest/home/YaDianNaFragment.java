package com.supe.supertest.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.supe.supertest.MainActivity;
import com.supe.supertest.R;
import com.supe.supertest.TestActivityA;
import com.supe.supertest.common.button.BaseFloatDailog;
import com.supe.supertest.common.button.FloatItem;
import com.supe.supertest.home.adapter.YDNAdapter;
import com.supe.supertest.home.presenter.YaDianNaPresenter;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.fragment.QsPullListFragment;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2018/10/17 20:32
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
    public void onResume() {
        super.onResume();
        L.i("MoveView", "onResume");
    }

    BaseFloatDailog mBaseFloatDailog ;
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        QsToast.show(position + "");
//        getPresenter().requestAuthCode();
        intent2Activity(MainActivity.class);

//        ARouter.getInstance().build("/supe/supertest")
//                .withString("name","SuperMax")
//                .withInt("age",18)
//                .navigation();
    }
}
