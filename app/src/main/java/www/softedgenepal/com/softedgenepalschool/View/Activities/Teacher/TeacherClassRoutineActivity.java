package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.TeacherClassRoutineAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class TeacherClassRoutineActivity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private List<List<TeacherClassRoutineModel>> classRoutineModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_routine);

        casting();
        toolBarTitle.setText(R.string.ClassRoutine);

        loadData();

        //serviceloader();
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
            classRoutineModel = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<List<TeacherClassRoutineModel>>>() {
            }.getType());

            //TeacherDataStore.ClassRoutine.store(this, classRoutineModel);
            populateInView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void offline() {
        Type type = new TypeToken<List<List<TeacherClassRoutineModel>>>() {
        }.getType();
        classRoutineModel = (List<List<TeacherClassRoutineModel>>) TeacherDataStore.ClassRoutine.get(this, type);

        populateInView();
    }

    List<TeacherClassRoutineModel> models = new ArrayList<>();

    private void populateInView() {
        try {
            for (int i = 0; i < classRoutineModel.size(); i++) {
//                for (int j = 0; j < classRoutineModel.get(i).size(); j++) {
//                    models.add(classRoutineModel.get(i).get(j));
//                }
                models.addAll(classRoutineModel.get(i));
            }

            TeacherClassRoutineAdapter adapter = new TeacherClassRoutineAdapter(this, models);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void casting() {
        super.casting();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    private void  setRecyclerView(){
    }

}
