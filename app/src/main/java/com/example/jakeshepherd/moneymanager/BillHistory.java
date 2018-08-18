package com.example.jakeshepherd.moneymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BillHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        String tempBillNames[] = {"Rent", "Water", "Electricity", "Spotify", "PornHub Premium"};
        ListAdapter billListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempBillNames);

        ListView billListView = findViewById(R.id.billListView);
        billListView.setAdapter(billListAdapter);
    }
}
