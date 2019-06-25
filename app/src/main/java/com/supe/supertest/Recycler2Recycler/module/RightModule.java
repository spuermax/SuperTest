package com.supe.supertest.Recycler2Recycler.module;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Author yinzh
 * @Date 2019/6/14 14:29
 * @Description
 */
public class RightModule implements MultiItemEntity {

    public int id;
    public String name;
    public int type;
    public String title;

    public static final int TITLE = 1;
    public static final int CONTENT = 2;


    @Override
    public int getItemType() {
        return type;
    }
}
