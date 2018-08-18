package com.example.jakeshepherd.moneymanager;

import java.util.Date;

/**
 * Bill class.
 * Each bill must have an amount, payee and a due date. Can also have a description
 */

public class Bill {

    // Must-have fields
    private float amount;
    private String payeeName;
    private Date dueDate;
    private int billNumber;

    // Non-essential fields - need these?
    private Date setDate;
    private String[] emailsToSplitBy;

    /**
     * Constructors
     */
    Bill(float amount, String payeeName, Date dueDate) {
        this.amount = amount;
        this.payeeName = payeeName;
        this.dueDate = dueDate;
        this.setDate = new Date();

        this.emailsToSplitBy = new String[] {"jakeshepherd98@gmail.com"}; // need to change
    }

    Bill(int number, String name, float amount, Date date) {
        this.billNumber = number;
        this.payeeName = name;
        this.amount = amount;
        this.dueDate = date;
        this.setDate = new Date();

        this.emailsToSplitBy = new String[] {"jakeshepherd98@gmail.com"}; // need to change
    }

    Bill(int number, String name, float amount, String date) {
        this.billNumber = number;
        this.payeeName = name;
        this.amount = amount;
        this.dueDate = new Date(); // need to change to actual date from string
        this.setDate = new Date();

        this.emailsToSplitBy = new String[] {"jakeshepherd98@gmail.com"}; // need to change
    }

    /**
     * Getters and Setters
     */
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getSetDate() {
        return setDate;
    }

    public String[] getEmails() {
        return this.emailsToSplitBy;
    }
}
