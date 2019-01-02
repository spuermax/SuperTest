package com.supe.supertest.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsActivity;


public class UserMessageActivity extends QsActivity {



    @Bind(R.id.mHobby)
    TextView mHobby;
    @Bind(R.id.Hobby)
    RelativeLayout Hobby;
    @Bind(R.id.mJob)
    TextView mJob;
    @Bind(R.id.Job)
    RelativeLayout Job;
    @Bind(R.id.Record)
    RelativeLayout Record;

    private Intent intent;
    private String hobby1;
    private String hobby;
    private String job;
    private String tall;

    @Override
    public int layoutId() {
        return R.layout.activity_user_message;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @OnClick({ R.id.Hobby, R.id.Job})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.Hobby:
                Intent intent = new Intent(UserMessageActivity.this, HobbyActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.Job:
                Intent intent1 = new Intent(UserMessageActivity.this, JobActivity.class);
                intent1.putExtra("id", 1);
                intent1.putExtra("tag", "job");
                startActivityForResult(intent1, 1);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            hobby = data.getStringExtra("hobby");
            mHobby.setText(hobby);
        } else if (resultCode == 20) {
            job = data.getStringExtra("job");
            mJob.setText(job);
        }
    }
}
