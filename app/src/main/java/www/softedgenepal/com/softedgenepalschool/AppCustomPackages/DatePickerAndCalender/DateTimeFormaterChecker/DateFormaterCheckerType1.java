package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormaterCheckerType1 {

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,background_img}-\\d{1,background_img}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,background_img}-\\d{1,background_img}$", "yyyy-MM-dd");
        put("^\\d{1,background_img}/\\d{1,background_img}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,background_img}/\\d{1,background_img}$", "yyyy/MM/dd");
        put("^\\d{1,background_img}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,background_img}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,background_img}-\\d{1,background_img}-\\d{4}\\s\\d{1,background_img}:\\d{background_img}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,background_img}-\\d{1,background_img}\\s\\d{1,background_img}:\\d{background_img}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,background_img}/\\d{1,background_img}/\\d{4}\\s\\d{1,background_img}:\\d{background_img}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,background_img}/\\d{1,background_img}\\s\\d{1,background_img}:\\d{background_img}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,background_img}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,background_img}:\\d{background_img}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,background_img}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,background_img}:\\d{background_img}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,background_img}-\\d{1,background_img}-\\d{4}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,background_img}-\\d{1,background_img}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,background_img}/\\d{1,background_img}/\\d{4}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,background_img}/\\d{1,background_img}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,background_img}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,background_img}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,background_img}:\\d{background_img}:\\d{background_img}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {
        String DateTime = "";
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                SimpleDateFormat format = new SimpleDateFormat(dateString);
                try {
                    Date dateTime = format.parse(regexp);
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    DateTime = outputFormat.format(dateTime);
                    //DateTime = String.valueOf(dateTime.getDate());
                }catch (Exception e){
                    //setMessage(e.toString());
                }
                //return DATE_FORMAT_REGEXPS.get(regexp);
                return DateTime;
            }
        }
        return null; // Unknown format.
    }

}
