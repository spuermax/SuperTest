package com.supe.supertest.nestedscrolling;

import android.os.Bundle;

import com.supe.supertest.R;
import com.supe.supertest.nestedscrolling.view.ScrollViewFrameLayout;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author yinzh
 * @Date 2019/2/26 16:09
 * @Description
 */
public class NestedScrollingActivity extends QsActivity{
    @Bind(R.id.scroll_frame)
    ScrollViewFrameLayout frameLayout;

    @Override
    public int layoutId() {
        return R.layout.activity_nested_scrolling;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    public String get(){
        String url = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        //            response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {

             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {

             }
         });
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
