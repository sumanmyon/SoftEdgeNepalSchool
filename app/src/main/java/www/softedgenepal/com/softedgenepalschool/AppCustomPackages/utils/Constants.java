package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import com.google.zxing.common.StringUtils;

public class Constants {
    //Objects.toString(gearBox, "")
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //            displayMessage(Objects.toString(leaveApplicationModelList.get(1).Remarks, "ab"));
    //        }
    //Optional.ofNullable(gearBox).orElse("");

//    If you also want to get rid of "null", you can do:
//            StringUtils.defaultString(str).replaceAll("^null$", "")
//    or to ignore case:
//            StringUtils.defaultString(str).replaceAll("^(?i)null$", "")
//    StringUtils.defaultString(a);

    public static String MY_PREFS_NAME;
    public static final String LoginCredential = "LoginCredential";

    public static String GENERATE_TOKEN;

    //todo for student store in local
    public static final String ProfileStudent = "StudentProfile";


    //todo for teacher store in local
    //for homework
    public static final String FacultyClassDetail = "FacultyClassDetail";
    public static final String CreateHomeWorkTeacher = "createhomework";
    public static final String ShowHomeWorkTeacher = "gethomeworkforteacher";

    //for leave
    public static final String GetLeaveTypeTeacher = "getleavetype";
    public static final String StaffLeaveApplication = "staffleaveapplication";
    public static final String ShowStaffLeaveApplication = "getleaveapplication";
    public static final String CancelStaffLeaveApplication = "cancelLeaveApplication";
    public static final String DeleteStaffLeaveApplication = "deleteLeaveApplication";

    //for profile
    public static final String ProfileTeacher = "getprofile";
}
