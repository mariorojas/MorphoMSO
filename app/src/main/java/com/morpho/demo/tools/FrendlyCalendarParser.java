/**
 *
 */
package com.morpho.demo.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Alex
 *
 */
public class FrendlyCalendarParser {

    public static String getDayFrendly(int day) {

        String dayOfWeek = null;

        switch (day) {
            case Calendar.MONDAY:
                dayOfWeek = "LUN";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "MAR";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "MIE";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "JUE";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "VIE";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "SAB";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "DOM";
                break;
        }

        return dayOfWeek;

    }

    public static String getDayFrendlyFull(int day) {

        String dayOfWeek = null;

        switch (day) {
            case Calendar.MONDAY:
                dayOfWeek = "LUNES";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "MARTES";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "MIÉRCOLES";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "JUEVES";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "VIERNES";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "SABADO";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "DOMINGO";
                break;
        }

        return dayOfWeek;

    }

    public static String getDateToStore(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        return dateFormat.format(calendar.getTime());
    }

    public static String getNiceStringDatefromString(Calendar date, String charSeparator){
        String res = null;
        if(date != null) {
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int day = date.get(Calendar.DATE);
            if(NumericTool.isStringNunnable(charSeparator))
                charSeparator = " / ";

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(day);
            stringBuilder.append(charSeparator);
            stringBuilder.append(FrendlyCalendarParser.getMonthFrendly(month));
            stringBuilder.append(charSeparator);
            stringBuilder.append(year);

            res = stringBuilder.toString();
        }
        return res;
    }

    public static String getNormalizatedTime(int hour, int minute) {
        String time = null;

        if (hour <= 9)
            time = "0" + hour;
        else
            time = "" + hour;

        if (minute <= 9)
            time = time + ":0" + minute;
        else
            time = time + ":" + minute;

        return time;
    }


    public static String getShortMonthFrendly(int month) {

        String frendlyMonth = null;

        switch (month) {
            case Calendar.JANUARY:
                frendlyMonth = "ENE";
                break;
            case Calendar.FEBRUARY:
                frendlyMonth = "FEB";
                break;
            case Calendar.MARCH:
                frendlyMonth = "MAR";
                break;
            case Calendar.APRIL:
                frendlyMonth = "ABR";
                break;
            case Calendar.MAY:
                frendlyMonth = "MAY";
                break;
            case Calendar.JUNE:
                frendlyMonth = "JUN";
                break;
            case Calendar.JULY:
                frendlyMonth = "JUL";
                break;
            case Calendar.AUGUST:
                frendlyMonth = "AGO";
                break;
            case Calendar.SEPTEMBER:
                frendlyMonth = "SEP";
                break;
            case Calendar.OCTOBER:
                frendlyMonth = "OCT";
                break;
            case Calendar.NOVEMBER:
                frendlyMonth = "NOV";
                break;
            case Calendar.DECEMBER:
                frendlyMonth = "DIC";
                break;
        }

        return frendlyMonth;

    }

    public static String getMonthFrendly(int month) {

        String frendlyMonth = null;

        switch (month) {
            case Calendar.JANUARY:
                frendlyMonth = "ENERO";
                break;
            case Calendar.FEBRUARY:
                frendlyMonth = "FEBRERO";
                break;
            case Calendar.MARCH:
                frendlyMonth = "MARZO";
                break;
            case Calendar.APRIL:
                frendlyMonth = "ABRIL";
                break;
            case Calendar.MAY:
                frendlyMonth = "MAYO";
                break;
            case Calendar.JUNE:
                frendlyMonth = "JUNIO";
                break;
            case Calendar.JULY:
                frendlyMonth = "JULIO";
                break;
            case Calendar.AUGUST:
                frendlyMonth = "AGOSTO";
                break;
            case Calendar.SEPTEMBER:
                frendlyMonth = "SEPTIEMBRE";
                break;
            case Calendar.OCTOBER:
                frendlyMonth = "OCTUBRE";
                break;
            case Calendar.NOVEMBER:
                frendlyMonth = "NOVIEMBRE";
                break;
            case Calendar.DECEMBER:
                frendlyMonth = "DICIEMBRE";
                break;
        }

        return frendlyMonth;

    }

    public static int getMonthNumber(String month) {

        int frendlyMonth = 0;

        if ((month.indexOf("Ene") == 0) || (month.indexOf("ENE") == 0))
            frendlyMonth = 1;
        else if ((month.indexOf("Feb") == 0) || (month.indexOf("FEB") == 0))
            frendlyMonth = 2;
        else if ((month.indexOf("Mar") == 0) || (month.indexOf("MAR") == 0))
            frendlyMonth = 3;
        else if ((month.indexOf("Abr") == 0) || (month.indexOf("ABR") == 0))
            frendlyMonth = 4;
        else if ((month.indexOf("May") == 0) || (month.indexOf("MAY") == 0))
            frendlyMonth = 5;
        else if ((month.indexOf("Jun") == 0) || (month.indexOf("JUN") == 0))
            frendlyMonth = 6;
        else if ((month.indexOf("Jul") == 0) || (month.indexOf("JUL") == 0))
            frendlyMonth = 7;
        else if ((month.indexOf("Ago") == 0) || (month.indexOf("AGO") == 0))
            frendlyMonth = 8;
        else if ((month.indexOf("Sep") == 0) || (month.indexOf("SEP") == 0))
            frendlyMonth = 9;
        else if ((month.indexOf("Oct") == 0) || (month.indexOf("OCT") == 0))
            frendlyMonth = 10;
        else if ((month.indexOf("Nov") == 0) || (month.indexOf("NOV") == 0))
            frendlyMonth = 11;
        else if ((month.indexOf("Dic") == 0) || (month.indexOf("DIC") == 0))
            frendlyMonth = 12;

        return frendlyMonth;

    }


    public static Calendar getDateFromString(String date,String separator){
        Calendar calendar = null;
        String dateParts[];
        if(date != null){
            try {
                dateParts = date.split(separator);
                if (dateParts != null && dateParts.length > 1) {
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
                    calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1])-1);
                    calendar.set(Calendar.DATE, Integer.parseInt(dateParts[0]));
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
                return null;
            }
        }

        return calendar;
    }

    public static Calendar getCalendarFromString(String date){
        Calendar calendar = null;
        String dateParts[];
        if(date != null){
            try {
                dateParts = date.split("T");
                if (dateParts != null && dateParts.length > 1) {
                    date = dateParts[0];
                    dateParts = date.split("-");
                    if (dateParts != null && dateParts.length > 1) {
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
                        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
                        calendar.set(Calendar.DATE, Integer.parseInt(dateParts[2]));
                    }
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
                return null;
            }
        }

        return calendar;
    }

    public static String parseWSDate(String date){
        String dateParts[];
        if(date != null){
            try {
                dateParts = date.split("T");
                if (dateParts != null && dateParts.length > 1) {
                    date = dateParts[0];
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
                return null;
            }
        }

        return date;
    }

}
