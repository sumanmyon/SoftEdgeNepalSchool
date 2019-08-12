package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import pub.devrel.easypermissions.EasyPermissions;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.NotificationSetting;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Calendar;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Home;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Notification;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.BindingNavigationAccordingToUserType;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.NavigationListener;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.RunTimePermissions.perms;

public class MainActivity extends AppCompatActivity {
    //For Navigation
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationListener navigationListener;
    public static String userType;// = "school";     // userType :: by default is school ,
                                                    // else teacher and student
    public static UserCache userCache;

    //For TabLayout
    private BottomNavigationView bottomNavigationView;

    //Fragments lists
    private Home homeFagment;
    private Notification notificationFragment;
    private Calendar calenderFragment;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;

    private LanguageSettingv2 languageSetting;

    @Override
    protected void onStart() {
        super.onStart();
        //for runtime permissions
        runTimePermissions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(refresh);
    }

    private void runTimePermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            Intent runIntent = new Intent(this, RunTimePermissions.class);
            startActivity(runIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();

        setContentView(R.layout.activity_main);
        //for runtime permissions
        runTimePermissions();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userCache = (UserCache) bundle.getSerializable("userCache");
            userType = userCache.getRole();
            Toast.makeText(MainActivity.this, userCache.getSystemCode(), Toast.LENGTH_SHORT).show();
        }

        //casting
        casting();

        //binding
        binding();

        //drawableLayout
        drawerLayout();

        //navigation
        navigation();

        //bottom navigation
        bottomNavigationView();

        //todo only get notification when setting is turn on
        if(NotificationSetting.getNotification(this).equals("TurnOn")
                || NotificationSetting.getNotification(this).equals("No name defined")) {
            //todo all devices
            FirebaseMessaging.getInstance().subscribeToTopic("allDevices").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d("FirebaseMessaging", msg);
                    //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Toast.makeText(MainActivity.this, String.valueOf(FirebaseInstanceId.getInstance().getInstanceId()), Toast.LENGTH_SHORT).show();

        //initFragment
        initFragment();

        //using fragment to save instance
        setFragmentManager();
    }

    private void casting() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        bottomNavigationView = findViewById(R.id.mainActivity_bottomNavigation);
    }

    private void binding() {
        BindingNavigationAccordingToUserType accordingToUserType = new BindingNavigationAccordingToUserType();
        accordingToUserType.setNavigationAccordingToUserType(navigationView);
    }

    private void drawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void navigation() {
        ImageView navImage = navigationView.getHeaderView(0).findViewById(R.id.navigation_image_view);
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(this, navImage);
        screenSize.setCompitableForHeight(3.5);
        screenSize.setImage();

        navigationListener = new NavigationListener(this, userType, drawerLayout);
        navigationView.setNavigationItemSelectedListener(navigationListener);
    }

    private void initFragment() {
        homeFagment = new Home();
        notificationFragment = new Notification();
        calenderFragment = new Calendar();
        activeFragment = homeFagment;
    }

    private void setFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_container, calenderFragment, "3").hide(calenderFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, notificationFragment, "background_img").hide(notificationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, homeFagment, "1").commit();
    }


    private void bottomNavigationView() {
        bottomNavigationView.animate();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //mTextMessage.setText(item.getTitle());
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
                        fragmentManager.beginTransaction().hide(activeFragment).show(homeFagment).commit();
                        activeFragment = homeFagment;
                        return true;
                    case R.id.navigation_notification:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.grey_700));
                        fragmentManager.beginTransaction().hide(activeFragment).show(notificationFragment).commit();
                        activeFragment = notificationFragment;
                        return true;
                    case R.id.navigation_calender:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        fragmentManager.beginTransaction().hide(activeFragment).show(calenderFragment).commit();
                        activeFragment = calenderFragment;
                        return true;
                }
                return false;
            }
        });
    }
}
