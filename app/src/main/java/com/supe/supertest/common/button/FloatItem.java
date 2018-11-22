package com.supe.supertest.common.button;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @Author yinzh
 * @Date 2018/11/21 15:36
 * @Description
 */
public class FloatItem {
    public String title;
    public int titleColor = Color.BLACK;
    public int bgColor = Color.WHITE;
    public Bitmap icon;
    public String dotNum = null;

    public FloatItem(String title, int titleColor, int bgColor, Bitmap icon, String dotNum) {
        this.title = title;
        this.titleColor = titleColor;
        this.bgColor = bgColor;
        this.icon = icon;
        this.dotNum = dotNum;
    }

    public String getDotNum() {
        return dotNum;
    }


    public FloatItem(String title, int titleColor, int bgColor, Bitmap bitmap) {
        this.title = title;
        this.titleColor = titleColor;
        this.bgColor = bgColor;
        this.icon = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) return true;

        if (obj instanceof FloatItem) {
            FloatItem floatItem = (FloatItem) obj;
            return floatItem.title.equals(this.title);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "FloatItem{" +
                "title='" + title + '\'' +
                ", titleColor=" + titleColor +
                ", bgColor=" + bgColor +
                ", icon=" + icon +
                ", dotNum='" + dotNum + '\'' +
                '}';
    }
}
