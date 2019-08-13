package com.supe.supertest;

import android.os.Bundle;
import android.view.View;

import com.supe.supertest.question.QuestionActivity;
import com.supe.supertest.rxjava.RxJavaActivity;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @Author yinzh
 * @Date 2019/8/13 09:43
 * @Description
 */
public class TestPagerActivity extends QsActivity {


    private IWXAPI api;
    private String WX_ID = "wxff6bbd5dfc9c8e7e";
    private String WX_SECRET = "e4cd86942b4de908ca415b9562e06345";
    @Override
    public int layoutId() {
        return R.layout.activity_testpager;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(getContext(),WX_ID ,true);
        //将应用的appid注册到微信
        api.registerApp(WX_ID);

    }

    @OnClick({R.id.tv_rxJava, R.id.tv_login, R.id.tv_question})
    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.tv_rxJava:
                intent2Activity(RxJavaActivity.class);
                break;
            case R.id.tv_login:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                api.sendReq(req);
                break;
            case R.id.tv_question:
                intent2Activity(QuestionActivity.class);
                break;
        }
    }
}
