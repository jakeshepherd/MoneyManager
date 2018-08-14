package com.example.jakeshepherd.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteData extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        db = new Database(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.8));

        Button delete = findViewById(R.id.buttonDelete);
        Button cancel = findViewById(R.id.buttonCancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText billToDelete = findViewById(R.id.editBillDeletion);
                String billNum = billToDelete.getText().toString();

                if(billNum.equals("")){
                    billToDelete.setError("Fill in");
                    showMessage("Please enter a bill number");
                }else{
                    int test = db.deleteRowData(billNum);

                    if(test == 0){
                        showMessage("This bill number does not exist, please try again.");
                    }else{
                        showMessage("Bill number " + billNum + " has been deleted");
                        Intent viewBills = new Intent(getBaseContext(), ViewBills.class);
                        startActivity(viewBills);
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(getBaseContext(), DeleteOrUpdate.class);
                startActivity(popup);
            }
        });
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}