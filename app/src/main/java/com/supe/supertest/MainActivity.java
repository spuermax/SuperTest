package com.supe.supertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.supermax.base.common.aspect.ThreadAspect;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.mvp.QsActivity;

public class MainActivity extends QsActivity {

    @Override
    public void initData(Bundle bundle) {
        init1();
        init2();
        init3();
        init4();
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
}
