package com.supe.supertest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.supe.supertest.abactivity.ActionBarActivity;
import com.supe.supertest.common.utils.PermissionUtils;
import com.supe.supertest.common.utils.SettingUtil;
import com.supe.supertest.indicator.MagicIndicatorActivity;
import com.supe.supertest.viewpageractivity.ViewPagerActivity;
import com.supermax.base.common.aspect.ThreadAspect;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.common.permission.annotation.Permission;
import com.supermax.base.common.permission.annotation.PermissionCanceled;
import com.supermax.base.common.permission.annotation.PermissionDenied;
import com.supermax.base.common.permission.model.CancelModel;
import com.supermax.base.common.permission.model.DenyModel;
import com.supermax.base.common.utils.ImageHelper;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;
import com.supermax.base.mvp.fragment.QsHeaderViewPagerFragment;

import java.util.List;

public class MainActivity extends QsActivity {

    @Bind(R.id.iv_image)
    ImageView iv_image;

    @Permission(value = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,//
            Manifest.permission.ACCESS_FINE_LOCATION},
            requestCode = 1)
    @Override
    public void initData(Bundle bundle) {
        init1();
        init2();
        init3();
        init4();

        testGlide();
        testImageHelper();


    }

    private void testGlide() {
        //=======
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_left_arrow_black)//占位图
                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁止Glide 缓存
                .error(R.drawable.ake)//异常占位图
                .skipMemoryCache(true)//内存缓存禁止
//                .override(Target.SIZE_ORIGINAL) //加载原始图片的大小
                .override(200,100);//指定大小


        Glide.with(this)
//                .asBitmap()
                .asGif()
                .load("http://guolin.tech/book.png")
                .apply(options)
                .into(iv_image);
    }

    private void testImageHelper() {
        // ==========================
        QsHelper.getInstance().getImageHelper().createRequest(this)
                .load("http://guolin.tech/book.png")
                .placeholder(R.mipmap.ic_left_arrow_black)//占位图
                .noDiskCache()//禁止Glide 缓存
                .noMemoryCache()//内存缓存禁止
                .error(R.drawable.ake)//异常占位图
                .resize(200,100)//指定大小(加载原始尺寸。。)
                .into(iv_image, new ImageHelper.ImageRequestListener() {
                    @Override
                    public void onLoadFailed(String s) {

                    }

                    @Override
                    public void onSuccess(Drawable drawable, Object o) {

                    }
                });
    }

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @ThreadPoint(ThreadType.HTTP)
    public void init1() {
        L.i(initTag(), "thread ==" + Thread.currentThread().getName() + " is == " + Thread.currentThread().getId());
    }

    @ThreadPoint(ThreadType.WORK)
    public void init2() {
        L.i(initTag(), "thread ==" + Thread.currentThread().getName() + " is == " + Thread.currentThread().getId());
    }

    @ThreadPoint(ThreadType.SINGLE_WORK)
    public void init3() {
        L.i(initTag(), "thread ==" + Thread.currentThread().getName() + " is == " + Thread.currentThread().getId());
    }

    @ThreadPoint(ThreadType.MAIN)
    public void init4() {
        L.i(initTag(), "thread ==" + Thread.currentThread().getName() + " is == " + Thread.currentThread().getId());
    }


    @OnClick({R.id.bt_thread, R.id.bt_ABActivity, R.id.bt_ViewPager, R.id.bt_ViewPagerA, R.id.bt_magic_indicator})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_thread:
                init1();
                init2();
                init3();
                init4();
                QsToast.show("Thead");
                break;
            case R.id.bt_ABActivity:
                intent2Activity(ActionBarActivity.class);
                QsToast.show("ABActivity");
                break;
            case R.id.bt_ViewPager:
                intent2Activity(ViewPagerActivity.class);
                QsToast.show("ViewPager");
                break;
            case R.id.bt_ViewPagerA:
                QsToast.show("ViewPagerA");
                break;
            case R.id.bt_magic_indicator:
                intent2Activity(MagicIndicatorActivity.class);
                break;
        }
    }


    /**
     * 设置状态栏透明
     *
     * @return
     */
    @Override
    public boolean isTransparentStatusBar() {
        return true;
    }

    /**
     * 设置是否透明虚拟键
     *
     * @return
     */
    @Override
    public boolean isTransparentNavigationBar() {
        return true;
    }


    @PermissionDenied
    public void denyPermission(DenyModel model) {
        if (model.getRequestCode() == 1) {
            List<String> denyList = model.getDenyList();
            if (denyList != null && denyList.size() > 0) {
                String message = PermissionUtils.getPermissionDialogMessage(denyList);
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage(message)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SettingUtil.go2Setting(QsHelper.getInstance().getApplication());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        }
    }


    @PermissionCanceled
    public void cancelPermission(CancelModel model) {
        if (model == null) return;
        switch (model.getRequestCode()) {
            case 1:
                List<String> denyList = model.getDenyList();
                if (denyList != null && denyList.size() > 0) {
                    QsToast.show(PermissionUtils.getPermissionDialogMessage(denyList));
                }
                break;
        }
    }

}
