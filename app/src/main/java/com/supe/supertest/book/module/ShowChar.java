package com.supe.supertest.book.module;

import android.graphics.Point;

/**
 * @Author yinzh
 * @Date 2019/5/14 14:46
 * @Description
 */
public class ShowChar {
    public char charData;
    public boolean isSelected;
    public float x;
    public float y;
    public int id;
    public int sentenceId;//句ID

    public Point TopLeftPosition = null;
    public Point TopRightPosition = null;
    public Point BottomLeftPosition = null;
    public Point BottomRightPosition = null;

    public float charWidth = 0;

    @Override
    public String toString() {
        return "ShowChar [charData=" + charData + ", isSelected=" + isSelected + ", TopLeftPosition=" + TopLeftPosition
                + ", TopRightPosition=" + TopRightPosition + ", BottomLeftPosition=" + BottomLeftPosition
                + ", BottomRightPosition=" + BottomRightPosition + ", charWidth=" + charWidth + ", id="  + id+ ", sentenceId="  + sentenceId
                + "]";
    }

}

