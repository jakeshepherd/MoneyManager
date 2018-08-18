package com.example.jakeshepherd.moneymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        if (billList.size() > 0) {
            continueSetup(billList);
        } else {
            // give a better alert to user, i.e. popup or something -- "You have no outstanding bills! Add one before viewing!"
            // Can you Snackbar with buttons???
            String snackText = String.format("You need to add a bill before you can view them!");
            Snackbar.make(root, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void continueSetup(ArrayList<Bill> list) {
        setupNewBillButton();
        Bill firstBill = list.remove(0);

        setupFirstBillCard(firstBill);
        setupBillList(list);
    }

    private void setupNewBillButton() {
        Button addButton = findViewById(R.id.addBillButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recurPaymentIntent = new Intent(getBaseContext(), RecurringPaymentActivity.class);
                startActivity(recurPaymentIntent);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void setupFirstBillCard(Bill bill) {
        TextView firstBillAmount = findViewById(R.id.firstAmountField);
        firstBillAmount.setText(String.format("£%.2f", bill.getAmount()));

        TextView firstBillPayee = findViewById(R.id.firstPayeeNameField);
        firstBillPayee.setText(String.format("To: %s", bill.getPayeeName()));

        TextView firstBillDate = findViewById(R.id.firstDueDateField);
        firstBillDate.setText(String.format("On: %s", bill.getDueDate().toString().substring(0, 10)));
    }

    private void setupBillList(ArrayList<Bill> bills) {
        ListAdapter billListAdapter = new BillListAdapter(this, bills);
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
