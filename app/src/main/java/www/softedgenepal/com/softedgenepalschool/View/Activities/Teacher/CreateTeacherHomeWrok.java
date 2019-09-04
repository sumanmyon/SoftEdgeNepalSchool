package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Teacher.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

public class CreateTeacherHomeWrok extends CustomAppCompatActivity implements View.OnClickListener, ApiCall.ResultListener {
    private View backpress;
    private TextView toolBarTitle;
    private Spinner facultySpinner, classSpinner, sectionSpinner, subjectSpinner;

    private List<FacultyClassDetailModel> facultyClassDetailModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher_home_wrok);

        casting();
        loadDataSetup();

        backpress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                onBackPressed();
                break;

            default:
                break;
        }
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
                    if (isSuccess) {
                        online(jsonObject);
                    } else {
                        offline();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    private void online(JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("Data");
        facultyClassDetailModelList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FacultyClassDetailModel>>() {
        }.getType());

        TeacherDataStore.FacultyClassDetail.store(this, facultyClassDetailModelList);
        populateFaculty();
    }

    @Override
    public void onFailed() {
        offline();
    }

    private void offline() {
        Type type = new TypeToken<List<FacultyClassDetailModel>>() {
        }.getType();
        facultyClassDetailModelList = (List<FacultyClassDetailModel>) TeacherDataStore.FacultyClassDetail.get(this, type);

        populateFaculty();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        offline();
    }

    private void populateFaculty() {
        final int[] facultyID = {-1};
        createSpinnerDropDown(facultyID[0],-1, "- Select Faculty", "Faculty", facultySpinner);

        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyID[0] = position-1;
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Class", "Class", classSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Section", "Section", sectionSpinner);
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Subject", "Subject", subjectSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createSpinnerDropDown(int facultyID, int getId, String addDefaultColumn, String selectType, Spinner spinner) {
        ArrayList<String> orderColumns = new ArrayList<>();
        orderColumns.add(addDefaultColumn);
        if (selectType.equals("Faculty")) {
            for (FacultyClassDetailModel model : facultyClassDetailModelList) {
                orderColumns.add(model.FacultyName);
            }
        } else if (selectType.equals("Class")) {
            if(getId >- 1) {
                for (FacultyClassDetailModel.ClassSectionSubDetail model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail) {
                    orderColumns.add(model.ClassName);
                }
            }
        } else if (selectType.equals("Section")) {
            if(getId >- 1) {
                for (FacultyClassDetailModel.SectionList model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail.get(getId).SectionList) {
                    orderColumns.add(model.SectionName);
                }
            }
        } else if (selectType.equals("Subject")) {
            if(getId >- 1) {
                for (FacultyClassDetailModel.SubjectList model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail.get(getId).SubjectList) {
                    orderColumns.add(model.SubjectName);
                }
            }
        }

        ArrayAdapter<String> orderColumnsAdapter = createArrayAdapter(orderColumns);
        addToSpinner(spinner, orderColumnsAdapter);
    }

    private void addToSpinner(Spinner spinner, ArrayAdapter<String> orderColumnsAdapter) {
        spinner.setAdapter(orderColumnsAdapter);
    }

    private ArrayAdapter<String> createArrayAdapter(ArrayList<String> columns) {
        ArrayAdapter<String> columnsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, columns);
        columnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return columnsAdapter;
    }

    private void casting() {
        backpress = findViewById(R.id.close);
        toolBarTitle = findViewById(R.id.toolBarTitleText);
        toolBarTitle.setText(getResources().getString(R.string.AssignHomeWork));
        facultySpinner = findViewById(R.id.facultySpinner);
        classSpinner = findViewById(R.id.classSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        subjectSpinner = findViewById(R.id.subjectSpinner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
