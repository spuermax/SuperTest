package com.supe.supertest;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supe.supertest.collapsing.CollapsingActivity;
import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.nestedscrolling.view.ScrollViewFrameLayout;
import com.supe.supertest.paint.PaintActivity;
import com.supe.supertest.homework.MaterialQuestionActivity;
import com.supe.supertest.homework.QuestionActivity;
import com.supe.supertest.rxjava.RxJavaActivity;
import com.supe.supertest.view_controller.ControllersView;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @Author yinzh
 * @Date 2019/8/13 09:43
 * @Description
 */
public class TestPagerActivity extends QsActivity {

    private PopupWindow mPopWindow;
    @Bind(R.id.pupop)
    Button popup;

    @Bind(R.id.edit_text)
    EditText editText;

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.tv_view)
    ScrollViewFrameLayout tv_view;

    @Bind(R.id.top_view)
    View top_view;

    @Bind(R.id.bottom_view)
    View bottom_view;

    @Bind(R.id.relative_layout)
    RelativeLayout relativeLayout;

    private IWXAPI api;
    private String WX_ID = "wxff6bbd5dfc9c8e7e";
    private String WX_SECRET = "e4cd86942b4de908ca415b9562e06345";
    private ControllersView controllersView;

    @Override
    public int layoutId() {
        return R.layout.activity_testpager;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        controllersView = new ControllersView(this, top_view, bottom_view);
        controllersView.setTopView(top_view);
        controllersView.setBottomView(bottom_view);


        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(getContext(), WX_ID, true);
        //将应用的appid注册到微信
        api.registerApp(WX_ID);

        editText.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                editText.clearFocus();
                hideInput();
                Log.i("AAAAAAAAAAAA", editText.getText().toString());
                tv_content.setText(editText.getText().toString());
                editText.setText("");
                return true;
            }
            return false;
        });

    }

    @OnClick({R.id.tv_rxJava, R.id.tv_login, R.id.tv_question,
            R.id.bt_material, R.id.pupop, R.id.paint,
            R.id.btn_input, R.id.relative_layout, R.id.collapsing})
    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.tv_rxJava:
                intent2Activity(RxJavaActivity.class);
                break;
            case R.id.tv_login:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                api.sendReq(req);
                break;
            case R.id.tv_question:
                intent2Activity(QuestionActivity.class);
                QsToast.show("123");
                break;
            case R.id.bt_material:
                intent2Activity(MaterialQuestionActivity.class);
                QsToast.show("123");
                break;
            case R.id.pupop:
                View inflate = getLayoutInflater().inflate(R.layout.popup_item, null);
                mPopWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mPopWindow.setContentView(inflate);

                // 设置背景图片， 必须设置，不然动画没作用
                mPopWindow.setBackgroundDrawable(new BitmapDrawable());
                // 设置点击popupwindow外屏幕其它地方消失
                mPopWindow.setOutsideTouchable(true);
                mPopWindow.setFocusable(true);
//                mPopWindow.showAsDropDown(popup, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.paint:
                intent2Activity(PaintActivity.class);
                break;

            case R.id.btn_input:
//                editText.setVisibility(View.VISIBLE);
//                showInput(editText);
                setScreen();

                break;
            case R.id.relative_layout:
                controllersView.setStae();
                break;
            case R.id.collapsing:
                intent2Activity(CollapsingActivity.class);
                break;
            default:
                break;
        }
    }

    private boolean mIsFullScreen;


    private void setScreen() {
        Log.i("AAAAAAAAAAAA", tv_view.getX() + "x=== " + tv_view.getY() + "Y ====");
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tv_view.getLayoutParams();

        if (!mIsFullScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            mIsFullScreen = true;


        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            mIsFullScreen = false;


        }

    }

    /**
     * 得到屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 得到屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ScreenUtils.dpToPx(120), ScreenUtils.dpToPx(90));
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tv_view.setScreenFull(true);
            QsToast.show("当前屏幕为横屏");
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tv_view.setLayoutParams(layoutParams);
        } else {

            tv_view.setScreenFull(false);
            layoutParams.topMargin = ScreenUtils.dpToPx(220);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tv_view.setLayoutParams(layoutParams);
            QsToast.show("当前屏幕为竖屏");
        }
    }
}
