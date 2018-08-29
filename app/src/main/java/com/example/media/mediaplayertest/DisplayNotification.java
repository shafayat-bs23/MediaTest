package com.example.media.mediaplayertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

public class DisplayNotification{

    Context context;
    NotificationManager mNotificationManager;

    public DisplayNotification(Context mContext) {
        this.context = mContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void makeNotification() {
        Intent intent = new Intent(context, MainActivity.class);
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.item_media_controller);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.side_nav_bar)
                .setContentTitle("Notification")
                .setContentText("This is notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCustomContentView(notificationLayout)
                .setContentIntent(pendingIntent);
        Notification n = builder.build();

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(1001, n);
    }
}
