package com.supe.supertest.viewpageractivity.model;

import android.widget.BaseAdapter;

import com.supe.supertest.common.model.BaseModel;

/*
 * @Author yinzh
 * @Date   2018/10/17 16:47
 * @Description
 */
public class Item extends BaseModel{
    public String name;
    public int age;
    public boolean isLast;

    @Override
    public boolean isLastPage() {
        return isLast;
    }
}
