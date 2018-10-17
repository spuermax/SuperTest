package com.supe.supertest.abactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.mvp.QsABActivity;

/*
 * @Author yinzh
 * @Date   2018/10/17 13:44
 * @Description
 */
public class ActionBarActivity extends QsABActivity{
    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

    @Override
    public boolean onKeyDown(KeyEvent keyEvent, int i) {
        return false;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @OnClick({R.id.ll_back})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
