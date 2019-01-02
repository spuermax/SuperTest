package com.supe.supertest.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsActivity;


public class JobActivity extends QsActivity {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.save)
    TextView save;
    @Bind(R.id.my_job)
    EditText myJob;
    @Bind(R.id.image_undo)
    ImageView imageUndo;
    private Intent intent;
    private int mID;
    private String mTag;


    @Override
    public int layoutId() {
        return R.layout.activity_job;
    }

    @Override
    public void initData(Bundle bundle) {
        intent = getIntent();
    }

    @OnClick({R.id.save})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                        String string = myJob.getText().toString();
                        Log.e("string--",string);
                        intent.putExtra("job", string);
                        setResult(20, intent);

                        SharedPreferences sharedPreferences = getSharedPreferences("job.db", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("job", string);
                        edit.commit();
                        finish();
                break;
        }
    }
}
