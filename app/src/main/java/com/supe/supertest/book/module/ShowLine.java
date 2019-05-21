package com.supe.supertest.book.module;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/5/14 14:46
 * @Description
 */
public class ShowLine {
    public List<ShowChar> CharsData = null;

    public String getLineData() {
        String linedata = "";
        if (CharsData == null || CharsData.size() == 0) return linedata;
        for (ShowChar c : CharsData) {
            linedata = linedata + c.charData;
        }
        return linedata;
    }

    public float lintHeight;

    @Override
    public String toString() {
        return "ShowLine [CharsData=" + getLineData() + "]";
    }

}
