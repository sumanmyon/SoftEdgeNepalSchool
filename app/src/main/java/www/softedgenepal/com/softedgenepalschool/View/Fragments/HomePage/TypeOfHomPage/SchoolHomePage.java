package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.content.Context;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.drawerLayout;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.navigationView;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class SchoolHomePage {
    private Activity activity;
    private View view;
    private ImageView schoolLogoImageView;
    private TextView schoolNameTextView,addressTextView, phoneNoTextView, faxTextView, emailTextView, webTextView;

    private MaterialRippleLayout navMenu;

    public SchoolHomePage(Activity activity, View view) {
        this.activity=activity;
        this.view=view;
    }

    public void setView() {
        //casting
        casting();

        //fetch/get data
        schoolDashboard();

        //set data
        setInFields();

        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }

        navMenu = view.findViewById(R.id.navMenu_School);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void schoolDashboard() {
        DashboardCategory category = new DashboardCategory(activity, view, userType) {
            @Override
            public void onClickListener(int id) {
                SchoolNav nav = new SchoolNav(activity, id);
                nav.set();
            }
        };
        category.columnSpan = 4;
        category.setTopic = getContext().getResources().getString(R.string.School);
        category.setCategoryText();
        category.setCategoryVisibilityGone();
        category.setDashboard(Resources.school(getContext()));
    }

    public Context getContext(){
        return activity.getApplicationContext();
    }

    private void setInFields() {
        //for compatible image for different devices
        //at first get display size
        //now set image
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(activity, schoolLogoImageView);
        screenSize.setCompitableForHeight(3);
        schoolLogoImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.logo));

        schoolNameTextView.setText("Welcome to\nSoftEdge Nepal");
        addressTextView.setText("Singhadurbar East Gate, Anamnagar, Kathmandu, Nepal");
        phoneNoTextView.setText("+977-9841729323\n+977-9801029323");
        faxTextView.setText("info@softedgenepal.com");
        emailTextView.setText("info@softedgenepal.com");
        webTextView.setText("https://softedgenepal.com");
    }

    private void casting() {
        schoolLogoImageView = view.findViewById(R.id.schoolLogo_imageView);
        schoolNameTextView = view.findViewById(R.id.schoolName_TextView);
        addressTextView = view.findViewById(R.id.schoolAddress_TextView);
        phoneNoTextView = view.findViewById(R.id.schoolPhoneNo_TextView);
        faxTextView = view.findViewById(R.id.schoolFax_TextView);
        emailTextView = view.findViewById(R.id.schoolEmail_TextView);
        webTextView = view.findViewById(R.id.schoolWeb_TextView);
    }
}
