package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplicationActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SuggestionActivity;

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
                showMessage("Coming Soon");
                break;
            case R.id.teacherAttendance:
                showMessage("Coming Soon");
                break;
            case R.id.classRoutine:
                showMessage("Coming Soon");
                break;
            case R.id.examRoutine:
                showMessage("Coming Soon");
                break;
            case R.id.resultReportCard:
                showMessage("Coming Soon");
                break;
            case R.id.salaryAccount:
                showMessage("Coming Soon");
                break;
            case R.id.teacherLeaveApplication:
                redirectToActivity(LeaveApplicationActivity.class);
                break;
            case R.id.teacherSuggestion:
                redirectToActivity(SuggestionActivity.class);
                break;
            case R.id.teacherProfile:
                showMessage("Coming Soon");
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
