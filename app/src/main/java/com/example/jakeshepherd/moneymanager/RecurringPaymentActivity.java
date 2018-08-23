package com.example.jakeshepherd.moneymanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecurringPaymentActivity extends AppCompatActivity {


    Database db;

    private Button addPaymentButton;
    private EditText amountField;
    private TextView dueDateLabel;
    private Button changeDateButton;
    private Switch recurSwitch;
    private EditText payeeNameField;
    private EditText editDescription;
    private EditText editNumSplit;

    private BillController billController;
    private String dueDateToSetBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_payment);

        this.billController = new BillController();
        db = new Database(this);
       //notificationActivity = new NotificationActivity();

        setupNodes();
    }

    private void setupNodes() {
        this.addPaymentButton = findViewById(R.id.AddButton);
        this.amountField = findViewById(R.id.AmountField);
        this.dueDateLabel = findViewById(R.id.ChosenDateLabel);
        this.changeDateButton = findViewById(R.id.ChangeDateButton);
        this.recurSwitch = findViewById(R.id.recurSwitch);
        this.payeeNameField = findViewById(R.id.PayeeNameField);
        this.editDescription = findViewById(R.id.editDescription);
        this.editNumSplit = findViewById(R.id.editSplitNum);

        addButtonListeners();
    }

    private void addButtonListeners() {
        addPaymentButtonListener();
        addChangeDateButtonListener();
    }

    /**
     * Sets the listener for the add Bill button.
     * Takes the user-inputted values and creates a new instance of Bill() with those values. The new
     * Bill object is then added to the list stored in BillController.
     *
     * TODO
     * > Need to add some form of offline storage for bills -- hasnt this been done in the database?
     * > and a function to load in stored data into BillController() -- this has been done too right?
     */
    private void addPaymentButtonListener() {
        this.addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float amount = Float.parseFloat(amountField.getText().toString());
                String name = payeeNameField.getText().toString();
                Date dueDate = null;
                String description = editDescription.getText().toString();
                int billSplitNum = Integer.parseInt(editNumSplit.getText().toString());

                try {
                    dueDate = (new SimpleDateFormat("dd/MM/yyyy", Locale.UK)).parse(dueDateToSetBill);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (amount != 0 && !name.equals("") && dueDate != null && billSplitNum != 0 && !description.equals("")) {
                    Bill newBill = new Bill(amount, name, dueDate);

                    System.out.println("Due: " + dueDate.toString());
                    db.insertData(name, amount, String.valueOf(dueDate), billSplitNum, description);

                    billController.addBill(newBill);
                    if (checkIfUserWantsToEmail()) {
                        notifyPeople(newBill);
                    }

                    billController.getBillsDueToday();

                    String snackText = String.format("New recurring payment of £%s is due on %s. Payable to %s.", amount, dueDate.toString(), name);
                    Snackbar.make(view, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    setAlarm(view, dueDate);

                    Intent homeScreen = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(homeScreen);
                } else {
                    boolean amountIsNull = (amount == 0);
                    boolean dateIsNull = (dueDate == null);
                    boolean nameIsNull = (name.equals(""));
                    boolean billSplitNumIsNull = (billSplitNum == 0);
                    boolean descriptionIsNull = (description.equals(""));

                    String snackText = String.format("Please fill in all details", amountIsNull, dateIsNull, nameIsNull, billSplitNumIsNull, descriptionIsNull);
                    Snackbar.make(view, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    }

    private boolean checkIfUserWantsToEmail() {
        // TODO: add pop-up/option to choose to and who notify
        return false; // false to prevent email pop up each time a bill is added
    }

    private void addChangeDateButtonListener() {
        this.changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeDateIntent = new Intent(getBaseContext(), DatePickerActivity.class);
                startActivityForResult(changeDateIntent, 0); // 0 for dates
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
                    this.dueDateLabel.setText(this.dueDateToSetBill);
                }
                break;
            }
        }
    }

    private void notifyPeople(Bill billToNotifyFor) {
        // data order: sender name, amount, due date, sender name, due date, amount, payee, amount
        String amount = "" + billToNotifyFor.getAmount();
        String date = billToNotifyFor.getDueDate().toString();
        String payee = billToNotifyFor.getPayeeName();

        String emailBody = String.format(billController.EMAIL_BODY_TEMPLATE, MainActivity.USER_NAME, amount, date, MainActivity.USER_NAME, date, amount, payee, amount);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , billToNotifyFor.getEmails());
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, billController.EMAIL_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT   , emailBody);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RecurringPaymentActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(RecurringPaymentActivity.this, "There has been an error. Cannot send an email at this time.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * setAlarm sends off an alarm at a given time that is received by AlertReceiver and will then show a notification.
     */
    public void setAlarm(View view, Date endDate){
        /**
         * TODO get it to send the alert on the actual date that has been set.
         * also, need the notification to say bill info maybe?
         * This currently sends an alert to the user five seconds after the payment has been recorded (because thats
         * the difference between the start and end date)
         */

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        long difference = 0;
        try {
            Date startDate = simpleDateFormat.parse("23/08/2018 20:36:00");
            Date simpleEndDate = simpleDateFormat.parse("23/08/2018 20:35:55");
            difference = simpleEndDate.getTime() - startDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long alertTime = difference;

        Intent alertIntent = new Intent(this, AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1,
                alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }



//    /**
//     * cant get the date difference to work with your messed up date picker thing @willtaylor. ;)
//     * @param endDate
//     */
//    public void dateDifference(Date endDate){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
//
//        try {
//            Date startDate = simpleDateFormat.parse("23/08/2018 20:36:00");
//            Date simpleEndDate = simpleDateFormat.parse("23/08/2018 20:35:55");
//            difference = simpleEndDate.getTime() - startDate.getTime();
//            return difference;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }




}
