package com.supe.supertest.homework.dialog;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.common.dialog.BaseDialogFragment;

/**
 * @Author yinzh
 * @Date 2020/3/18 17:46
 * @Description
 */
public class SureTitleDialog extends BaseDialogFragment implements View.OnClickListener {

    private CallBack mCallBack;

    private String titleStr;
    private String contentStr;
    private String strRight, strLeft;

    private boolean isShowOneBtn = false;

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_sure_title;
    }

    public SureTitleDialog setClickListener(CallBack callBack) {
        mCallBack = callBack;
        return this;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView mTvLeft = view.findViewById(R.id.tv_cancel);
        TextView mTvRight = view.findViewById(R.id.tv_sure);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        View viewLine = view.findViewById(R.id.view);
        TextView mTvContent = view.findViewById(R.id.tv_content);
        if (titleStr != null) {
            mTvTitle.setText(titleStr);
        }
        if (contentStr != null) {
            mTvContent.setText(contentStr);
        }
        if (strRight != null) {
            mTvRight.setText(strRight);
        }
        if (strLeft != null) {
            mTvLeft.setText(strLeft);
        }

        if (isShowOneBtn) {
            mTvLeft.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }

        mTvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Dialog设置标题
     *
     * @param title
     */
    public SureTitleDialog setTitle(String title) {
        titleStr = title;
        return this;
    }

    /**
     * Dialog设置dialog的message
     *
     * @param message
     */
    public SureTitleDialog setMessage(String message) {
        contentStr = message;
        return this;
    }

    /**
     * @param strRight
     * @param strLeft
     */
    public SureTitleDialog setAllBtnText(String strRight, String strLeft) {
        this.strRight = strRight;
        this.strLeft = strLeft;
        return this;
    }

    /**
     * @param strRight
     */
    public SureTitleDialog setBtnText(String strRight) {
        this.strRight = strRight;
        return this;
    }

    /**
     * 只显示一个按钮
     */
    public SureTitleDialog setOneBtn() {
        this.isShowOneBtn = true;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            if (mCallBack != null) {
                mCallBack.onLeftClick(SureTitleDialog.this, v);
            }
        } else if (v.getId() == R.id.tv_sure) {
            if (mCallBack != null) {
                mCallBack.onRightClick(SureTitleDialog.this, v);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface CallBack {
        void onRightClick(SureTitleDialog dialog, View v);

        void onLeftClick(SureTitleDialog dialog, View v);
    }
}
