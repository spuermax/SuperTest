package com.supe.supertest.common.wdiget.bookpage.bean;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/11/26 16:44
 * @Description
 */
public class DetailBean <T> {
    private T detail;
    private List<CommentBean> bestComments;
    private List<CommentBean> comments;

    public DetailBean(T details, List<CommentBean> bestComments, List<CommentBean> comments) {
        this.detail = details;
        this.bestComments = bestComments;
        this.comments = comments;
    }

    public T getDetail() {
        return detail;
    }

    public List<CommentBean> getBestComments() {
        return bestComments;
    }

    public List<CommentBean> getComments() {
        return comments;
    }
}

