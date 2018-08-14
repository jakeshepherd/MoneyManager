package com.example.jakeshepherd.moneymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateData extends AppCompatActivity {

    Database db;
    private String dueDateToSetBill;
    Date dueDate = null;


    /**
     * TODO add a check if the user hasnt put anything for amount
     * TODO need to get the date to work with the database properly
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        db = new Database(this);

        Button confirm = findViewById(R.id.buttonConfirm);
        Button cancel = findViewById(R.id.buttonCancel);
        Button change = findViewById(R.id.buttonDateChange);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeDateIntent = new Intent(getBaseContext(), DatePickerActivity.class);
                startActivityForResult(changeDateIntent, 0); // 0 for dates
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText billNum = findViewById(R.id.editBillNum);
                EditText name = findViewById(R.id.editNewName);
                EditText amount = findViewById(R.id.editNewAmount);

                String billNumString = billNum.getText().toString();
                String nameString = name.getText().toString();
                float amountFloat = Float.parseFloat(amount.getText().toString());

                try {
                    dueDate = (new SimpleDateFormat("dd/MM/yyyy", Locale.UK)).parse(dueDateToSetBill);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(billNumString.equals("")){
                    billNum.setError("Enter a bill number to update");
                    //Intent reset = new Intent(getBaseContext(), UpdateData.class);
                }
                if(nameString.equals("")){
                    name.setError("Enter a name");
                }

                if((billNumString.equals("")) || (nameString.equals(""))){
                    showMessage("Please fill in all details");
                }else{
                    boolean updated = db.updateData(billNumString, nameString, amountFloat, String.valueOf(dueDate));
                    if(updated){
                        System.out.println("Bill data added");
                    }else{
                        System.out.println("Bill addition failed");
                    }
                    Intent viewBills = new Intent(getBaseContext(), ViewBills.class);
                    startActivity(viewBills);
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : { // for dates
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    this.dueDateToSetBill = data.getStringExtra("date");
                    TextView dueDateLabel = findViewById(R.id.textDate);
                    dueDateLabel.setText(this.dueDateToSetBill);
                }
                break;
            }
        }
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}