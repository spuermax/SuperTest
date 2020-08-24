package com.supe.supertest;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.supe.supertest.common.utils.UrlUtils;
import com.supermax.base.QsApplication;
import com.supermax.base.common.http.HttpBuilder;

import androidx.multidex.MultiDex;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @Author yinzh
 * @Date 2018/10/17 10:43
 * @Description
 *      Caused by: java.lang.ClassNotFoundException: Didn't find class "io.realm.Realm" on path: DexPathList[[zip file "/data/app/com.supe.supertest-qdKeJ1bmfT86GsNxdndZuQ==/base.apk"],nativeLibraryDirectories=[/data/app/com.supe.supertest-qdKeJ1bmfT86GsNxdndZuQ==/lib/arm64, /data/app/com.supe.supertest-qdKeJ1bmfT86GsNxdndZuQ==/base.apk!/lib/arm64-v8a, /system/lib64, /product/lib64]]
 */
public class SuperApplication extends QsApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        LeakCanary.install(this);

//        CrashReport.initCrashReport(this, "3a7015de5a", false);

        if (isApkDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }


//        FeedbackAPI.init(this, "27704404","ad8f16ef360fea53aef8005c25b6cbfc ");

        ARouter.init(this);

//        initRealm();
    }

    /**
     * 初始化 数据库
     */
    private void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库
                .build();

        Realm.setDefaultConfiguration(config);
    }

    /**
     * 每次打正式包，必须把  true改为false
     * true开启日志
     * false关闭日志
     */
    @Override
    public boolean isLogOpen() {
        return true;
    }

    @Override
    public void initHttpAdapter(HttpBuilder httpBuilder) {
        String token = null;
        token = token == null ? "" : token;
        httpBuilder.setTerminal(UrlUtils.getCurrentUrl());
        httpBuilder.addHeader("token", token);
    }


    @Override
    public int loadingLayoutId() {
        return R.layout.layout_loading;
    }

    @Override
    public int emptyLayoutId() {
        return R.layout.layout_empty;
    }

    @Override
    public int errorLayoutId() {
        return R.layout.layout_error;
    }

    private boolean isApkDebug() {
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
