package com.example.jakeshepherd.moneymanager;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BillHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        /* Change colour to the orange to make the cards easier to see for testing */
        View someView = findViewById(R.id.billListView);
        View root = someView.getRootView();
        root.setBackgroundColor(Color.parseColor("#FF8C00"));

        ArrayList<Bill> billList = getBillList();

        Bill firstBill = billList.remove(0);

        TextView firstBillAmount = findViewById(R.id.firstAmountField);
        firstBillAmount.setText(String.format("£%.2f", firstBill.getAmount()));

        TextView firstBillPayee = findViewById(R.id.firstPayeeNameField);
        firstBillPayee.setText(String.format("To: %s", firstBill.getPayeeName()));

        TextView firstBillDate = findViewById(R.id.firstDueDateField);
        firstBillDate.setText(String.format("On: %s", firstBill.getDueDate().toString().substring(0, 10)));

        ListAdapter billListAdapter = new BillListAdapter(this, billList);
        ListView billListView = findViewById(R.id.billListView);
        billListView.setAdapter(billListAdapter);
    }

    private ArrayList<Bill> getBillList() {
        Database db = new Database(this);
        ArrayList<Bill> billList = new ArrayList<>();

        Cursor dbCursor = db.getAllData();
        while(dbCursor.moveToNext()) {
            Bill newBill = getNextBill(dbCursor);
            billList.add(newBill);
        }

        return billList;
    }

    private ArrayList getBillNames(ArrayList<Bill> billList) {
        ArrayList<String> billNames = new ArrayList<>();
        int listLength = billList.size();
        for (int i=0; i<listLength; i++) {
            Bill bill = billList.get(i);
            String billString = String.format("%s - £%s - %s", bill.getPayeeName(), bill.getAmount(), (bill.getDueDate().toString()).substring(0, 10));
            billNames.add(billString);
        }
        return billNames;
    }

    private Bill getNextBill(Cursor cursor) {
        int billNumber = Integer.parseInt(cursor.getString(0));
        String billName = cursor.getString(1);
        float billAmount = Float.parseFloat(cursor.getString(2));
        String billDueDate = cursor.getString(3);

        return new Bill(billNumber, billName, billAmount, billDueDate); // number, name, amount, date
    }
}
