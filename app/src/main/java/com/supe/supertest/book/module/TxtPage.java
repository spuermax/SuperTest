package com.supe.supertest.book.module;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/5/14 14:56
 * @Description
 */
public class TxtPage {
    public int position;
    public String title;
    public int titleLines; //当前 lines 中为 title 的行数。
    public List<String> lines;
    public List<String> linesChange;
    public List<ShowLine> showLines;// 当前页的行数
    public String sentence;//章节第一句
}

