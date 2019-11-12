package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    String userType;
    String registrationNo;
    DrawerLayout drawerLayout;

    public NavigationListener(Activity activity, String userType, String registrationNo, DrawerLayout drawerLayout) {
        this.activity=activity;
        this.userType=userType;
        this.registrationNo = registrationNo;
        this.drawerLayout=drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if(userType.equals("School")){
            new SchoolNav(activity, itemId).set();
        }else if(userType.equals("Teacher")){
            new SchoolNav(activity, itemId).set();
            new TeacherNav(activity, itemId).set();
        }else if(userType.equals("Student")){
            new SchoolNav(activity, itemId).set();
            new StudentNav(activity, itemId).set(registrationNo);
        }

        menuItem.setChecked(true);
        if(drawerLayout!=null)
            drawerLayout.closeDrawers();
        return true;
    }
}
