package com.supe.supertest.common.utils;

import com.supermax.base.common.utils.QsHelper;

/*
 * @Author yinzh
 * @Date   2018/10/20 16:22
 * @Description
 */
public class UrlUtils {
    private static String URL_OFFLINE = "http://39.105.71.165/";
    private static String URL_OFFLINE1 = "http://10.16.135.8";// lxl 测试端口号
    private static String URL_OFFLINE2 = "http://10.16.135.8:8083/swagger-ui.html#!/live/pushUrlUsingPOST";// lxl 测试端口号

    private static final String URL_ONLINE = "http://api.grapedu.cn/api/grapedu";//----03--31


    public static String getCurrentUrl() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? URL_OFFLINE : URL_OFFLINE;
    }
}
