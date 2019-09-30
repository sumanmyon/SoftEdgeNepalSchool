package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.HomeWork.ShowTeacherHomeWork;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication.ShowTeacherLeaveAppActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.TeacherAttendance;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.TeacherClassRoutineActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.TeacherClassRoutinev2Activity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.TeacherProfileActivity;

import static www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.TeacherHomePage.profileTeacherModel;

public class TeacherNav {
    private Activity activity;
    private int itemId;

    public TeacherNav(Activity activity, int itemId) {
        this.activity=activity;
        this.itemId=itemId;
    }

    public void set(){
        switch (itemId){
            case R.id.assignHomeWork:
                redirectToActivity(ShowTeacherHomeWork.class);
                break;

            case R.id.teacherAttendance:
                redirectToActivity(TeacherAttendance.class);
                break;

            case R.id.classRoutine:
                redirectToActivity(TeacherClassRoutinev2Activity.class);
                break;

//            case R.id.examRoutine:
//                showMessage("Coming Soon");
//                break;

            case R.id.resultReportCard:
                showMessage("Coming Soon");
                break;

            case R.id.salaryAccount:
                showMessage("Coming Soon");
                break;

            case R.id.teacherLeaveApplication:
                redirectToActivity(ShowTeacherLeaveAppActivity.class);
                break;

            case R.id.teacherProfile:
                Intent intent = new Intent(activity, TeacherProfileActivity.class);
                intent.putExtra("profileModel", profileTeacherModel);
                activity.startActivity(intent);
                break;

            default:
                break;
        }
    }
    private void redirectToActivity(Class<?> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    private void showMessage(String message){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
