package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class StoreInSharePreference {
    Context context;
    SharedPreferences prefs;
    static String MY_PREFS_NAME;
    private String stringName;
    public String Calender = "calender";
    public String Assignment = "assignment";
    public String BusRoute = "busroute";
    public String Routine = "routine";
    public String LeaveApplication = "leave_application";
    public String Attendance = "attendance";
    public String ReportCard = "report_card";
    public String ReportDetailCard = "report_detail_card";

    //todo for Setting
    public String Language = "language";
    public String ReportCardSetting = "ReportCardSetting";
    public String NotificationSetting = "NotificationSetting";
    public String BaseUrlSetting = "BaseUrlSetting";

    //todo for login
    public String LoginCredential = "LoginCredential";

    public StoreInSharePreference(Context context) {
        this.context = context;
    }

    public void setType(String type) {
        if (type.equals(Calender)) {
            MY_PREFS_NAME = "Calender";
            stringName = Calender;
        }

        if (type.equals(Assignment)) {
            MY_PREFS_NAME = "Assignment";
            stringName = Assignment;
        }

        if (type.equals(BusRoute)) {
            MY_PREFS_NAME = "BusRoute";
            stringName = BusRoute;
        }

        if (type.equals(Routine)) {
            MY_PREFS_NAME = "Routine";
            stringName = Routine;
        }

        if (type.equals(LeaveApplication)) {
            MY_PREFS_NAME = "LeaveApplication";
            stringName = LeaveApplication;
        }

        if (type.equals(Attendance)) {
            MY_PREFS_NAME = "Attendance";
            stringName = Attendance;
        }

        if (type.equals(ReportCard)) {
            MY_PREFS_NAME = "ReportCard";
            stringName = ReportCard;
        }

        if (type.equals(ReportDetailCard)) {
            MY_PREFS_NAME = "ReportDetailCard";
            stringName = ReportDetailCard;
        }

        if (type.equals(Language)) {
            MY_PREFS_NAME = "Language";
            stringName = Language;
        }

        if (type.equals(ReportCardSetting)) {
            MY_PREFS_NAME = "ReportCardSetting";
            stringName = ReportCardSetting;
        }

        if (type.equals(NotificationSetting)) {
            MY_PREFS_NAME = "NotificationSetting";
            stringName = NotificationSetting;
        }

        if (type.equals(BaseUrlSetting)) {
            MY_PREFS_NAME = "BaseUrlSetting";
            stringName = BaseUrlSetting;
        }

        if (type.equals(LoginCredential)) {
            MY_PREFS_NAME = "LoginCredential";
            stringName = LoginCredential;
        }
        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    public void storeData(String data) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(stringName, data);
        editor.apply();
    }

    public String getData() {
        String getUserData = null;
        try {
            String data = prefs.getString(stringName, "No name defined");//"No name defined" is the default value.
            getUserData = data;
        } catch (Exception e) {
            getUserData = null;
        }
        //showMessage(getUserData);

        return getUserData;
    }

    //todo Removing single preference:
    public void clear() {
        prefs.edit().remove(stringName).apply();  //commit()
    }

    //todo Removing all preferences:
    public void clearAll() {
        prefs.edit().clear().apply();
    }

    private void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
