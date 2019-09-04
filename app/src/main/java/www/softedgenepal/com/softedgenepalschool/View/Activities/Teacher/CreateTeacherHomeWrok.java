package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

public class CreateTeacherHomeWrok extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private List<FacultyClassDetailModel> facultyClassDetailModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher_home_wrok);

        loadDataSetup();
    }

    private void loadDataSetup() {
        ApiCall.getInstance().connect(this, URL.getFacultyClassDetail, Request.Method.POST, null, this, "");
        pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;

        switch (url) {
            case URL.getFacultyClassDetail:
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    facultyClassDetailModelList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FacultyClassDetailModel>>() {
                    }.getType());
                    displayMessage(facultyClassDetailModelList.get(0).ClassSectionSubDetail.get(0).SubjectList.get(0).SubjectName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }
}
