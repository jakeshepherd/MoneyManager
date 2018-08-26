package com.example.jakeshepherd.moneymanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;


/**
 * Could potentially use a timestamp for notification ID
 */
public class AlertReceiver extends BroadcastReceiver{
    private int notificationId = 1;
    private static String CHANNEL_ID = "default";


    /**
     * when the alarm goes off, a notification is created and sent to the user.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("billName");
        float amount = intent.getFloatExtra("billAmount", 0);
        String billDate = intent.getStringExtra("billDate");
        int billSplitNum = intent.getIntExtra("billSplitNum", 0);
        String description = intent.getStringExtra("billDescription");

        createNotification(context, "You have an upcoming bill...", "You owe " + name + " £" + amount + " on " + billDate +
                    " split between " + billSplitNum + " people.");
    }

    public void createNotification(Context context, String title, String text){
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, BillHistory.class), 0);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_attach_money)
                .setContentTitle(title)
                .setContentText(text);

        mbuilder.setContentIntent(notificIntent);

        //TODO - get the LED to blink correctly
        mbuilder.setLights(Color.RED,3000,3000);
        
        mbuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mbuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mbuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, mbuilder.build());
    }

}