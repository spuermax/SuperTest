package com.supe.supertest.rxjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.rxjava.view.NestRecyclerView;
import com.supe.supertest.rxjava.view.ParentRecyclerView;
import com.supe.supertest.rxjava.view.TestTextView;
import com.supermax.base.common.log.L;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @Author yinzh
 * @Date 2018/12/25 13:04
 * @Description
 */
public class RxJavaActivity extends QsActivity {

    @Bind(R.id.recycler)
    ParentRecyclerView recyclerView;

    List<String> data = new ArrayList<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public int layoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    public void initData(Bundle bundle) {
//        test();
        for (int i = 'A'; i < 'z'; i++) {
            data.add("" + (char) i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());

//        test();


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(100);
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer + 100;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
//                QsToast.show(integer + "");
            }
        });


        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(234);
                e.onComplete();
                L.i("RxJAva2","我是第二个事件的轮询");
            }
        });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(100);
                e.onComplete();
                L.i("RxJAva2","我是第一个事件的轮询");
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {

                if (integer == 100) {
                    return observable;
                }

                return null;
            }
        }).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.delay(3000, TimeUnit.MILLISECONDS);
            }
        }).takeUntil(new Predicate<Object>() {
            @Override
            public boolean test(Object integer) throws Exception {
                return true;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object integer) throws Exception {
                QsToast.show(integer.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(100);
                e.onComplete();
            }
        }).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.delay(5, TimeUnit.SECONDS);
            }
        }).takeUntil(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return true;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object integer) throws Exception {
//                QsToast.show(integer.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });





    }

    private Disposable disposable;

    private void test() {
        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                L.i("RxJavaTest", "onSubscribe" + "======current thread + " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String value) {
                L.i("RxJavaTest", "onNext" + value + "======current thread + " + Thread.currentThread().getName());
                if (value.equals("222")) {
                    disposable.dispose();
                    L.i("RxJavaTest", "onNext 我被取消执行了" + value);
                }

            }

            @Override
            public void onError(Throwable e) {
                L.i("RxJavaTest", "onError" + "======current thread + " + Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                L.i("RxJavaTest", "onComplete" + "======current thread + " + Thread.currentThread().getName());

            }
        };


        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(100);
//                L.i("RxJavaTest", "发送111" + "======current thread + " + Thread.currentThread().getName());
//                e.onNext("222");
//                L.i("RxJavaTest", "发送222" + "======current thread + " + Thread.currentThread().getName());
//                e.onNext("333");
//                L.i("RxJavaTest", "发送3333" + "======current thread + " + Thread.currentThread().getName());
//                e.onNext("444");
//                L.i("RxJavaTest", "发送333" + "======current thread + " + Thread.currentThread().getName());
//                e.onComplete();

            }
        });

        final int[] no = new int[1];

        observable.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {

                return no[0] + o;
            }
        }).flatMap(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(100 + no[0]);
                    }
                });
                return null;
            }
        }).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.delay(500, TimeUnit.MILLISECONDS);
            }
        }).takeUntil(new Predicate() {
            @Override
            public boolean test(Object o) throws Exception {
                return no[0] != 400;
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                QsToast.show(o.toString());
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

        private ArrayList<String> strings;
        private TwoAdapter adapter2;

        public Adapter() {
            super();
            strings = new ArrayList<>();
            strings.add("三科技大厦科技");
            strings.add("骚的脾气为文件");
            strings.add("我是第三个老哥");
            strings.add("我是第三个老哥");
            strings.add("我是第三个老哥");
            strings.add("我是第三个老哥");
            strings.add("l.kms.d,mflklmw");
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    RxJavaActivity.this).inflate(R.layout.item_home, viewGroup,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            adapter2 = new TwoAdapter(strings);
            myViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(RxJavaActivity.this));
            myViewHolder.recyclerView.setAdapter(adapter2);
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            NestRecyclerView recyclerView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                recyclerView = (NestRecyclerView) itemView.findViewById(R.id.recycler);
            }
        }

    }


    class TwoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        ArrayList<String> strings;

        public TwoAdapter(ArrayList<String> strings) {
            super(R.layout.item_home2, strings);
            this.strings = strings;
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
            helper.setOnClickListener(R.id.tv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QsToast.show("hha");
                    L.i("RecyclerView", "我是子TestTextView -------  setOnClickListener");
                    notifyItemChanged(1, 3);
                }
            });
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            TestTextView tv;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }


    @Override
    public boolean isTransparentStatusBar() {
        return true;
    }


}
