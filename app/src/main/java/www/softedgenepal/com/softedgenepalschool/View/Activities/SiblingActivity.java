package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.DashboardCategory;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.StudentNav;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class SiblingActivity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private Activity activity;
    private View view;
    private CircleImageView userProfileImage;
    private TextView classTextView, roll_SubTextView;
    private StudentDataCache studentDataCache;
    public static StudentProfileModel siblingProfileModel;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sibling);

        activity = this;
        casting();

        Bundle bundle = getIntent().getExtras();
        studentDataCache = (StudentDataCache) bundle.getSerializable("SiblingData");
        uid = studentDataCache.RegistrationNo;

        toolBarTitle.setText(studentDataCache.StudentName);

        //dashboard
        studentDashboard();


        ShowInGlide glide = new ShowInGlide(activity);
        glide.loadURL(studentDataCache.ImageUrl);
        glide.loadFailed(R.drawable.userprofile4);
        glide.show(userProfileImage);

        classTextView.setText(String.format("%s%s", getResources().getString(R.string.profile_class), " " + studentDataCache.ClassName + ", " + studentDataCache.SectionName));
        roll_SubTextView.setText(String.format("%s%s", getResources().getString(R.string.profile_rollno), " " + studentDataCache.RollNo));

        callApi();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void studentDashboard() {
        DashboardCategory category = new DashboardCategory(activity, view, userType) {
            @Override
            public void onClickListener(int id) {
                StudentNav nav = new StudentNav(activity, id);
                nav.set(uid);
            }
        };
        category.columnSpan = 4;
        category.userType = userType;
        category.setDashboard(Resources.student(activity));
        category.setCategoryVisibilityGone();
    }

    @Override
    protected void casting() {
        super.casting();
        view = findViewById(R.id.scrl);
        userProfileImage = view.findViewById(R.id.profile_ImageView);
        classTextView = view.findViewById(R.id.profile_Class);
        roll_SubTextView = view.findViewById(R.id.profile_RollNo);
    }

    private void callApi(){
        String parameters = "?role=" + user.Role + "&id=" + uid;
        ApiCall.getInstance().connect(this, new URL().getProfileUrl(), Request.Method.POST, parameters, null, this, getString(R.string.Loading));
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

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        offline();
    }


    private void online(JSONObject jsonObject) {
        siblingProfileModel = new Gson().fromJson(jsonObject.toString(), new TypeToken<StudentProfileModel>() {
        }.getType());
        StudentDataStore.Profile.store(this, uid, siblingProfileModel);
    }

    private void offline() {
        siblingProfileModel = (StudentProfileModel) StudentDataStore.Profile.get(this, uid, StudentProfileModel.class);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
