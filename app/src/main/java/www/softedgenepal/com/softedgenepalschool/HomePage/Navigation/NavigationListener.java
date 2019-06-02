package www.softedgenepal.com.softedgenepalschool.HomePage.Navigation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    String userType;
    DrawerLayout drawerLayout;

    public NavigationListener(Activity activity, String userType, DrawerLayout drawerLayout) {
        this.activity=activity;
        this.userType=userType;
        this.drawerLayout=drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if(userType.equals("school")){
            new SchoolNav(activity, itemId).set();
        }else if(userType.equals("teacher")){
            new SchoolNav(activity, itemId).set();
            new TeacherNav(activity, itemId).set();
        }else if(userType.equals("student")){
            new SchoolNav(activity, itemId).set();
            new StudentNav(activity, itemId).set();
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
