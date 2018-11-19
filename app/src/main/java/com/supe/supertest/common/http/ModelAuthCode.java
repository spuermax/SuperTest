package com.supe.supertest.common.http;

import com.supe.supertest.common.model.BaseModel;

/*
 * @Author yinzh
 * @Date   2018/10/20 16:38
 * @Description
 */
public class ModelAuthCode extends BaseModel {

    public DataBean data;
    public MessageBean message;

    public static class DataBean extends BaseModel {
        public String token;
        public String headImage;
        public String nickName;
        public String userName;
        public int userId;
        public String phone;
        public int isTeacher;
        public int isMember;
    }

    public static class MessageBean extends BaseModel {
        public String errinfo;
        public int errcode;
    }

}
