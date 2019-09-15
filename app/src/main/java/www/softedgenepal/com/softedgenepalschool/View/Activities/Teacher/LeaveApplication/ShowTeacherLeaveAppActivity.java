package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.TeacherLeaveAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class ShowTeacherLeaveAppActivity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private View bottom_sheet;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    private UserModel userModel = user;
    private List<LeaveTypeModel> leaveTypeModelList;
    private List<LeaveApplicationModel> leaveApplicationModelList;

    @Override
    public void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_leave_app);

        casting();
        toolBarTitle.setText(getString(R.string.ShowAllLeaveApplication_ToolBar));

        loadLeaveType();
        loadStaffLeaveApplications();

        fab.setOnClickListener(this);
    }

    private void loadLeaveType() {
        Type type = new TypeToken<List<LeaveTypeModel>>() {
        }.getType();
        leaveTypeModelList = (List<LeaveTypeModel>) TeacherDataStore.GetLeaveType.get(this, type);
    }

    private void loadStaffLeaveApplications() {
        String parameters = "?UserId="+userModel.Id + "&Role="+userModel.Role + "&From=12/12/2018&To=";
        ApiCall.getInstance().connect(this, Constants.ShowStaffLeaveApplication, Request.Method.POST, parameters, null, this, "Please wait....");
        pendingRequestsCount++;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();

        if(id == fab.getId()){
            redirect(CreateTeacherLeaveAppActivity.class);
        }
    }

    @Override
    protected void casting() {
        super.casting();
        fab = findViewById(R.id.floating_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;

        if(isSuccess){
            online(jsonObject);
        }else {
            offline();
        }


        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        offline();

    }

    private void online(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            leaveApplicationModelList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<LeaveApplicationModel>>() {
            }.getType());

            TeacherDataStore.ShowLeaveApplication.store(this, leaveApplicationModelList);
            populateInView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void offline() {
        Type type = new TypeToken<List<LeaveApplicationModel>>() {
        }.getType();

        leaveApplicationModelList = (List<LeaveApplicationModel>) TeacherDataStore.ShowLeaveApplication.get(this, type);
        populateInView();
    }

    private void populateInView() {
        TeacherLeaveAdapter leaveAdapter = new TeacherLeaveAdapter(this,leaveTypeModelList, leaveApplicationModelList, mBehavior, mBottomSheetDialog);
        recyclerView.setAdapter(leaveAdapter);
    }

}
