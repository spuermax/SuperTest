package com.supe.supertest.viewpageractivity.persenter;

import com.supe.supertest.common.SuperPresenter;
import com.supe.supertest.viewpageractivity.fragment.UserFragment;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;

import java.util.ArrayList;

/*
 * @Author yinzh
 * @Date   2018/10/17 19:54
 * @Description
 */
public class UserPresenter extends SuperPresenter<UserFragment> {
    @ThreadPoint(ThreadType.HTTP)
    public void requestData() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.name = "拿铁的可乐——含";
            item.age = i;
            list.add(item);
        }

        getView().setData(list);
    }

}
