package www.softedgenepal.com.softedgenepalschool.Model.URLs;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.FakeApi.StudentApi;

public class URL {
    Context context;
    public String url = new OnlineUrl().onlineUrl;
    public String imgUrl = new OnlineUrl().baseUrl;

    public URL(Context context) {
        this.context = context;
    }

    public URL() {
    }

    public String getLoginUrl() {
        return new OnlineUrl().getLoginUrl();
    }

    public String getProfileUrl() {
        return new OnlineUrl().profile;
    }

    public String getCreateLeaveApplicationUrl() {
        return new OnlineUrl().createLeaveApplication();
    }

    public String getLeaveApplicationUrl() {
        return new OnlineUrl().getGetLeaveApplicationUrl();
    }

    public String cancelLeaveApplicationUrl() {
        return new OnlineUrl().cancelLeaveApplicationUrl;
    }

    public String calenderUrl() {
        return new OnlineUrl().calenderAndEventsUrl;
    }

    public String assignmentUrl() {
        return new OnlineUrl().assignmentUrl;
    }

    public String busRouteUrl() {
        return new OnlineUrl().busRouteUrl;
    }

    public String routineUrl() {
        return new OnlineUrl().routineUrl;
    }

    public String reportCardUrl() {
        return new OnlineUrl().reportCardUrl;
    }

    public String reportCardDetailUrl() {
        return new OnlineUrl().reportCardDetailUrl;
    }

    public String attendanceUrl() {
        return new OnlineUrl().attendanceUrl;
    }

    private class OnlineUrl {
        //private String baseUrl = "http://192.168.0.112:400/";
        private String baseUrl = "http://192.168.100.10:400/";
        private String onlineUrl = baseUrl + "api/data/";
        private String loginUrl = onlineUrl + "Login?";
        private String profile = "getprofile";

        private String createLeaveApplicationUrl = onlineUrl + "studentLeaveApplication";
        private String getLeaveApplicationUrl = onlineUrl + "getLeaveApplication";
        private String cancelLeaveApplicationUrl = onlineUrl + "cancelLeaveApplication";

        private String calenderAndEventsUrl = onlineUrl + "geteventinfo";//?From=10/10/1995&To=12/12/2023";
        //http://192.168.100.100:423/api/data/geteventinfo?From=10/10/1995&To=12/12/2020
        private String assignmentUrl = onlineUrl + "gethomework";
        private String busRouteUrl = onlineUrl + "getbusroutes";

        private String routineUrl = onlineUrl + "getroutine";
        private String attendanceUrl = onlineUrl + "getattendance";

        private String reportCardUrl = onlineUrl + "getpublishedexam";
        private String reportCardDetailUrl = onlineUrl + "getexammarks";

        public OnlineUrl() {
            loginUrl = loginUrl;
        }

        public String getLoginUrl() {
            //192.168.100.100:423/api/auth/Login?UserName=superadmin&Password=admin123
            return loginUrl;
        }

        public String createLeaveApplication() {
            return createLeaveApplicationUrl;
        }

        public String getGetLeaveApplicationUrl() {
            return getLeaveApplicationUrl;
        }
    }

    public static final String getFacultyClassDetail = "getFacultyClassDetail";

}
