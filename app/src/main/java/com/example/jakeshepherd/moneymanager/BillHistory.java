package com.example.jakeshepherd.moneymanager;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BillHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        ArrayList billList = getBillList();
        ArrayList billNameList = getBillNames(billList);
        ListAdapter billListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, billNameList);

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
