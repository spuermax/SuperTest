package com.supe.supertest.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.supe.supertest.MainActivity;
import com.supe.supertest.R;
import com.supe.supertest.common.button.BaseFloatDailog;
import com.supe.supertest.common.button.FloatItem;
import com.supe.supertest.common.button.FloatLogoMenu;
import com.supe.supertest.common.button.FloatMenuView;
import com.supe.supertest.common.button.MyFloatDialog;
import com.supe.supertest.common.wdiget.SonnyJackDragView;
import com.supe.supertest.home.adapter.YDNAdapter;
import com.supe.supertest.home.presenter.YaDianNaPresenter;
import com.supe.supertest.viewpageractivity.ViewPagerActivity;
import com.supe.supertest.viewpageractivity.model.Item;
import com.supermax.base.common.log.L;
import com.supermax.base.common.model.QsModel;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.fragment.QsFragment;
import com.supermax.base.mvp.fragment.QsPullListFragment;

import java.util.ArrayList;

/**
 * @Author yinzh
 * @Date 2018/10/17 20:32
 * @Description
 */
public class YaDianNaFragment extends QsPullListFragment<YaDianNaPresenter, Item> {


    private FloatLogoMenu mFloatMenu;

    String HOME = "首页";
    String FEEDBACK = "客服";
    String MESSAGE = "消息";

    String[] MENU_ITEMS = {HOME, FEEDBACK, MESSAGE};

    private int[] menuIcons = new int[]{R.drawable.yw_menu_account, R.drawable.yw_menu_fb, R.drawable.yw_menu_msg};

    ArrayList<FloatItem> itemList = new ArrayList<>();

    @Override
    public void initData(Bundle bundle) {
        getPresenter().requestData(false);

        for (int i = 0; i < menuIcons.length; i++) {
            itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), String.valueOf(i + 1)));
        }
    }

    @Override
    public QsListAdapterItem<Item> getListAdapterItem(int i) {
        return new YDNAdapter();
    }

    @Override
    public void onRefresh() {
        QsToast.show("刷新");
        getPresenter().requestData(false);
    }

    @Override
    public void onLoad() {
        QsToast.show("加载更多");
        getPresenter().requestData(true);


    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("MoveView", "onResume");
    }

    BaseFloatDailog mBaseFloatDailog ;
    @Override
    public void onPause() {
        super.onPause();
        L.i("MoveView", "onPause");


        if (mFloatMenu == null) {
            mFloatMenu = new FloatLogoMenu.Builder()
                    .withActivity(getActivity())
//                    .withContext(mActivity.getApplication())//这个在7.0（包括7.0）以上以及大部分7.0以下的国产手机上需要用户授权，需要搭配<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
                    .logo(BitmapFactory.decodeResource(getResources(), R.drawable.yw_game_logo))
                    .drawCicleMenuBg(true)
                    .backMenuColor(0xffe4e3e1)
                    .setBgDrawable(this.getResources().getDrawable(R.drawable.yw_game_float_menu_bg))
                    //这个背景色需要和logo的背景色一致
                    .setFloatItems(itemList)
                    .defaultLocation(FloatLogoMenu.RIGHT)
                    .drawRedPointNum(false)
                    .showWithListener(new FloatMenuView.OnMenuClickListener() {
                        @Override
                        public void onItemClick(int position, String title) {
                            Toast.makeText(getContext(), "position " + position + " title:" + title + " is clicked.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void dismiss() {

                        }
                    });

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshDot();
                }
            }, 5000);
        }

//        mFloatMenu.hide();



        if (mBaseFloatDailog != null) return;

        mBaseFloatDailog = new MyFloatDialog(getContext());
        mBaseFloatDailog.show();
    }


    public void refreshDot() {
        for (FloatItem menuItem : itemList) {
            if (TextUtils.equals(menuItem.getTitle(), "我的")) {
                menuItem.dotNum = String.valueOf(8);
            }
        }
        mFloatMenu.setFloatItemList(itemList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        QsToast.show(position + "");
        getPresenter().requestAuthCode();
    }
}
