package com.supe.supertest.common.wdiget.bookpage;

import com.supe.supertest.common.wdiget.bookpage.show.ShowLine;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/11/26 16:15
 * @Description 当前页的详细数据
 */
public class TxtPage {
    int position;
    String title;
    int titleLines; //当前 lines 中为 title 的行数。
    List<String> lines;

    public List<ShowLine> showLines;// 当前页的行数


}
