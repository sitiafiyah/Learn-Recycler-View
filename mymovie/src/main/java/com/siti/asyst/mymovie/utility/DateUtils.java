package com.siti.asyst.mymovie.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String currentFormat, String resultFormat, String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormatResult = new SimpleDateFormat(resultFormat, Locale.getDefault());
        return simpleDateFormatResult.format(newDate);
    }

    public static Date dateToString(String currentFormat, String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

}
