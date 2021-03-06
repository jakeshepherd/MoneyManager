package com.example.jakeshepherd.moneymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewBills extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupListView() {

    }

    private void tempStringBufferFunction() { // needed to get it out the way, kept confusing myself lol
        StringBuffer buffer = new StringBuffer();
        Cursor res = db.getAllData();
        while(res.moveToNext()) {
            buffer.append("------------------------------\n");
            buffer.append("Bill number " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Amount: " + res.getString(2) + "\n");
            buffer.append("Due date: " + res.getString(3) + "\n");
            buffer.append("------------------------------\n\n");
        }

//        TextView scrollableText = findViewById(R.id.TextData);
//        scrollableText.setText(buffer.toString());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(getBaseContext(), DeleteOrUpdate.class);
                startActivity(popup);
            }
        });
    }
}
