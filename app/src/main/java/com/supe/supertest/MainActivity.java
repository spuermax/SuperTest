package com.supe.supertest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.supe.supertest.abactivity.ActionBarActivity;
import com.supe.supertest.book.ReadBookActivity;
import com.supe.supertest.common.button.BaseFloatDailog;
import com.supe.supertest.common.button.FloatItem;
import com.supe.supertest.common.utils.PermissionUtils;
import com.supe.supertest.common.utils.SettingUtil;
import com.supe.supertest.common.wdiget.SonnyJackDragView;
import com.supe.supertest.indicator.MagicIndicatorActivity;
import com.supe.supertest.realm.TestModel;
import com.supe.supertest.test.LoginManager;
import com.supe.supertest.viewpageractivity.HomeActivity;
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
import com.supermax.base.mvp.QsABActivity;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends QsABActivity {

    @Bind(R.id.iv_image)
    ImageView iv_image;

    @Bind(R.id.draw_layout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    String HOME = "首页";
    String FEEDBACK = "客服";
    String MESSAGE = "消息";

    String[] MENU_ITEMS = {HOME, FEEDBACK, MESSAGE};

    private int[] menuIcons = new int[]{R.drawable.yw_menu_account, R.drawable.yw_menu_fb, R.drawable.yw_menu_msg};

    private SonnyJackDragView mSonnyJackDragView;




    @Permission(value = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//
            Manifest.permission.READ_EXTERNAL_STORAGE
    },
            requestCode = 1)
    @Override
    public void initData(Bundle bundle) {
        init1();
        init2();
        init3();
        init4();

        testGlide();
        testImageHelper();

        TestModel testModel = new TestModel();
        testModel.setAge("12");
        testModel.setAgeA(15);
        testModel.setName("yzh");


        Realm defaultInstance = Realm.getDefaultInstance();

        defaultInstance.insert(testModel);
        TestModel object = defaultInstance.createObject(TestModel.class);

        QsToast.show(object.getAge());



        LoginManager.getInstance(this).dealData();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < menuIcons.length; i++) {
            itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), String.valueOf(i + 1)));
        }
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


    @OnClick({R.id.bt_thread, R.id.bt_ABActivity, R.id.bt_ViewPager, R.id.bt_ViewPagerA, R.id.bt_magic_indicator, R.id.bt_bookPage, R.id.bt_rxJava})
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
                intent2Activity(HomeActivity.class);
                QsToast.show("ViewPager");
                break;
            case R.id.bt_ViewPagerA:
                QsToast.show("ViewPagerA");
                break;
            case R.id.bt_magic_indicator:
                intent2Activity(MagicIndicatorActivity.class);
                break;
            case R.id.bt_bookPage:
                intent2Activity(ReadBookActivity.class);
                break;
            case R.id.bt_rxJava:
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

    ArrayList<FloatItem> itemList = new ArrayList<>();
    private BaseFloatDailog mBaseFloatDailog;



    @Override
    protected void onResume() {
        super.onResume();

//
//        if (mBaseFloatDailog != null) return;
//
//        mBaseFloatDailog = new MyFloatDialog(this);
//        mBaseFloatDailog.show();

    }

    public void refreshDot() {
        for (FloatItem menuItem : itemList) {
            if (TextUtils.equals(menuItem.getTitle(), "我的")) {
                menuItem.dotNum = String.valueOf(8);
            }
        }
    }

    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }
}
