package com.supe.supertest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.toast.QsToast;

import java.util.HashMap;

/**
 * @Author yinzh
 * @Date 2019/3/13 11:13
 * @Description
 */
public class TestActivtyA extends Activity{
    HashMap hashMap = new HashMap();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QsToast.show("2222");
                Log.i("HashMap","我的Hash值是："+hash(1));
                Log.i("HashMap","我的Hash值是："+hash(2.0));
                Log.i("HashMap","我的Hash值是："+hash("789"));
                Log.i("HashMap","我的Hash值是："+hash("--123"));
                Log.i("HashMap","我的Hash值是："+hash(5));
            }
        };

        hashMap.put("","");

        findViewById(R.id.view).setOnClickListener(listener);




    }

     private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
