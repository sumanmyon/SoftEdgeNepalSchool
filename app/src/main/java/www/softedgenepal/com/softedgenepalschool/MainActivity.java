package www.softedgenepal.com.softedgenepalschool;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.HomePage.Navigation.NavigationListener;

public class MainActivity extends AppCompatActivity {
    //For Navigation
    //private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationListener navigationListener;
    private String userType = "school";     // userType :: by default is school ,
                                        // else teacher and student
    int w,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Actual window display
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

//        DisplayMetrics displayMetrics = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////        int height = displayMetrics.heightPixels;
////        int width = displayMetrics.widthPixels;

        Toast.makeText(this,"w: "+width+"\nh: "+height,Toast.LENGTH_LONG).show();
        w = width;
        h = (int) (height/3.5);

        //casting
        casting();

        //valid user type and then set userType
        //binding
        userType = "school";
        binding();

        //drawableLayout
        drawerLayout();

        //navigation
        navigation();


    }

    private void casting() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
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
        navImage.requestLayout();
        navImage.getLayoutParams().height = h;
        //navImage.getLayoutParams().width = w;
        navImage.setScaleType(ImageView.ScaleType.FIT_XY);

        navigationListener = new NavigationListener(this, userType, drawerLayout);
        navigationView.setNavigationItemSelectedListener(navigationListener);
    }
}
