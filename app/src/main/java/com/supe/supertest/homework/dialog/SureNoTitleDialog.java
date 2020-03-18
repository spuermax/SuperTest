package com.supe.supertest.homework.dialog;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supe.supertest.common.dialog.BaseDialogFragment;

/**
 * @Author yinzh
 * @Date 2020/3/18 17:52
 * @Description
 */
public class SureNoTitleDialog extends BaseDialogFragment implements View.OnClickListener {
    private CallBack mCallBack;

    private String contentStr;
    private String strRight, strLeft;

    private boolean isShowOneBtn = false;

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_sure_notitle;
    }

    public SureNoTitleDialog setClickListener(CallBack callBack) {
        mCallBack = callBack;
        return this;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView mTvLeft = view.findViewById(R.id.tv_cancel);
        TextView mTvRight = view.findViewById(R.id.tv_sure);
        View viewLine = view.findViewById(R.id.view);
        TextView mTvContent = view.findViewById(R.id.tv_message);
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
     * Dialog设置dialog的message
     *
     * @param message
     */
    public SureNoTitleDialog setMessage(String message) {
        contentStr = message;
        return this;
    }

    /**
     * @param strRight
     * @param strLeft
     */
    public SureNoTitleDialog setAllBtnText(String strRight, String strLeft) {
        this.strRight = strRight;
        this.strLeft = strLeft;
        return this;
    }

    /**
     * @param strRight
     */
    public SureNoTitleDialog setBtnText(String strRight) {
        this.strRight = strRight;
        return this;
    }

    /**
     * 只显示一个按钮
     */
    public SureNoTitleDialog setOneBttn() {
        this.isShowOneBtn = true;
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            if (mCallBack != null) {
                mCallBack.onLeftClick(SureNoTitleDialog.this, v);
            }
        } else if (v.getId() == R.id.tv_sure) {
            if (mCallBack != null) {
                mCallBack.onRightClick(SureNoTitleDialog.this, v);
            }
        }
    }

    public interface CallBack {
        void onRightClick(SureNoTitleDialog dialog, View v);

        void onLeftClick(SureNoTitleDialog dialog, View v);
    }
}
