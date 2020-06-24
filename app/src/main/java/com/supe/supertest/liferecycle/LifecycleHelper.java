package com.supe.supertest.liferecycle;

import com.supermax.base.common.log.L;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @Author yinzh
 * @Date 2020/4/17 13:43
 * @Description 接口，用于将一个类标记为LifecycleObserver
 * 接口里面没有任何方法，而是依赖于 {@lOnLifecycleEvent}注释方法
 */
public class LifecycleHelper implements LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume(){
        L.i("RRRRRRRR","LifecycleHelper = onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){
        L.i("RRRRRRRR","LifecycleHelper = onStop");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestory(){
        L.i("RRRRRRRR","LifecycleHelper = onDestory");
    }
}
