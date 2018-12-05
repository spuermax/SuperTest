package com.supe.supertest;

import com.supe.supertest.common.utils.UrlUtils;
import com.supermax.base.QsApplication;
import com.supermax.base.common.http.HttpBuilder;

/**
 * @Author yinzh
 * @Date   2018/10/17 10:43
 * @Description
 */
public class SuperApplication extends QsApplication{


    /**
     * 每次打正式包，必须把  true改为false
     * true开启日志
     * false关闭日志
     */
    @Override
    public boolean isLogOpen() {
        return true;
    }

    @Override
    public void initHttpAdapter(HttpBuilder httpBuilder) {
        String token = null;
        token = token == null ? "" : token;
        httpBuilder.setTerminal(UrlUtils.getCurrentUrl());
        httpBuilder.addHeader("token", token);
    }
}
