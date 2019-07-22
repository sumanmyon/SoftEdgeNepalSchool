package www.softedgenepal.com.softedgenepalschool.Model.URLs;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.FakeApi.StudentApi;

public class URL {
    Context context;
    public String url = new OnlineUrl().onlineUrl;

    public URL(Context context) {
        this.context = context;
    }
    public URL(){}

    public String getUrl(){
        return new OnlineUrl().getLoginStudentUrl();
    }
    public String getCreateLeaveApplicationUrl(){
        return new OnlineUrl().createLeaveApplication();
    }

    public String getLeaveApplicationUrl(){
        return new OnlineUrl().getGetLeaveApplicationUrl();
    }
    public String cancelLeaveApplicationUrl(){
        return new OnlineUrl().cancelLeaveApplicationUrl;
    }

    public String calenderUrl(){
        return new OnlineUrl().calenderAndEventsUrl;
    }

    public String assignmentUrl(){
        return new OnlineUrl().assignmentUrl;
    }

    public String busRouteUrl(){
        return new OnlineUrl().busRouteUrl;
    }

    public String routineUrl(){
        return new OnlineUrl().routineUrl;
    }

    public String attendanceUrl(){
        return new OnlineUrl().attendanceUrl;
    }

    private class OnlineUrl{
        private String onlineUrl = "http://192.168.100.20:400/";
        private String loginStudentUrl = onlineUrl + "api/auth/Login?";

        private String createLeaveApplicationUrl =onlineUrl + "api/data/Leaveapplication";
        private String getLeaveApplicationUrl = onlineUrl + "api/data/getLeaveapplication";
        private String cancelLeaveApplicationUrl = onlineUrl + "api/data/cancelLeaveapplication";

        private String calenderAndEventsUrl = onlineUrl + "api/data/geteventinfo";//?From=10/10/1995&To=12/12/2023";
        //http://192.168.100.100:423/api/data/geteventinfo?From=10/10/1995&To=12/12/2020

        private String assignmentUrl = onlineUrl + "/api/data/gethomework";

        private String busRouteUrl = onlineUrl + "api/data/getbusroutes";

        private String routineUrl = onlineUrl + "api/data/getroutine";

        private String attendanceUrl = onlineUrl + "api/data/getattendance";

        public OnlineUrl() {
            loginStudentUrl = loginStudentUrl + "UserName=suman&Password=admin123";
        }

        public String getLoginStudentUrl() {
            //192.168.100.100:423/api/auth/Login?UserName=superadmin&Password=admin123
            return loginStudentUrl;
        }

        public String createLeaveApplication(){
            return createLeaveApplicationUrl;
        }

        public String getGetLeaveApplicationUrl() {
            return getLeaveApplicationUrl;
        }
    }

    private class OfflineUrl{
        StudentApi studentApi;
        public OfflineUrl() {
            studentApi = new StudentApi(context);
        }

        public String getStudentUrl() {
            return studentApi.getS();
        }
    }
}
