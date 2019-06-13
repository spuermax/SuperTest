package com.supe.supertest.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.fragment.QsFragment;
import com.supermax.base.mvp.fragment.QsPullFragment;

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


    @Override
    public int layoutId() {
        return R.layout.shouye_fragment;
    }


    @Override
    public void initData(Bundle bundle) {


        new BadgeHelper(getContext())
                .setBadgeType(BadgeHelper.Type.TYPE_POINT)
                .setBadgeOverlap(false)
                .bindToTargetView(tv_title);



        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                int height = dp2Px(45, getContext());

                if (i1 <= 0) {
                    tv_title.setText("SuperTest");
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

            }
        });
    }


    public int dp2Px(float dp, Context mContext) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                mContext.getResources().getDisplayMetrics());
    }
}
