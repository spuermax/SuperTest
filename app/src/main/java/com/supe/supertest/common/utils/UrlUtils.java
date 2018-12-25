package com.supe.supertest.common.utils;

import com.supermax.base.common.utils.QsHelper;

/**
 * @Author yinzh
 * @Date   2018/10/20 16:22
 * @Description
 */
public class UrlUtils {
    private static String URL_OFFLINE = "http://39.105.71.165/";
    public final static String API_BASE_URL = "http://39.105.71.165:8769/";// 电子书章节端口


    public static String getCurrentUrl() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? API_BASE_URL : API_BASE_URL;
    }
}
