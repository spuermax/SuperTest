package com.supe.supertest.common.wdiget.bookpage.show;

import android.graphics.Point;

/**
 * @Author yinzh
 * @Date 2018/11/28 14:12
 * @Description 字Bean
 */
public class ShowChar {
    public char charData;
    public boolean isSelected;
    public float x;
    public float y;
    public int id;//  句ID。

    @Override
    public String toString() {
        return "ShowChar [charData = " + charData + "isSelected = " + isSelected + "x = " + x
                + "y =" + y + "id = "+ id + "]";
    }

}
