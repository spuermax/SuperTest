package com.supe.supertest.rxjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supe.supertest.R;
import com.supe.supertest.common.utils.RxUtils;
import com.supe.supertest.rxjava.view.ChildRecyclerView;
import com.supe.supertest.rxjava.view.NestRecyclerView;
import com.supe.supertest.rxjava.view.ParentRecyclerView;
import com.supe.supertest.rxjava.view.TestTextView;
import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.indicator.model.ScrollState;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;

import java.util.ArrayList;
import java.util.List;

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


        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("111");
                L.i("RxJavaTest", "发送111" + "======current thread + " + Thread.currentThread().getName());
                e.onNext("222");
                L.i("RxJavaTest", "发送222" + "======current thread + " + Thread.currentThread().getName());
                e.onNext("333");
                L.i("RxJavaTest", "发送3333" + "======current thread + " + Thread.currentThread().getName());
                e.onNext("444");
                L.i("RxJavaTest", "发送333" + "======current thread + " + Thread.currentThread().getName());
                e.onComplete();

            }
        });


        observable
                .map(new Function() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())//被观察者在子线程
                .observeOn(AndroidSchedulers.mainThread())// 观察者在主线程
                .subscribe(observer);

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
