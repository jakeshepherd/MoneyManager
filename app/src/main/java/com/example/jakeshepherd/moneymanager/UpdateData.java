package com.example.jakeshepherd.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jakeshepherd.moneymanager.Database;
import com.example.jakeshepherd.moneymanager.R;
import com.example.jakeshepherd.moneymanager.ViewBills;

public class UpdateData extends AppCompatActivity {

    Database db;

    /**
     * TODO add robustness
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        db = new Database(this);

        Button confirm = findViewById(R.id.buttonConfirm);
        Button cancel = findViewById(R.id.buttonCancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText billNum = findViewById(R.id.editBillNum);
                EditText name = findViewById(R.id.editNewName);
                EditText amount = findViewById(R.id.editNewAmount);

                String billNumString = billNum.getText().toString();
                String nameString = name.getText().toString();
                float amountString = Float.parseFloat(amount.getText().toString());

                db.updateData(billNumString, nameString, amountString);

                Intent viewBills = new Intent(getBaseContext(), ViewBills.class);
                startActivity(viewBills);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewBills = new Intent(getBaseContext(), ViewBills.class);
                startActivity(viewBills);
            }
        });

    }
}