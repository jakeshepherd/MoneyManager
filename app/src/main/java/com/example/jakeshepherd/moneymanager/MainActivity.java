package com.example.jakeshepherd.moneymanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Homescreen gives options for recurring payment or single payment
 * then need to add view bills option and maybe some other stuff
 * also i rlly like the orange colour
 */

public class MainActivity extends AppCompatActivity {
    /**
     * TODO get notification to work
     */
    public static final String USER_NAME = "Will";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Test", "Create notification method called");

        Button singlePayment = findViewById(R.id.buttonViewBills);
        Button recurringPaymentButton = findViewById(R.id.buttonRecur);

        singlePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewBills = new Intent(getBaseContext(), BillHistory.class);
                startActivity(viewBills);
            }
        });

        recurringPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recurPaymentIntent = new Intent(getBaseContext(), RecurringPaymentActivity.class);
                startActivity(recurPaymentIntent);
            }
        });
    }




}