package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication.LeaveApplicationActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication.ShowAllLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SuggestionActivity;

public class StudentNav {
    private Activity activity;
    private int itemId;

    public StudentNav(Activity activity, int itemId) {
        this.activity=activity;
        this.itemId=itemId;
    }

    public void set(){
        switch (itemId){
            case R.id.homeWork:
                showMessage("Coming Soon");
                break;
            case R.id.studentAttendance:
                showMessage("Coming Soon");
                break;
            case R.id.routine:
                showMessage("Coming Soon");
                break;
            case R.id.reportCard:
                showMessage("Coming Soon");
                break;
            case R.id.studentAccount:
                showMessage("Coming Soon");
                break;
            case R.id.studentLeaveApplication:
                redirectToActivity(ShowAllLeaveApplication.class);
                break;
            case R.id.studentSuggestion:
                redirectToActivity(SuggestionActivity.class);
                break;
            case R.id.assignment:
                showMessage("Coming Soon");
                break;
            case R.id.busRoute:
                showMessage("Coming Soon");
                break;
            case R.id.liveBusTracking:
                showMessage("Coming Soon");
                break;
            case R.id.studentProfile:
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
