package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Teacher.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomFragment;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.StudentNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.TeacherNav;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class TeacherHomePage implements ApiCall.ResultListener {
    private CustomFragment activity;
    private View view;
    ProgressBar progressBar;
    private MaterialRippleLayout navMenu;

    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;
    public static Cache cache;
    private List<FacultyClassDetailModel> facultyClassDetailModels = new ArrayList<>();

    public TeacherHomePage(CustomFragment activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void setView() {
        //casting
        casting();

        //progress bar
        progressBar.setVisibility(View.VISIBLE);

        //fetch/get data
        loadDataSetup();

        //dashboard
        teacherDashboard();
        schoolDashboard();
    }

    private void teacherDashboard() {
        DashboardCategory category = new DashboardCategory(getContext(), view, userType) {
            @Override
            public void onClickListener(int id) {
                TeacherNav nav = new TeacherNav(getContext(), id);
                nav.set();
            }
        };
        category.columnSpan = 4;
        category.userType = userType;
        category.setDashboard(Resources.teacher(getContext()));
        category.setCategoryVisibilityGone();
    }

    private void schoolDashboard() {
        DashboardCategory category = new DashboardCategory(getContext(), view, "School") {
            @Override
            public void onClickListener(int id) {
                SchoolNav nav = new SchoolNav(getContext(), id);
                nav.set();
            }
        };
        category.columnSpan = 4;
        category.setTopic = getContext().getResources().getString(R.string.Others);
        category.setCategoryText();
        category.setDashboard(Resources.school(getContext()));
    }

    public Activity getContext(){
        return activity.getActivity();
    }

    private void casting() {
        userProfileImage = view.findViewById(R.id.userProfile_ImageView);
        userNameTextView = view.findViewById(R.id.userProfile_nameTextView);
        classTextView = view.findViewById(R.id.userProfile_classTextView);
        roll_SubTextView = view.findViewById(R.id.userProfile_Roll_SubjectTextView);
        progressBar = view.findViewById(R.id.profile_progress_bar);
    }
    private void loadDataSetup() {
        ApiCall.getInstance().connect(getContext(), URL.getFacultyClassDetail, Request.Method.POST,"", null, this, "Please wait...");
        activity.pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        activity.pendingRequestsCount--;
        switch (url) {
            case URL.getFacultyClassDetail:
                try {
                    if (isSuccess) {
                        online(jsonObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (progressDialog != null && progressDialog.isShowing() && activity.pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    private void online(JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("Data");
        facultyClassDetailModels = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FacultyClassDetailModel>>() {
        }.getType());

        TeacherDataStore.FacultyClassDetail.store(getContext(), facultyClassDetailModels);
    }

    @Override
    public void onNetworkFailed(String url, String message) {

    }
}
