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
import java.text.ParseException;
import java.util.Date;

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

        DateManager dm = new DateManager();
        Date d = new Date();
        String s = d.toString();

        try {
            System.out.println(String.format("\n\n\n%s\n%s\n\n\n", s, dm.getDateFromString("Fri Aug 17 00:00:00 GMT+01:00 2018").toString()));
        } catch (ParseException e) {
            System.out.println(String.format("\n\n\nCOULD NOT PARSE DATE!\n%s\n\n\n", s));
        }

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