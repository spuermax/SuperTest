package com.supe.supertest.common.wdiget.bookpage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author yinzh
 * @Date 2018/11/26 16:10
 * @Description
 */
@Entity
public class BookChapterBean implements Serializable {
    private static final long serialVersionUID = 56423411313L;

    /**
     * bookCode : 1004912299104101110120105100111110103
     * name : 红楼梦之测试数据1
     * id : 10
     * catalogue : [{"chapterId":"1","chapterName":"第1章"},{"chapterId":"2","chapterName":"第2章"},{"chapterId":"3","chapterName":"第3章"}]
     */

    private String bookCode;
    private String name;
    private int id;
    private String chapterId;
    private String chapterName;

    @Index
    private String bookId;

    @Generated(hash = 1022884783)
    public BookChapterBean(String bookCode, String name, int id, String chapterId, String chapterName, String bookId) {
        this.bookCode = bookCode;
        this.name = name;
        this.id = id;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.bookId = bookId;
    }

    @Generated(hash = 853839616)
    public BookChapterBean() {
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
