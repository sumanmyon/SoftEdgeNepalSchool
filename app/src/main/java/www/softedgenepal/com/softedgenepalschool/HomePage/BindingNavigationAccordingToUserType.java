package www.softedgenepal.com.softedgenepalschool.HomePage;

import android.support.design.widget.NavigationView;

import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.MainActivity.userType;

public class BindingNavigationAccordingToUserType {

    public void setNavigationAccordingToUserType(NavigationView navigationView){
        if(userType.equals("school")){
            navigationView.getMenu().setGroupVisible(R.id.group_for_all, true);
            navigationView.getMenu().setGroupVisible(R.id.group_for_teacher, false);
            navigationView.getMenu().setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("teacher")){
            navigationView.getMenu().setGroupVisible(R.id.group_for_all, true);
            navigationView.getMenu().setGroupVisible(R.id.group_for_teacher, true);
            navigationView.getMenu().setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("student")){
            navigationView.getMenu().setGroupVisible(R.id.group_for_all, true);
            navigationView.getMenu().setGroupVisible(R.id.group_for_teacher, false);
            navigationView.getMenu().setGroupVisible(R.id.group_for_student, true);
        }
    }
}
