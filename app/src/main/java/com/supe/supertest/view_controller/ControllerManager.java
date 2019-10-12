package com.supe.supertest.view_controller;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/9/24 14:15
 * @Description
 */
public class ControllerManager {
    /**
     * true 当前处于显示状态，或正在执行显示动画
     * false 当前处于隐藏状态，或正在执行隐藏动画
     */
    private boolean showing = true;

    private List<Controller> controllerList = new ArrayList<>();
    private CountDownTimer countDownTimer;

    public ControllerManager addController(Controller controller) {
        controllerList.add(controller);
        return this;
    }

    /**
     * 初始化完成后 开始倒计时隐藏 controllers
     * @return
     */
    public ControllerManager startWorking() {
        startCount();
        return this;
    }

    public boolean isShowing() {
        return showing;
    }

    /**
     * 切换状态
     * 同时取消倒计时
     */
    public void switchState() {
        stopCount();
        showing = !showing;
        if (showing) {
            show();
        } else {
            hide();
        }
    }

    /**
     * 分发 show 事件至所有 controller
     * 同时开始倒计时
     */
    private void show() {
        for (Controller controller : controllerList) {
            controller.show();
        }
        startCount();
    }

    private void hide() {
        for (Controller controller : controllerList) {
            controller.hide();
        }
    }

    /**
     * 倒计时结束时切换状态
     */
    private void startCount() {
        countDownTimer = new CountDownTimer(2500, 2500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switchState();
            }
        }.start();
    }

    private void stopCount() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
