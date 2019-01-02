package com.supe.supertest.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsActivity;


public class HobbyActivity extends QsActivity {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.save)
    TextView save;
    @Bind(R.id.my_hobby)
    EditText myHobby;
    @Bind(R.id.image_undo)
    ImageView imageUndo;
    private Intent intent;
    private int mID;
    private String mTag;

    @Override
    public int layoutId() {
        return R.layout.activity_hobby;
    }

    @Override
    public void initData(Bundle bundle) {
        intent = getIntent();
    }


    @OnClick({R.id.save})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                Toast.makeText(this, "twgbvaf", Toast.LENGTH_SHORT).show();
                String string = myHobby.getText().toString();
                intent.putExtra("hobby", string);
                setResult(10, intent);

                SharedPreferences sharedPreferences = getSharedPreferences("hobby.db", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("hobby", string);
                edit.commit();
                finish();


                break;
        }
    }
}
