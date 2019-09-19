package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalenderDate;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

public class DateTime {

    public DateTime() {

    }

    public static String splitDateOrTime(String date, String type) {
        //removing created time from created date
        String convert = DateOrTimeFormate2(date);
        String split[] = convert.split("T");
        String dateOrTime = "";
        if (type.equals("date")) dateOrTime = split[0];
        else if (type.equals("time")) {
            dateOrTime = split[1];
        }
        return dateOrTime;
    }

    public static CalenderDate convertToNepali(String[] date) {
        return new CalenderDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2])).convertToNepali();
    }

    public static CalenderDate calenderToEnglish(String[] date) {
        return new CalenderDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2])).convertToEnglish();
    }

    public static String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getTodayDateWithTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String DateConvertToNepali(String date, String dateOrTime) {
        if (!date.equals("null") || date != null || !date.equals("")) {
            String data = splitDateOrTime(date, dateOrTime);
            if (dateOrTime.equals("date")) {
                String[] s = data.split("-");
                return String.valueOf(convertToNepali(s));
            } else {
                return null;
            }
        }else
            return "";

    }

    public static String DateConvertToEnglish(String date, String dateOrTime) {
        String data = splitDateOrTime(date, dateOrTime);
        if (dateOrTime.equals("date")) {
            String[] s = data.split("-");
            return String.valueOf(calenderToEnglish(s));
        } else return null;
    }

    public static String DateTimeConverterForYoutube(String time){
        try {
            time = URLEncoder.encode(time,"utf-8");
            time = time.replace("PT" , "");
            time = time.replace("H" , "h ");
            time = time.replace("M" , "m ");
            time = time.replace("S" , "sec ");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return time;
    }
}
