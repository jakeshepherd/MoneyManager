package com.example.jakeshepherd.moneymanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Build;
import android.support.design.widget.Snackbar;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RecurringPaymentActivity extends AppCompatActivity {
    Database db;
    Date data;

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
    NotificationCompat.Builder mBuilder;
    private static String CHANNEL_ID = "default";
    public boolean recurring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_payment);

        this.billController = new BillController();
        db = new Database(this);
        createNotificationChannel();
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
        this.recurSwitch = findViewById(R.id.recurSwitch);

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
     * TODO - also make this entire method look nicer i think
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
                    /**
                     * this should convert the string dueDate to a nice formatted Date dueDate...
                     */
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

                    setAlarm(view, name, amount, dueDateToSetBill, billSplitNum, description);

                    Intent homeScreen = new Intent(getBaseContext(), MainActivity.class);
                    //startActivity(homeScreen);
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
     * TODO get it to send the alert on the actual date that has been set.
     */
    public void setAlarm(View view, String name, float amount, String endDate, int billSplitNum, String description){
        String[] individualComponents = endDate.split("/");
        int day = Integer.parseInt(individualComponents[0]);
        int month = Integer.parseInt(individualComponents[1]);
        int year = Integer.parseInt(individualComponents[2]);

        Intent alertIntent = new Intent(this, AlertReceiver.class);
        alertIntent.putExtra("billName", name);
        alertIntent.putExtra("billAmount", amount);
        alertIntent.putExtra("billDate", endDate);
        alertIntent.putExtra("billSplitNum", billSplitNum);
        alertIntent.putExtra("billDescription", description);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alertIntent, 0);

        /**
         * set calendar to the date that user has entered as bill due date
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, day);

        // i think months work like arrays in the calender, ie jan = 0
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);

        /**
         * doesnt work with time -- not even sure if it works with the date etc properly yet...
         */
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        /**
         * checks if the switch is on, if so it sets a recurring payment,
         * else set a one time alarm
         */
        if(recurSwitch.isChecked()){
            /**
             * sends alarm on the day in the calender, then repeats that every month
             */
            String snackText = String.format("New recurring payment of £%s is due on %s. Payable to %s.", amount, endDate, name);
            Snackbar.make(view, snackText, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            // this should set the alarm to repeat once every month
            //TODO -- give the user an option to decide how big the interval is
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*(Calendar.DAY_OF_MONTH), pendingIntent);
        }else{
            /**
             * below can be un-commented to send a notifications as soon as payment is added
             */
            long alertTime = new GregorianCalendar().getTimeInMillis()+1000;
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1,
                    alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }

    /**
     * Creates a channel for notifications to be sent through
     * TODO -- get a new channel to be created every time a new payment is added, this way I think multiple notifications can be sent?
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Money Manager Notification";
            String description = "Channel to send notifications from Money Manager";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
