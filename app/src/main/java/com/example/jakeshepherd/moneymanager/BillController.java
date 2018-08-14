package com.example.jakeshepherd.moneymanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BillController {

    final String EMAIL_SUBJECT = "There's a New Bill to Pay!";
    // data order: sender name, amount, due date, sender name, due date, amount, payee, amount
    final String EMAIL_BODY_TEMPLATE = "# You owe %s money!\n" +
            "(We're sorry)\n\n" +
            "## Apparently, you need to pay £%s on %s. If for some unknown reason you can't make the payment, get a job.\nOr just reply to this email.\n\n" +
            "## Here are the full details of the payment, according to what %s told us:\n" +
            "After %s;\n" +
            "You'll have £%s less than you do now." +
            "And %s will then be £%s richer.";


    private ArrayList<Bill> billList;

    BillController() {
        billList = new ArrayList<>();
    }

    public void addBill(Bill billToAdd) {
        this.billList.add(billToAdd);
    }

    public void addMultipleBills(Bill[] listOfBillsToAdd) {
        for (Bill b : listOfBillsToAdd) {
            this.billList.add(b);
        }
    }

    public Bill getLatestBill() {
        return billList.get(billList.size() - 1); // last bill added
    }

    public Bill removeLatestBill() {
        return billList.remove(billList.size() - 1);
    }

    public Bill removeBillAtIndex(int index) {
        return billList.remove(index);
    }

    public void addBillAtIndex(int index, Bill billToAdd) {
        this.billList.add(index, billToAdd);
    }

    public ArrayList<Bill> getBillsDueToday() {
        ArrayList<Bill> listToReturn = new ArrayList<Bill>();

        for (Bill bill : billList) {
            DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            String currentDateString = dateFormatter.format(new Date());
            Date currentDate = null;
            Date billDate = bill.getDueDate();

            try {
                currentDate = dateFormatter.parse(currentDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (billDate.toString().equals(currentDate.toString())) { // toString() for easy comparison
                listToReturn.add(bill);
            }
        }

        return listToReturn;
    }
}
