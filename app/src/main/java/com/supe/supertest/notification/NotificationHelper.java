package com.supe.supertest.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.supe.supertest.R;

import java.lang.reflect.Method;

/**
 * @Author yinzh
 * @Date 2019/3/20 20:03
 * @Description
 */
public class NotificationHelper {

    private static final String CHANNEL_ID = "SuperTest";
    private static final int NOTIFICATION_ID = 1138;

    private Method setChannelId;
    private Context context;


    private final NotificationManager notificationManager;


    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                new NotificationChannel(CHANNEL_ID, context.getString(R.string.tag_name_notification), NotificationManager.IMPORTANCE_LOW);

            try {
                setChannelId = NotificationCompat.Builder.class.getMethod("setChannelId", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void show(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);


        if (setChannelId != null) {
            try {
                setChannelId.invoke(builder, CHANNEL_ID);
            } catch (Exception ignored) {
            }
        }

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        builder.setTicker("我是测试内容")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND);


        notificationManager.notify(10, builder.build());
    }




    private Intent getIntent(){
        return new Intent(context, NotificationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
