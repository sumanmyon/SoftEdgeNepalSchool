package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.BindingNavigationAccordingToUserType;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.TabLayoutAdapter;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.NavigationListener;
import www.softedgenepal.com.softedgenepalschool.R;

public class MainActivity extends AppCompatActivity {
    //For Navigation
    //private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationListener navigationListener;
    public static String userType = "student";     // userType :: by default is school ,
                                                  // else teacher and student
    //For TabLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutAdapter tabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting
        casting();

        //binding
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
        BindingNavigationAccordingToUserType accordingToUserType = new BindingNavigationAccordingToUserType();
        accordingToUserType.setNavigationAccordingToUserType(navigationView);
    }

    private void drawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void navigation() {
        ImageView navImage = navigationView.getHeaderView(0).findViewById(R.id.navigation_image_view);
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(this,navImage);
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
