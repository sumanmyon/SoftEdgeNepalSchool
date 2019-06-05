package www.softedgenepal.com.softedgenepalschool;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.Adapters.FragmentAdapter;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.DisplaySizeInPixel;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.HomePage.Navigation.NavigationListener;
import www.softedgenepal.com.softedgenepalschool.HomePage.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity {
    //For Navigation
    //private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationListener navigationListener;
    public static String userType = "student";     // userType :: by default is school ,
                                                  // else teacher and student
    int w,h;

    //For TabLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutAdapter tabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Actual window display
        DisplaySizeInPixel pixel = new DisplaySizeInPixel(this);
        pixel.setByWindowManager();
        w = pixel.getWidth();
        h = pixel.getHeight();

        //casting
        casting();

        //valid user type and then set userType

        //binding
        //userType = "school";
        binding();

        //drawableLayout
        drawerLayout();

        //navigation
        navigation();

        //Tab Layout
        tabLayout();
    }

    private void casting() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        tabLayout = findViewById(R.id.main_tabLayout);
        viewPager = findViewById(R.id.main_viewPager);
    }

    private void binding(){
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

    private void drawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void navigation() {
        ImageView navImage = navigationView.getHeaderView(0).findViewById(R.id.navigation_image_view);
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(navImage,w,h);
        screenSize.setCompitableForHeight(3.5);
        screenSize.setImage();

        navigationListener = new NavigationListener(this, userType, drawerLayout);
        navigationView.setNavigationItemSelectedListener(navigationListener);
    }

    private void tabLayout(){
        tabLayoutAdapter = new TabLayoutAdapter(this, tabLayout, viewPager, getSupportFragmentManager());
        tabLayoutAdapter.setTablayout(true);
        //tabLayoutAdapter.setIcons();
        tabLayoutAdapter.disableSwipe();
    }


}
