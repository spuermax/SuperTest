package com.supe.supertest.common.http;

import com.supermax.base.common.aspect.FormBody;
import com.supermax.base.common.aspect.POST;

import java.util.Map;

/**
 * @Author yinzh
 * @Date   2018/10/20 16:35
 * @Description
 */public interface LoginApi {

    /**
     * 获取验证码
     * @param map
     * @return
     */
    @POST("/api/getAuthCode")
    ModelAuthCode getAuthCode(@FormBody Map<String, String> map);

}
