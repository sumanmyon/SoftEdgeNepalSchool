package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.StudentNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.TeacherNav;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class TeacherHomePage {
    private Activity activity;
    private View view;
    ProgressBar progressBar;
    private MaterialRippleLayout navMenu;

    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;
    public static Cache cache;

    public TeacherHomePage(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void setView() {
        //casting
        casting();

        //progress bar
        progressBar.setVisibility(View.VISIBLE);

        //fetch/get data

        //dashboard
        teacherDashboard();
        schoolDashboard();
    }

    private void teacherDashboard() {
        DashboardCategory category = new DashboardCategory(activity, view, userType) {
            @Override
            public void onClickListener(int id) {
                TeacherNav nav = new TeacherNav(activity, id);
                nav.set();
            }
        };
        category.columnSpan = 4;
        category.userType = userType;
        category.setDashboard(Resources.teacher(getContext()));
        category.setCategoryVisibilityGone();
    }

    private void schoolDashboard() {
        DashboardCategory category = new DashboardCategory(activity, view, "School") {
            @Override
            public void onClickListener(int id) {
                SchoolNav nav = new SchoolNav(activity, id);
                nav.set();
            }
        };
        category.columnSpan = 4;
        category.setTopic = getContext().getResources().getString(R.string.Others);
        category.setCategoryText();
        category.setDashboard(Resources.school(getContext()));
    }

    public Context getContext(){
        return activity.getApplicationContext();
    }

    private void casting() {
        userProfileImage = view.findViewById(R.id.userProfile_ImageView);
        userNameTextView = view.findViewById(R.id.userProfile_nameTextView);
        classTextView = view.findViewById(R.id.userProfile_classTextView);
        roll_SubTextView = view.findViewById(R.id.userProfile_Roll_SubjectTextView);
        progressBar = view.findViewById(R.id.profile_progress_bar);
    }
}
