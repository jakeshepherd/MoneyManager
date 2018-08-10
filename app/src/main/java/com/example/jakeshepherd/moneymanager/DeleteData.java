package com.example.jakeshepherd.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jakeshepherd.moneymanager.Database;
import com.example.jakeshepherd.moneymanager.R;
import com.example.jakeshepherd.moneymanager.ViewBills;

public class DeleteData extends AppCompatActivity {

    Database db;

    /**
     * TODO add robustness
     * @param savedInstanceState
     */
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

                /**
                 * this will delete the specified row
                 * could look into how this is implemented in order to search for rows.
                 */
                db.deleteRowData(billNum);
                Intent viewBills = new Intent(getBaseContext(), ViewBills.class);
                startActivity(viewBills);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getBaseContext(), ViewBills.class);
                startActivity(back);
            }
        });
    }
}