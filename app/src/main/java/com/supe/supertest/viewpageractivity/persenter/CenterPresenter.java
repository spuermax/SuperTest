package com.supe.supertest.viewpageractivity.persenter;

import com.supe.supertest.common.SuperPresenter;
import com.supe.supertest.viewpageractivity.fragment.CenterFragment;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;

import java.util.ArrayList;

/*
 * @Author yinzh
 * @Date   2018/10/17 16:59
 * @Description
 */
public class CenterPresenter extends SuperPresenter<CenterFragment>{

    @ThreadPoint(ThreadType.HTTP)
    public void requestData(){
        ArrayList list = new ArrayList();
        for (int i = 0; i < 20 ; i ++ ){
            Item item =new Item();
            item.name = "卡布奇诺——狙";
            item.age = i;
            list.add(item);
        }

        getView().setData(list);
    }
}
