package com.supe.supertest.common.model;

import com.supermax.base.common.model.QsModel;

/*
 * @Author yinzh
 * @Date   2018/10/18 14:56
 * @Description
 */
public class BaseModel extends QsModel{
    /**
     * 200：成功
     * 400：输入错误
     * 401：token过期
     * 500：服务器异常
     */
    public String code;
    public String message;
    public int    totalSize;

    /**
     * 请求是否成功
     * 以现在的服务端逻辑，是没有失败一说的，只要有JSON串就算成功
     */
    public boolean isSuccess() {
        return true;
    }
}
