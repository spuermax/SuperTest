package com.supe.supertest.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.supe.supertest.R;
import com.supe.supertest.common.model.req.ModelChapterContentReq;
import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.common.wdiget.MagicalLabelView;
import com.supe.supertest.common.wdiget.Utils;
import com.supermax.base.common.utils.ImageHelper;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.utils.ScreenHelper;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.fragment.QsFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2019/1/2 17:31
 * @Description
 */
public class MagicalLabelFragment extends QsFragment {

    private boolean isShow = false;

    @Bind(R.id.magical_label)
    MagicalLabelView magical_label;
    @Bind(R.id.bt_show)
    Button bt_show;
    @Bind(R.id.iv_image1)
    ImageView iv_image1;
    @Bind(R.id.iv_image2)
    ImageView iv_image2;
    @Bind(R.id.iv_image3)
    ImageView iv_image3;
    private ArrayList<String> label = new ArrayList<>();


    @Override
    public int layoutId() {
        return R.layout.fragment_margiclabel;
    }

    @Override
    public void initData(Bundle bundle) {
        label.add("全部");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        label.add("类别1");
        magical_label.setLabels(label);
        magical_label.setMaxLines(2);


        ModelChapterContentReq req = new ModelChapterContentReq();

        WeakReference<ModelChapterContentReq> reference = new WeakReference<ModelChapterContentReq>(req);

//        QsHelper.getInstance().getImageHelper().createRequest(this)
//                .load("https://manhua.qpic.cn/vertical/0/07_22_36_afe651da2ab940d0e257a1ec894bd992_1504795010150.jpg/420")
//                .roundedCorners(ScreenUtils.dpToPx(5))
//                .into(iv_image1);


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_left_arrow_black);


        iv_image1.setImageBitmap(bitmap1);
        iv_image2.setImageBitmap(bitmap2);
        iv_image3.setImageBitmap(bitmap3);


    }

    @OnClick({R.id.bt_show})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_show:
                intent2Activity(ChapterBitmapActivity.class);
                if (!isShow) {
                    magical_label.setMaxLines(6);
                    bt_show.setText("收起");
                    isShow = true;
                } else {
                    magical_label.setMaxLines(2);
                    bt_show.setText("展开");
                    isShow = false;
                }
                break;
        }
    }
}
