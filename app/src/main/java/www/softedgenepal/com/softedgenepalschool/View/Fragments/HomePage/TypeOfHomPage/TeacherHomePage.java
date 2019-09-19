package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ImageUrlFormater;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.ProfileTeacherModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ProfileActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.TeacherProfileActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomFragment;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.TeacherNav;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;
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
    private List<LeaveTypeModel> leaveTypeModelList = new ArrayList<>();
    public static ProfileTeacherModel profileTeacherModel;

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

    public Activity getContext() {
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
        ApiCall.getInstance().connect(getContext(), URL.getFacultyClassDetail, Request.Method.POST, "", null, this, "");
        activity.pendingRequestsCount++;
        ApiCall.getInstance().connect(getContext(), Constants.GetLeaveTypeTeacher, Request.Method.POST, "", null, this, "");
        activity.pendingRequestsCount++;

        String parameters = "?id="+user.Id+"&role="+user.Role;
        ApiCall.getInstance().connect(getContext(), Constants.ProfileTeacher, Request.Method.POST, parameters, null, this, "");
        activity.pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        activity.pendingRequestsCount--;
        try {
            if (isSuccess) {
                switch (url) {
                    case URL.getFacultyClassDetail:
                        JSONArray facultyArray = jsonObject.getJSONArray("Data");
                        facultyClassDetailModels = new Gson().fromJson(facultyArray.toString(), new TypeToken<List<FacultyClassDetailModel>>() {
                        }.getType());

                        TeacherDataStore.FacultyClassDetail.store(getContext(), facultyClassDetailModels);
                        break;

                    case Constants.GetLeaveTypeTeacher:
                        JSONArray leaveTypeArray = jsonObject.getJSONArray("Data");
                        leaveTypeModelList = new Gson().fromJson(leaveTypeArray.toString(), new TypeToken<List<LeaveTypeModel>>() {
                        }.getType());

                        TeacherDataStore.GetLeaveType.store(getContext(), leaveTypeModelList);
                        break;

                    case Constants.ProfileTeacher:
                        JSONObject dataObj = jsonObject.getJSONObject("StaffDetail");
                        online(dataObj);
                        break;

                }
            }else {
                offline();
            }
        } catch (Exception e) {
            e.printStackTrace();
            offline();
        }

        if (progressDialog != null && progressDialog.isShowing() && activity.pendingRequestsCount == 0)
            progressDialog.dismiss();
    }


    @Override
    public void onNetworkFailed(String url, String message) {
        switch (url) {
            case Constants.ProfileTeacher:
                offline();
                break;
        }
    }

    private void online(JSONObject jsonObject) {
        profileTeacherModel = new Gson().fromJson(jsonObject.toString(), new TypeToken<ProfileTeacherModel>() {
        }.getType());

        TeacherDataStore.Profile.store(getContext(), profileTeacherModel);
        populateInView();
    }

    private void offline(){
        profileTeacherModel = (ProfileTeacherModel) TeacherDataStore.Profile.get(getContext(), ProfileTeacherModel.class);
        populateInView();
    }

    private void populateInView() {
        progressBar.setVisibility(View.GONE);

        ShowInGlide glide = new ShowInGlide(getContext());
        glide.loadURL(new ImageUrlFormater().conver(profileTeacherModel.ImageUrl));
        glide.loadFailed(R.drawable.userprofile4);
        glide.show(userProfileImage);

        //username = String.format("%s%s", getContext().getResources().getString(R.string.profile_username), username);
        userNameTextView.setText(String.format("%s%s", getContext().getResources().getString(R.string.profile_username), " " + profileTeacherModel.FullName));
        classTextView.setText(String.format("%s%s", "Position: ", " " + profileTeacherModel.Position));
        roll_SubTextView.setText(String.format("%s%s", "Address: ", " " + profileTeacherModel.TemporaryAddress));

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo teacher user detail page
                Intent intent = new Intent(getContext(), TeacherProfileActivity.class);
                intent.putExtra("profileModel", profileTeacherModel);
                getContext().startActivity(intent);
            }
        });
    }


    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
