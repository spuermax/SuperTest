package com.supe.supertest.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;


public class TallActivity extends AppCompatActivity  {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.save)
    TextView save;
    @Bind(R.id.image_undo)
    ImageView imageUndo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
    }


}
