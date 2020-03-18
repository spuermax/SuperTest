package com.supe.supertest.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.supe.supertest.R;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @Author yinzh
 * @Date 2020/3/18 15:10
 * @Description
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private boolean mBackCancel = true;//默认点击返回键关闭dialog
    private boolean mTouchOutsideCancel = true;//默认点击dialog外面屏幕，dialog关闭

    private ImmersionBar mImmersionBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PopDialogTheme3);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();
        }
        initView(view);
        initData();
        setListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //点击外部消失
        dialog.setCanceledOnTouchOutside(mTouchOutsideCancel);
        dialog.setOnKeyListener(new KeyBackListener());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar = null;
        }
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    protected boolean setTouchOutsideCancel(){
        return true;
    }
    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * view与数据绑定
     */
    protected void initView(View view) {
    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 设置点击返回键是否关闭dialog
     **/
    public BaseDialogFragment setCancel(boolean canDismiss) {
        this.mBackCancel = canDismiss;
        return this;
    }

    /**
     * 设置点击屏幕外面是否关闭dialog
     **/
    public BaseDialogFragment setCancelOnTouchOutside(boolean canDismiss) {
        this.mTouchOutsideCancel = canDismiss;
        return this;
    }

    public void showDialog(FragmentManager fragmentManager) {
        try {
            fragmentManager.beginTransaction().remove(this).commit();
            String className = this.getClass().getSimpleName();
            this.show(fragmentManager, className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog(FragmentManager fragmentManager) {
        String className = this.getClass().getSimpleName();
        Fragment prev = fragmentManager.findFragmentByTag(className);
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    /**
     * 监听返回键的类
     **/
    class KeyBackListener implements DialogInterface.OnKeyListener {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return !mBackCancel;
            }
            return false;
        }
    }
}

