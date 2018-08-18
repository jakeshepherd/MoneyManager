package com.example.jakeshepherd.moneymanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    Date getDateFromString(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH); // 18/08/2018
        Date date = format.parse(dateString);
        System.out.println(date);

        return date;
    }

}
