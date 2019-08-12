package com.supe.supertest.home;

import android.content.Context;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.SortActivity;
import com.supe.supertest.rxjava.RxTestActivity;
import com.supe.supertest.tablayout.TabLayoutActivity;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.fragment.QsFragment;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @Author yinzh
 * @Date 2018/10/17 20:30
 * @Description
 */
public class ShouYueFragment extends QsFragment {

//    @Bind(R.id.iv_image)
//    ImageView iv_image;

//    @Override
//    public int layoutId() {
//        return R.layout.user_fragment;
//    }

    @Bind(R.id.nested_scrollView)
    NestedScrollView nestedScrollView;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.iv_image)
    ImageView iv_image;

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;

    private IWXAPI api;
    private String WX_ID = "wxff6bbd5dfc9c8e7e";
    private String WX_SECRET = "e4cd86942b4de908ca415b9562e06345";

    @Override
    public int layoutId() {
        return R.layout.shouye_fragment;
    }


    @Override
    public void initData(Bundle bundle) {


        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(getContext(),WX_ID ,true);
        //将应用的appid注册到微信
        api.registerApp(WX_ID);



        new BadgeHelper(getContext())
                .setBadgeType(BadgeHelper.Type.TYPE_POINT)
                .setBadgeOverlap(false)
                .bindToTargetView(tv_title);


        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, i1, i2, i3) -> {
            int height = dp2Px(45, getContext());

            if (i1 <= 0) {
                tv_title.setText("教师资格证");
                tv_title.setAlpha(0);
            } else if (i1 > 0 && i1 < height) {
                tv_title.setText("教师资格证");
                //获取渐变率
                float scale = (float) i1 / height;
                //获取渐变数值
                float alpha = (1.0f * scale);
                tv_title.setAlpha(alpha);
            } else {
                tv_title.setAlpha(1f);
            }

        });
    }


    public int dp2Px(float dp, Context mContext) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                mContext.getResources().getDisplayMetrics());
    }

    @OnClick({R.id.tv_cancel, R.id.bt_rxJava})
    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                intent2Activity(SortActivity.class);
                break;
            case R.id.bt_rxJava:
//                intent2Activity(TabLayoutActivity.class);

                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                api.sendReq(req);

                break;
        }
    }
}
