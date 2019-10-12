package com.supe.supertest.view_controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.supe.supertest.R;

/**
 * @Author yinzh
 * @Date 2019/9/24 14:13
 * @Description 视频进度条控制器
 */
public class ControllersView extends RelativeLayout {

    private Context context;

    private View topView;
    private View bottomView;
    private View rootView;

    public void setTopView(View topView) {
        this.topView = topView;
    }

    public void setBottomView(View bottomView) {
        this.bottomView = bottomView;
    }

    private ControllerManager controllerManager;

    public ControllersView(Context context, View topView, View bottomView) {
        super(context);
        this.context = context;
        this.topView = topView;
        this.bottomView = bottomView;
        initView();
    }

    public ControllersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        controllerManager = new ControllerManager();
        controllerManager.addController(new TopController(topView))
                .addController(new BottomController(bottomView))
                .startWorking();

    }

    public void setStae(){
        controllerManager.switchState();
    }
}

