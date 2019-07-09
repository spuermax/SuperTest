package com.supe.supertest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;


/**
 * @Author yinzh
 * @Date 2019/3/13 11:13
 * @Description
 */
@Route(path = "/supe/supertest")
public class TestActivityA extends QsActivity {
    NotificationManager mNotificationManager;
    @Bind(R.id.bt_Notification)
    Button bt_Notification;
    @Autowired (name = "name")
    public String name;
    @Autowired(name = "age")
    int age;

    @Override
    public int layoutId() {
        return R.layout.activity_test_a;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);//添加在onCreate（）

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    @OnClick({R.id.bt_Notification})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_Notification:
                intent2Activity(TopBottomActivity.class);
//                NotificationHelper notificationHelper = new NotificationHelper(this);
//                notificationHelper.show();
//                QsToast.show("qqq");
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
                Intent intent1 = new Intent();
                intent1.setClass(this, MainActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("收到一条聊天消息")
                        .setContentText("今天中午吃什么？")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
                manager.notify(1, notification);

                QsToast.show(name + age + "--");
                break;
        }
    }

}
