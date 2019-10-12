package com.supe.supertest.view_controller;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * @Author yinzh
 * @Date 2019/9/24 14:18
 * @Description
 */
public abstract class ValueAnimatorController implements Controller {

    private static final int DURATION = 250;

    /**
     * 子类提供显示时的目标 value
     *
     * @return
     */
    protected abstract int getShowTarget();

    /**
     * 子类提供隐藏时的目标 value
     *
     * @return
     */
    protected abstract int getHideTarget();

    /**
     * 子类处理动态计算出的 value 以实现动画效果
     *
     * @param shift
     */
    protected abstract void onShiftChanged(int shift);

    protected View view;
    protected ValueAnimator va;
    protected int shift;

    public ValueAnimatorController(View view) {
        this.view = view;
    }

    @Override
    public void show() {
        stop();
        makeAnimation(getShowTarget());
    }

    @Override
    public void hide() {
        stop();
        makeAnimation(getHideTarget());
    }

    private void stop() {
        if (va != null) {
            va.cancel();
        }
    }

    protected void makeAnimation(int target) {
        //这里采用当前状态的 shift 而不是初始值，
        //是为了动画被停止后，朝反方向移动更平滑
        va = ValueAnimator.ofInt(shift, target);
        va.setDuration(DURATION);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shift = (int) animation.getAnimatedValue();
                onShiftChanged(shift);
            }
        });
        va.start();
    }

}
