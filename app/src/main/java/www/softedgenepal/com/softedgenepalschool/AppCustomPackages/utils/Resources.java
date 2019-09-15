package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class Resources {
    //todo for student
    public static List<Dashboard> student(Context context){
                List<Dashboard> studentDashboard = new ArrayList<>();
        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.HomeWork),
                context.getResources().getDrawable(R.drawable.ic_homework),
                R.id.homeWork));
        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.StudentAttendance),
                context.getResources().getDrawable(R.drawable.ic_attendance),
                R.id.studentAttendance));
        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.Routine),
                context.getResources().getDrawable(R.drawable.ic_assignment),
                R.id.routine));

        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.ReportCard),
                context.getResources().getDrawable(R.drawable.ic_report),
                R.id.reportCard));
        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.StudentLeaveApplication),
                context.getResources().getDrawable(R.drawable.ic_leave_application),
                R.id.studentLeaveApplication));
        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.LiveBusTracking),
                context.getResources().getDrawable(R.drawable.ic_directions_bus),
                R.id.liveBusTracking));

        studentDashboard.add(new Dashboard(context.getResources().getString(R.string.StudentProfile),
                context.getResources().getDrawable(R.drawable.ic_profile_img),
                R.id.studentProfile));

        return studentDashboard;
    }

    public static List<Dashboard> teacher(Context context){
        List<Dashboard> teacherDashboard = new ArrayList<>();
        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.AssignHomeWork),
                context.getResources().getDrawable(R.drawable.ic_homework),
                R.id.assignHomeWork));
        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.TeacherAttendance),
                context.getResources().getDrawable(R.drawable.ic_attendance),
                R.id.teacherAttendance));
        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.ExamRoutine),
                context.getResources().getDrawable(R.drawable.ic_assignment),
                R.id.examRoutine));

        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.ResultReportCard),
                context.getResources().getDrawable(R.drawable.ic_report),
                R.id.resultReportCard));
        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.SalaryAccount),
                context.getResources().getDrawable(R.drawable.ic_account),
                R.id.salaryAccount));
        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.TeacherLeaveApplication),
                context.getResources().getDrawable(R.drawable.ic_leave_application),
                R.id.teacherLeaveApplication));

        teacherDashboard.add(new Dashboard(context.getResources().getString(R.string.TeacherProfile),
                context.getResources().getDrawable(R.drawable.ic_profile_img),
                R.id.teacherProfile));

        return teacherDashboard;
    }



    //todo for school
    public static List<Dashboard> school(Context context){
        List<Dashboard> schoolDashboard = new ArrayList<>();

        schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Event),
                context.getResources().getDrawable(R.drawable.ic_calender_white),
                R.id.event));
        schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Gallery),
                context.getResources().getDrawable(R.drawable.ic_gallery),
                R.id.gallery));
        schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Videos),
                context.getResources().getDrawable(R.drawable.ic_video),
                R.id.videos));

        schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.About),
                context.getResources().getDrawable(R.drawable.ic_about_us),
                R.id.about));
        schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Setting),
                context.getResources().getDrawable(R.drawable.ic_setting),
                R.id.setting));
        if(!userType.equals("School")) {
            schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Logout), context.getResources().getDrawable(R.drawable.ic_logout), R.id.logout));
        }else {
            schoolDashboard.add(new Dashboard(context.getResources().getString(R.string.Login), context.getResources().getDrawable(R.drawable.ic_login), R.id.login));
        }

        return schoolDashboard;
    }

    public static class Dashboard{
        public String topic;
        public Drawable icon;
        public int id;

        public Dashboard(String topic, Drawable icon, int id) {
            this.topic = topic;
            this.icon = icon;
            this.id = id;
        }
    }
}
