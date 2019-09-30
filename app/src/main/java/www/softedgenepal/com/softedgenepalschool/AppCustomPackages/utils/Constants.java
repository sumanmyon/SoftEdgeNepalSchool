package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import com.google.zxing.common.StringUtils;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.YoutubeUrlSetting;

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
    public static String DEVICE_MODEL_NAME_AND_IMEI;

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

    //for class routine
    public static final String ClassRoutineTeacher = "getClassRoutineForTeacher";


    //todo for school
    //for youtube
    //https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&playlistId=PL59C5594169CCAB9B&maxResults=50&key=AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk
    //https://www.googleapis.com/youtube/v3/videos?id=9bZkp7q19f0&part=contentDetails&key=AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk
    public static final String Youtube_APIkey="AIzaSyBBMxBwRLaRxACVkHRW-1hWjolNMjVAD3s";//"AIzaSyCl6oVQxU3sGNHcjvFz6UOVnGMDikyhxmk";
    public static final String YouTube_BaseUrl = "https://www.googleapis.com/youtube/v3/";

    //for playlist
    public static final String YouTube_PlayList = "playlistItems";
    public static final String YouTube_playlistId = "PLzcb1fwVs1bNPdXg1WafBGLTGCjPNzlTd";
    public static final String YouTube_maxResults = "50";

    //for video
    public static final String YouTube_Video = "videos";
}
