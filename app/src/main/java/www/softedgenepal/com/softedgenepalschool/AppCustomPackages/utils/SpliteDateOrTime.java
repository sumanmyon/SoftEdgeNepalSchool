package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.Date;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

public class SpliteDateOrTime {
    public static String splitDateOrTime(String date, String type){
        //removing created time from created date
        String convert = DateOrTimeFormate2(date);
        String split[] = convert.split("T");
        String dateOrTime = "";
        if(type.equals("date"))
            dateOrTime = split[0];
        else if(type.equals("time")){
            dateOrTime =split[1];
        }
        return dateOrTime;
    }

    public static Date convertToNepali(String[] date){
        return new Date(
                Integer.valueOf(date[0]),
                Integer.valueOf(date[1]),
                Integer.valueOf(date[2]))
                .convertToNepali();
    }

}
