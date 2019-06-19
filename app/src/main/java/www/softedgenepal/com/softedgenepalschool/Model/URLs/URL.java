package www.softedgenepal.com.softedgenepalschool.Model.URLs;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.FakeApi.StudentApi;

public class URL {
    Context context;
    private String url;

    public URL(Context context) {
        this.context = context;
    }
    public String getUrl(){
        return new OnlineUrl().getLoginStudentUrl();
    }
    public String getCreateLeaveApplicationUrl(){
        return new OnlineUrl().createLeaveApplication();
    }

    public String getLeaveApplicationUrl(){
        return new OnlineUrl().getGetLeaveApplicationUrl();
    }

    private class OnlineUrl{
        private String onlineUrl = "http://192.168.100.100:423/";
        private String loginStudentUrl = onlineUrl + "api/auth/Login?";

        private String createLeaveApplicationUrl =onlineUrl + "api/data/Leaveapplication";
        private String getLeaveApplicationUrl = onlineUrl + "api/data/getLeaveapplication";
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
