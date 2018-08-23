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
//        boolean isCorrectFormat = checkDateStringFormat(dateString);
        boolean isCorrectFormat = true;
        if (isCorrectFormat) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH); // 18/08/2018
            Date date = format.parse(dateString);
            System.out.println(date);

            return date;
        } else {
            return new Date(); //  default to current date
        }
    }

    boolean checkDateStringFormat(String dateString) {
        if (dateString.length() > 6 && dateString.length() < 9) { return false;} // 7 or 8

        String[] dateComponents = getDateComponenetsFromString(dateString); // [dd, MM, yyyy]

        boolean hasNumericDay = checkIfNumber(dateComponents[0]); // day
        boolean hasNumericMonth = checkIfNumber(dateComponents[0]); // day
        boolean hasNumericYear = checkIfNumber(dateComponents[0]); // day

        if (hasNumericDay && hasNumericMonth && hasNumericYear) {
            return true;
        }
        return false;
    }

    String[] getDateComponenetsFromString(String dateString) {
        dateString = dateString.replace("/", ""); // remove / i.e ddMMyyyy
        String day = dateString.substring(0, 2);
        String month = dateString.substring(3, 4);
        String year = dateString.substring(5, 8);
        return new String[] {day, month, year};
    }

    boolean checkIfNumber(String toCheck) {
        try {
            int i = Integer.parseInt(toCheck);
            return true;
        } catch (NumberFormatException nfe) { // could not parse therefore not a valid date number
            return false;
        }
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
