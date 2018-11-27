package com.supe.supertest.common.wdiget.bookpage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;

/**
 * @Author yinzh
 * @Date 2018/11/26 16:10
 * @Description
 */
@Entity
public class BookChapterBean implements Serializable {
    private static final long serialVersionUID = 56423411313L;
    /**
     * title : 第一章 他叫白小纯
     * link : http://read.qidian.com/chapter/rJgN8tJ_cVdRGoWu-UQg7Q2/6jr-buLIUJSaGfXRMrUjdw2
     * unreadble : false
     */
    //链接是唯一的
    private String link;

    private String title;

    //所属的下载任务
    @Index
    private String taskName;
    //所属的书籍
    @Index
    private String bookId;

    private boolean unreadble;

    @Generated(hash = 1921381654)
    public BookChapterBean(String link, String title, String taskName, String bookId,
                           boolean unreadble) {
        this.link = link;
        this.title = title;
        this.taskName = taskName;
        this.bookId = bookId;
        this.unreadble = unreadble;
    }

    @Generated(hash = 853839616)
    public BookChapterBean() {
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean getUnreadble() {
        return this.unreadble;
    }

    public void setUnreadble(boolean unreadble) {
        this.unreadble = unreadble;
    }

}
