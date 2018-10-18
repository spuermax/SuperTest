package com.supe.supertest.home.presenter;

import android.os.SystemClock;

import com.supe.supertest.common.SuperPresenter;
import com.supe.supertest.home.YaDianNaFragment;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.util.ArrayList;

/*
 * @Author yinzh
 * @Date   2018/10/18 14:04
 * @Description
 */
public class YaDianNaPresenter extends SuperPresenter<YaDianNaFragment> {
    private int page;

    @ThreadPoint(ThreadType.HTTP)
    public void requestData(boolean isLoadMore) {

        if (isLoadMore) {
            L.i(initTag(),"page ==== "+ page+ "====isLoadMore===" + isLoadMore);
            if (page < 2) return;
            ArrayList<Item> list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.name = "拿铁的男人";
                item.age = i;
                item.isLastPage();
                list.add(item);
            }
            page++;
            SystemClock.sleep(1000);
            getView().addData(list);
            paging(list.get(0));

        } else {
            L.i(initTag(),"page ==== "+ page + "====isLoadMore===" + isLoadMore);
            page = 1;
            ArrayList<Item>  list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.name = "拿铁的男人";
                item.age = i;
                list.add(item);
            }
            page = 2;
            getView().setData(list);
            paging(list.get(0));
        }


    }
}
