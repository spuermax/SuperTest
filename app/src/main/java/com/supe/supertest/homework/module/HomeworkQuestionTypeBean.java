package com.supe.supertest.homework.module;

import java.io.Serializable;

/**
 * @Author yinzh
 * @Date 2020/3/18 16:40
 * @Description
 */
public enum HomeworkQuestionTypeBean implements Serializable {
    /**
     *  choice 多选
     *  determine 判断
     *  essay 问答
     *  fill 填空
     *  material 材料
     *  single_choice 单选
     *  uncertain_choice 不定项
     */
    choice("多选题"),
    determine("判断题"),
    essay("问答题"),
    fill("填空题"),
    material("材料题"),
    single_choice("单选题"),
    uncertain_choice("不定项题"),
    empty("");

    public String name;

    HomeworkQuestionTypeBean(String name)
    {
        this.name = name;
    }

    public String title()
    {
        return this.name;
    }

    public static HomeworkQuestionTypeBean value(String typeName)
    {
        HomeworkQuestionTypeBean type;
        try {
            type =  valueOf(typeName);
        }catch (Exception e) {
            type = empty;
        }
        return type;
    }
}

