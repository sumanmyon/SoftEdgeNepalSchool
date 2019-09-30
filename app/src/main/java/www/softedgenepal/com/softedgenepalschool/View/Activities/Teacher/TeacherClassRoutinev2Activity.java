package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel2;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ReportCardDetailActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.FragmentAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.TeacherClassRoutineFragment;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class TeacherClassRoutinev2Activity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private View close;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    FragmentAdapter adapter;
    Fragment[] fragments = null;
    String[] title = null;
    int[] icon = new int[]{R.drawable.logo, R.drawable.logo};

    private List<TeacherClassRoutineModel2> classRoutineModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_routinev2);

        casting();
//        toolBarTitle.setText(getString(R.string.ClassRoutine));
        loadData();

        close.setOnClickListener(this);
    }

    private void loadData() {
        String parameter = "?UserId=" + user.Id;
        ApiCall.getInstance().connect(this, Constants.ClassRoutineTeacher, Request.Method.POST, parameter, null, this, "");
        pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;

        if (isSuccess) {
            online(jsonObject);
        } else {
            offline("");
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        offline(message);
    }

    private void online(JSONObject jsonObject){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            classRoutineModel = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<TeacherClassRoutineModel2>>() {
            }.getType());

            TeacherDataStore.ClassRoutine.store(this, classRoutineModel);

            populateInView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void offline(String message){
        classRoutineModel = (List<TeacherClassRoutineModel2>) TeacherDataStore.ClassRoutine.get(this, new TypeToken<List<TeacherClassRoutineModel2>>(){}.getType());

        populateInView();
    }

    private void populateInView() {
        int size = classRoutineModel.size();
        title = new String[size];
        fragments = new Fragment[size];

        //create fragment and set frag names
        for(int i=0; i<size; i++){
            title[i] = classRoutineModel.get(i).ShiftName;
            TeacherClassRoutineFragment routineFragment = new TeacherClassRoutineFragment();
            routineFragment.setTitle(title[i], classRoutineModel.get(i));
            fragments[i] = routineFragment;
        }

        //send data to frags
        adapter = new FragmentAdapter(this, tabLayout, viewPager, getSupportFragmentManager(), fragments, title, icon);
        adapter.setTablayout(true);
        //adapter.setCurrentItem();
        //adapter.disableSwipe();
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == close.getId()){
           onBackPressed();
       }
    }

    @Override
    protected void casting() {
        tabLayout = findViewById(R.id.reportCardTab);
        viewPager = findViewById(R.id.viewpager);
        close = findViewById(R.id.close);
    }
}
