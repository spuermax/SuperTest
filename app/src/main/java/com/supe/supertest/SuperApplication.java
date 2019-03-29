package com.supe.supertest;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.supe.supertest.common.utils.UrlUtils;
import com.supermax.base.QsApplication;
import com.supermax.base.common.http.HttpBuilder;

/**
 * @Author yinzh
 * @Date 2018/10/17 10:43
 * @Description
 */
public class SuperApplication extends QsApplication {

    private RefWatcher refWatcher = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        refWatcher = LeakCanary.install(this);

        if(isApkDebug()){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
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
