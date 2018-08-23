package com.example.jakeshepherd.moneymanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "You have an upcoming bill...", "Open Money Manager to view your upcoming bills");
    }

    public void createNotification(Context context, String msg, String msgText){
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, BillHistory.class), 0);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_attach_money)
                .setContentTitle(msg)
                .setContentText(msgText);

        mbuilder.setContentIntent(notificIntent);
        mbuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mbuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, mbuilder.build());
    }

}
