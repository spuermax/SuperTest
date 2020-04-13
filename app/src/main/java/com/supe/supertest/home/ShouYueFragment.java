package com.supe.supertest.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.supe.supertest.R;
import com.supe.supertest.Recycler2Recycler.SortActivity;
import com.supe.supertest.TestPagerActivity;
import com.supe.supertest.paint.PaintActivity;
import com.supe.supertest.rxjava.RxTestActivity;
import com.supe.supertest.tablayout.TabLayoutActivity;
import com.supermax.base.common.utils.CommonUtils;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
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

    @Override
    public int layoutId() {
        return R.layout.shouye_fragment;
    }


    @Override
    public void initData(Bundle bundle) {

//        showTwo();


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


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                int height = dp2Px(45, getContext());



            }
        });


//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_header_layout, linearLayout);
//       PopupWindow popupWindow = new PopupWindow(view,
//                ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(getActivity(),108), true);
//
//        nestedScrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//
//                // 设置弹窗外可点击
//                popupWindow.setOutsideTouchable(false);
//                popupWindow.setFocusable(false);
//                popupWindow.showAtLocation(nestedScrollView, Gravity.BOTTOM, 0, CommonUtils.dip2px(getContext(),10));
//            }
//        });
    }

    private AlertDialog.Builder builder;

    private void showTwo() {

        builder = new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher).setTitle("最普通dialog")
                .setMessage("我是最简单的dialog").setPositiveButton("确定（积极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(getActivity(), "确定按钮", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消（消极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(getActivity(), "关闭按钮", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });

        builder.setCancelable(false);
        builder.create().show();
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
//                intent2Activity(SortActivity.class);
                intent2Activity(PaintActivity.class);

                break;
            case R.id.bt_rxJava:
                intent2Activity(TestPagerActivity.class);
//                FeedbackAPI.openFeedbackActivity();
                break;
        }
    }
}
