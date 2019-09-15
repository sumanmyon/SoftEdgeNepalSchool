package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.HomeWork;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.HomeWorkModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.TeacherHomeworkAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class ShowTeacherHomeWork extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private RecyclerView recyclerView;
    private String uid = user.Id;
    private FloatingActionButton fab;

    private List<HomeWorkModel> homeWorkModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_home_work);

        casting();
        toolBarTitle.setText(R.string.ShowHomework);

        fab.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadHomeworkData();
    }

    private void loadHomeworkData() {
        String today = DateTime.getTodayDate();//String.valueOf(DateTime.convertToNepali(todaySplit));

        //parameters
        String parameters = "?userId=" + uid + "&dateFrom=6/2/2019" + "&dateTo=" + today;
        //call api
        ApiCall.getInstance().connect(this, Constants.ShowHomeWorkTeacher, Request.Method.POST, parameters, null, this, "");
    }

    @Override
    protected void casting() {
        super.casting();
        fab = findViewById(R.id.floating_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == fab.getId()) {
            redirect(CreateTeacherHomeWrok.class);
        }
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;
        try {
            if (isSuccess) {
                switch (url) {
                    case Constants.ShowHomeWorkTeacher:
                        if (isSuccess) {
                            online(jsonObject);
                        } else {
                            offline();
                        }
                        break;

                    default:
                        break;
                }
            } else {
                showErrorPopUp("Faileded", volleyError.getStackTrace().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopUp("Failed", e.getMessage());
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String s) {
        offline();
    }

    private void online(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            homeWorkModelList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HomeWorkModel>>() {
            }.getType());

            TeacherDataStore.HomeWorkDetail.store(this, homeWorkModelList);
            populateInView();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void offline() {
        homeWorkModelList = (List<HomeWorkModel>) TeacherDataStore.HomeWorkDetail.get(this, HomeWorkModel.class);
        populateInView();
    }

    private void populateInView() {
        TeacherHomeworkAdapter adapter = new TeacherHomeworkAdapter(getApplicationContext(), homeWorkModelList);
        recyclerView.setAdapter(adapter);
    }
}
