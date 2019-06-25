package www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication;

import java.io.Serializable;

public class LeaveApplicationDataCache implements Serializable {
    public String SystemCode;
    public String StudentID;
    public String Subject;
    public String Message;
    public String From;
    public String To;
    public String CreateDate;
    public String CreateTime;
    public String IsActive;

    public LeaveApplicationDataCache(String systemCode, String studentID, String subject,
                                     String message, String from, String to,
                                     String createDate, String createTime, String isActive) {
        SystemCode = systemCode;
        StudentID = studentID;
        Subject = subject;
        Message = message;
        From = from;
        To = to;
        CreateDate = createDate;
        CreateTime = createTime;
        IsActive = isActive;
    }
}
