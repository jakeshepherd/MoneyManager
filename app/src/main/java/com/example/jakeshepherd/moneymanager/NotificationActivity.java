package com.example.jakeshepherd.moneymanager;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * pretty sure this class isnt needed
 */
public class NotificationActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    /**
     * displays push notification to user when they add a payment
     * @param view
     *      ?
     * @param name
     *      name of bill
     * @param amount
     *      amount of bill
     * @param date
     *      date of bill
     * @param billSplitNum
     *      number of people the bill is split between
     * @param description
     *      description of bill
     */
    public void displayNotification(View view, String name, float amount, String date, int billSplitNum, String description){
        Intent intent = new Intent(this, ViewBills.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_attach_money);
        builder.setContentTitle("You have an upcoming bill");
        builder.setContentText("You owe " + name + " £" + amount + " split between " + billSplitNum + " people on the " + date);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent).setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
