package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.AttendanceActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.HomeWorkActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.BusRouteActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication.ShowAllLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ProfileActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ReportCardActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.RoutineActivity;

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
                redirectToActivity(HomeWorkActivity.class);
                break;
            case R.id.studentAttendance:
                redirectToActivity(AttendanceActivity.class);
                break;
            case R.id.routine:
                redirectToActivity(RoutineActivity.class);
                break;
            case R.id.reportCard:
                redirectToActivity(ReportCardActivity.class);
                break;
//            case R.id.studentAccount:
//                showMessage("Coming Soon");
//                break;
            case R.id.studentLeaveApplication:
                redirectToActivity(ShowAllLeaveApplication.class);
                break;
//            case R.id.studentSuggestion:
//                redirectToActivity(SuggestionActivity.class);
//                break;
            case R.id.liveBusTracking:
                redirectToActivity(BusRouteActivity.class);
                break;
            case R.id.studentProfile:
                redirectToActivity(ProfileActivity.class);
                break;

            case R.id.sibling:
                //redirectToActivity(ProfileActivity.class);
                showMessage("Comming Soon");
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
