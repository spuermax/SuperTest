package com.supe.supertest.rxjava;

import android.os.Bundle;
import android.util.Log;

import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.indicator.model.ScrollState;
import com.supermax.base.mvp.QsActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @Author yinzh
 * @Date 2018/12/25 13:04
 * @Description
 */
public class RxJavaActivity extends QsActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void initData(Bundle bundle) {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }
}
