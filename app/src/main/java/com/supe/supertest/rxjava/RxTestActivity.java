package com.supe.supertest.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.supe.supertest.R;
import com.supermax.base.common.log.L;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * @Author yinzh
 * @Date 2019/6/22 11:37
 * @Description
 */
public class RxTestActivity extends QsActivity {
    private static final String TAG = "RxTestActivity_Log";

    @Override
    public int layoutId() {
        return R.layout.activity_rx_test;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.create, R.id.zip, R.id.map, R.id.flatmap, R.id.concatmap, R.id.doOnNext,
            R.id.filter, R.id.skip, R.id.take,
            R.id.just, R.id.interval, R.id.timer})
    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                create();
                break;
            case R.id.zip:
                zip();
                break;
            case R.id.map:
                map();
                break;
            case R.id.flatmap:
                flatMap();
                break;
            case R.id.concatmap:
                //concatMap作用和flatMap几乎一模一样，唯一的区别是它能保证事件的顺序
                break;
            case R.id.filter:
                filter();
                break;
            case R.id.skip:
                skip();
                break;
            case R.id.take:
                take();
                break;
            case R.id.interval:

                Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("兄弟");
                    }
                });


                Observable.interval(3, 2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                Log.e(TAG, "interval :" + aLong + getNowStrTime() + "\n");
                            }
                        });
                break;
            case R.id.timer:
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                L.i(TAG, "timer :" + aLong + getNowStrTime() + "\n");
                            }
                        });
                break;
            case R.id.just:
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("100");
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        loading();
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        loadingClose();
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        L.i(TAG, s);
                    }
                });
                break;


        }
    }

    private void create() {
        Observable.create((ObservableOnSubscribe<String>) e -> {
            e.onNext("我要开始干RxJava了");
            e.onNext("兄弟们");
            L.i(TAG, Thread.currentThread().getName() + "被观察者S");
        }).subscribe(s -> {
            L.i(TAG, s);
            L.i(TAG, Thread.currentThread().getName() + "观察者S");
        });

        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(100);
            e.onNext(200);
            e.onNext(300);
            L.i(TAG, Thread.currentThread().getName() + "被观察者I");

        }).subscribeOn(Schedulers.io())//被观察者在子线程
                .observeOn(AndroidSchedulers.mainThread())//观察者在主线程
                .subscribe(integer -> {
                    L.i(TAG, integer + "");
                    L.i(TAG, Thread.currentThread().getName() + "观察者I");
                });

    }


    /**
     * TAG:合并事件专用
     * Description：分别从两个上游事件中各取出一个组合，一个事件只能被使用一次，
     * 顺序严格按照事件发送的顺序，最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)
     */
    private void zip() {
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("兄弟，ZIP来了");
            }
        });

        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(100);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                L.i(TAG, s);
            }
        });
    }

    /**
     * 作用是对上游发送的每一个事件应用一个函数，使得每一个事件都按照指定的函数去变化
     */
    private void map() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("兄弟，MAP来了");
                e.onNext("兄弟，MAP又来了");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "over";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                L.i(TAG, s);
            }
        });
    }

    /**
     * FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables，
     * 然后将它们发射的时间合并后放进一个单独的Observable里.
     */
    private void flatMap() {
        //简单的转换Observable
        Observable.create((ObservableOnSubscribe<Integer>) e -> e.onNext(100))
                .flatMap((Function<Integer, ObservableSource<String>>) integer -> Observable.just("兄弟，记住了，我今年" + integer))
                .subscribe(s -> L.i(TAG, s));


        //下一个Observable 依赖于上一个Observable
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("兄弟");
            }
        }).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext(s + "经过了FlatMap的加工");
                    }
                });
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object s) throws Exception {
                L.i(TAG, s.toString());
            }
        });

    }

    /**
     * 让订阅者在接收到数据前干点事情的操作符。
     * 允许我们在每次输出一个元素之前额外的做些其他事情。
     */
    private void doNoNext() {
        Observable.just(100, 200, 300, 400)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        switch (integer) {
                            case 100:
                                L.i(TAG, "到100了，兄弟们");
                                break;
                            case 200:
                                L.i(TAG, "到200了，兄弟们");
                                break;
                            case 300:
                                L.i(TAG, "到300了，兄弟们");
                                break;
                            case 400:
                                L.i(TAG, "到400了，兄弟们");
                                break;
                        }
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                L.i(TAG, integer + "Over");
            }
        });
    }

    /**
     * 过滤操作符，取正确的值
     */
    private void filter() {
        Observable.just(1, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                L.i(TAG, "filter : " + integer + "\n");
            }
        });
    }

    /**
     * 接受一个long型参数，代表跳过多少个数目的事件再开始接收
     */
    private void skip() {
        Observable.just(100, 200, 300, 400, 500)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        L.i(TAG, integer + "");
                    }
                });
    }

    /**
     * 用于指定订阅者最多收到多少数据
     */
    private void take() {
        Flowable.just(100, 200, 300, 400, 500)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        L.i(TAG, integer + "\n");
                    }
                });
    }

    public static String getNowStrTime() {
        long time = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
