package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.Contractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ProfileActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.StudentNav;
import www.softedgenepal.com.softedgenepalschool.View.Sibling.SiblingPopUpMenu;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class StudentHomePage implements Contractor.View, ApiCall.ResultListener {
    private Activity activity;
    private View view;
    ProgressBar progressBar;
    private MaterialRippleLayout navMenu;

    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;
    public static Cache cache;
    public static StudentProfileModel studentProfileModellist;
    private StudentProfileModel studentProfileModel;

    public static List<StudentDataCache> studentDataCacheList;

    public StudentHomePage(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
        //setMessage(user.Id);
    }

    public void setView() {
        //casting
        casting();

        //progress bar
        //progressBar.setVisibility(View.VISIBLE);

        //fetch/get data
        fetchStudentProfile();

        //dashboard
        studentDashboard();
        schoolDashboard();
    }

    private void fetchStudentProfile() {
        String parameters = "?role=" + user.Role + "&id=" + user.Id;
        ApiCall.getInstance().connect(getContext(), new URL().getProfileUrl(), Request.Method.POST, parameters, null, this, "");
    }

    private void studentDashboard() {
        DashboardCategory category = new DashboardCategory(activity, view, userType) {
            @Override
            public void onClickListener(int id) {
                StudentNav nav = new StudentNav(activity, id);
                nav.set(user.Id);
            }
        };
        category.columnSpan = 4;
        category.userType = userType;
        category.setDashboard(Resources.student(getContext()));
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

    public void userDataList(Cache cache) {
        this.cache = cache;
    }

    private void setData(String username, String classSection, String rollno, String imageUrl) {
        progressBar.setVisibility(View.INVISIBLE);

        ShowInGlide glide = new ShowInGlide(activity);
        glide.loadURL(imageUrl);
        glide.loadFailed(R.drawable.userprofile4);
        glide.show(userProfileImage);

        userNameTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_username), " " + username));
        classTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_class), " " + classSection));
        roll_SubTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_rollno), " " + rollno));

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo student user detail page
                Intent profileIntent = new Intent(activity, ProfileActivity.class);
                activity.startActivity(profileIntent);
            }
        });
    }

    private void casting() {
        userProfileImage = view.findViewById(R.id.userProfile_ImageView);
        userNameTextView = view.findViewById(R.id.userProfile_nameTextView);
        classTextView = view.findViewById(R.id.userProfile_classTextView);
        roll_SubTextView = view.findViewById(R.id.userProfile_Roll_SubjectTextView);
        progressBar = view.findViewById(R.id.profile_progress_bar);
    }

    public Context getContext() {
        return activity.getApplicationContext();
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public void siblingMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sibling:
                if (studentProfileModel.Data.HasSibling.equals(true)) {
                    if (studentProfileModel.SiblingDetail != null) {
                        contextMenu();
                    } else {
                        setMessage(getContext().getString(R.string.profile_noSiblling));
                    }
                } else {
                    setMessage(getContext().getString(R.string.profile_noSiblling));
                }
                break;
        }
    }

    //todo create different class for this
    private void contextMenu() {
        SiblingPopUpMenu popUpMenu = new SiblingPopUpMenu(activity, studentProfileModel.SiblingDetail);
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        try {
            if (isSuccess) {
                online(jsonObject);
            } else {
                showMessage(volleyError.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        offline();
    }


    private void online(JSONObject jsonObject) {
        studentProfileModel = new Gson().fromJson(jsonObject.toString(), new TypeToken<StudentProfileModel>() {
        }.getType());
        studentProfileModellist = studentProfileModel;
        StudentDataStore.Profile.store(getContext(), user.Id, studentProfileModel);
        populateInView();
    }

    private void offline() {
        studentProfileModel = (StudentProfileModel) StudentDataStore.Profile.get(getContext(), user.Id, StudentProfileModel.class);
        studentProfileModellist = studentProfileModel;
        populateInView();
    }

    private void populateInView() {
        progressBar.setVisibility(View.INVISIBLE);

        ShowInGlide glide = new ShowInGlide(activity);
        glide.loadURL(studentProfileModel.StudentDetail.ImageUrl);
        glide.loadFailed(R.drawable.userprofile4);
        glide.show(userProfileImage);

        userNameTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_username), " " + studentProfileModel.StudentDetail.StudentName));
        classTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_class), " " + studentProfileModel.StudentDetail.ClassName + ", " + studentProfileModel.StudentDetail.SectionName));
        roll_SubTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_rollno), " " + studentProfileModel.StudentDetail.RollNo));

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo student user detail page
                Intent profileIntent = new Intent(activity, ProfileActivity.class);
                profileIntent.putExtra("cache", studentProfileModel);
                activity.startActivity(profileIntent);
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
