package com.example.jakeshepherd.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Homescreen gives options for recurring payment or single payment
 * then need to add view bills option and maybe some other stuff
 * also i rlly like the orange colour
 */

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "Will";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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