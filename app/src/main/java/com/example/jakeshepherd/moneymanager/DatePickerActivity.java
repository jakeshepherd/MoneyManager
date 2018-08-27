package com.example.jakeshepherd.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.rtt.RangingRequest;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerActivity extends AppCompatActivity {

    CalendarView calView;
    String dateToReturn;
    Button saveDateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        dateToReturn = new Date().toString();

        setupCalendarView();
        setupSaveButton();

        DisplayMetrics disMet = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disMet);
        getWindow().setLayout((int)(disMet.widthPixels * .8), (int)(disMet.heightPixels * .7));

        /***
         * TODO:
         * > Need to add a way to make the activity auto-resize to whatever the content size is rather
         *      than as a percentage of the screen size
         ***/

    }

    private void setupCalendarView() {
        this.calView = findViewById(R.id.calendarView);
        this.calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i2 + "/" + (i1+1) + "/" + i);

                dateToReturn = date;

                String snackText = "Selected date is: " + date;
                Snackbar.make(calendarView, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void setupSaveButton() {
        this.saveDateButton = findViewById(R.id.SaveDateButton);
        this.saveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult();
            }
        });
    }

    private void returnResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("date", dateToReturn);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
