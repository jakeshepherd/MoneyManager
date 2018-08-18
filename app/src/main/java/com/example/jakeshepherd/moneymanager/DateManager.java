package com.example.jakeshepherd.moneymanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    /**
     * Gets a Date object for the passed in String.
     * The string must be in the format "dd/MM/yyyy" (i think)
     *
     * @param dateString : String containing the date
     * @return : Date object parsed from the String
     * @throws ParseException : when the String is in the incorrect format
     */
    Date getDateFromString(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH); // 18/08/2018
        Date date = format.parse(dateString);
        System.out.println(date);

        return date;
    }

    /**
     * Returns the passed in date as a String
     *
     * @param date : date to get as String
     * @return : String representation of the date
     */
    String getStringFromDate(Date date) {
        return "";
    }

    /**
     * Returns the number of days until the passed date.
     * Used in the view bills activity.
     *
     * @param date : date to get days until
     * @return : number of days (int) until the passed in date
     */
    int getsDaysUntilDate(Date date) {
        return 0;
    }

    /**
     * Meh, don't really need this one
     *
     * @param dateOne : first date
     * @param dateTwo : second date
     * @return : comparison result
     */
    int compareDates(Date dateOne, Date dateTwo) {
        return dateOne.compareTo(dateTwo);
    }



}
