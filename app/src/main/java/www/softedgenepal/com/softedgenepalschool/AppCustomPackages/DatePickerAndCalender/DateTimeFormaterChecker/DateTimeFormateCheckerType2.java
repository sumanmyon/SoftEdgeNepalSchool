package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormateCheckerType2 {
    private static final String[] DATETIME_FORMATES = {
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss.SSS",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy MM dd'T'HH:mm:ss.SSS",
            "yyyy MM dd HH:mm:ss.SSS",
            "yyyy-MM-dd",
            "EEE, dd MMM yyyy HH:mm:ss z", // RFC_822
            "EEE, dd MMM yyyy HH:mm zzzz",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", // Blogger Atom feed has millisecs also
            "yyyy-MM-dd'T'HH:mm:sszzzz",
            "yyyy-MM-dd'T'HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ssz", // ISO_8601
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HHmmss.SSSz",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "EEEE, d MMMM yyyy",
            "dd.MM.yyyy HH:mm:ss",
            "dd.MM.yy HH:mm:ss",
            "dd.MM.yyyy HH:mm",
            "MMM d, yyyy",
            "MMMM d, yyyy",
            "EEEE, MMMM d, yyyy",
            "MMM d yyyy",
            "MMMM d yyyy"
    };

    private static Date formatedDate;

    public static String DateOrTimeFormate2(String s) {
        String DateTime = "";
        for (String pattern : DATETIME_FORMATES){
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            try {
                Date dateTime = format.parse(s);
                formatedDate = dateTime;
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                DateTime = outputFormat.format(dateTime);
                break;
            }catch (Exception e){
                //setMessage(e.toString());
            }
        }
        return DateTime;
    }

    //todo get formated Date in Date form
    public Date getFormatedDate(){
        return formatedDate;
    }
}
