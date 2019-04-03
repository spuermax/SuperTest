package com.supe.supertest.home.presenter;

import android.os.SystemClock;

import com.supe.supertest.common.SuperPresenter;
import com.supe.supertest.common.http.LoginApi;
import com.supe.supertest.common.http.ModelAuthCode;
import com.supe.supertest.home.TopBottomListFragment;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yinzh
 * @Date 2019/4/3 14:29
 * @Description
 */
public class TopBottomPresenter extends SuperPresenter<TopBottomListFragment> {
    private int page;

    @ThreadPoint(ThreadType.HTTP)
    public void requestData(boolean isLoadMore) {

        if (isLoadMore) {
            L.i(initTag(), "page ==== " + page + "====isLoadMore===" + isLoadMore);
            if (page < 2) return;
            ArrayList<Item> list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.name = "拿铁的男人";
                if (page > 5) item.isLast = true;
                item.age = i;
                item.isLastPage();
                list.add(item);
            }
            page++;
            SystemClock.sleep(1000);
            getView().addTopData(list);
            paging(list.get(0));

        } else {
            L.i(initTag(), "page ==== " + page + "====isLoadMore===" + isLoadMore);
            page = 1;
            ArrayList<Item> list = new ArrayList();
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

    @ThreadPoint(ThreadType.HTTP)
    public void requestAuthCode() {
        LoginApi loginApi = createHttpRequest(LoginApi.class, "Login_Auth_code");
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13783141492");
        map.put("type", "1");
        if (loginApi == null) {
            L.i(initTag(), "Login is null");
        } else {
            L.i(initTag(), "Login not is null");
        }
        ModelAuthCode authCode = loginApi.getAuthCode(map);
        if (authCode != null) {
            L.i(initTag(), "我擦，来了吗");
            L.i(initTag(), authCode.toString());
        }

    }
}
