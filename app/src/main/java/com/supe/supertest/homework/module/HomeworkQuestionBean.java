package com.supe.supertest.homework.module;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/8/13 14:18
 * @Description
 */
public class HomeworkQuestionBean implements MultiItemEntity {

    private int id;
    public HomeworkQuestionTypeBean type; //题型
    private String stem; //题干
    private ArrayList<String> answer; //正确答案
    private String analysis; //解析
    private ArrayList<String> metas; //选项
    private String difficulty;
    private HomeworkQuestionBean parent;
    private HomeworkItemResultBean result; //结果
    private int itemType;

    private List<HomeworkQuestionBean> items; //材料题子题

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public HomeworkItemResultBean getResult() {
        return result;
    }

    public void setResult(HomeworkItemResultBean result) {
        this.result = result;
    }

    public List<HomeworkQuestionBean> getItems() {
        return items;
    }

    public void setItems(List<HomeworkQuestionBean> items) {
        this.items = items;
    }

    public HomeworkQuestionBean getParent() {
        return parent;
    }

    public void setParent(HomeworkQuestionBean parent) {
        this.parent = parent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public HomeworkQuestionTypeBean getType() {
        return type;
    }

    public void setType(HomeworkQuestionTypeBean type) {
        this.type = type;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getStem() {
        return this.stem;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public ArrayList<String> getAnswer() {
        return this.answer;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnalysis() {
        return this.analysis;
    }

    public void setMetas(ArrayList<String> metas) {
        this.metas = metas;
    }

    public ArrayList<String> getMetas() {
        return this.metas;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    @Override
    public int getItemType() {
        return itemType;
    }



}
