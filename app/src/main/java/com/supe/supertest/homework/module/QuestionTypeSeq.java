package com.supe.supertest.homework.module;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2020/3/18 16:39
 * @Description
 */
public class QuestionTypeSeq implements Serializable {

    public int id;
    public int testId;
    public int seq;
    public int questionId;
    public HomeworkQuestionTypeBean questionType;
    public int parentId;
    public double score;
    public double missScore;

//    public Question question;
    public ArrayList<QuestionTypeSeq> items;
}
