package com.supe.supertest.question.module;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/8/13 15:23
 * @Description
 */
public class HomeworkItemResultBean {
    public int id;
    public int itemId;
    public int homeworkId;
    public int homeworkResultId;
    public int questionId;
    public int questionParentId;
    public String status; //答题结果
    public String teacherSay;
    public String questionType;

    public List<HomeworkItemResultBean> items;
    public ArrayList<String> answer; //我的结果
}
