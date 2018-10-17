package com.supe.supertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.mvp.QsActivity;

public class MainActivity extends QsActivity {

    @ThreadPoint(ThreadType.HTTP)
    private void init1(){
    }

    @Override
    public void initData(Bundle bundle) {

    }


}
