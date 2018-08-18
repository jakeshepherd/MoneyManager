package com.example.jakeshepherd.moneymanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BillListAdapter extends ArrayAdapter<Bill> {

    BillListAdapter(Context context, ArrayList<Bill> resource) {
        super(context, R.layout.bill_table_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.bill_table_row, parent, false);

        Bill bill = getItem(position);

        TextView billCellTitle = customView.findViewById(R.id.cardViewTitle);
        TextView billAmountAndPayee = customView.findViewById(R.id.amountAndPayeeTextView);
        TextView billTotalAndSplitBy= customView.findViewById(R.id.totalAndSplitByView);

        String titleString = String.format("%s - X Days", bill.getPayeeName());
        @SuppressLint("DefaultLocale") String amountString = String.format("£%.2f to %s", bill.getAmount(), bill.getPayeeName());
        @SuppressLint("DefaultLocale") String totalString = String.format("Total of £%.2f, split between X.", bill.getAmount());

        billCellTitle.setText(titleString);
        billAmountAndPayee.setText(amountString);
        billTotalAndSplitBy.setText(totalString);

        return customView;
    }
}
