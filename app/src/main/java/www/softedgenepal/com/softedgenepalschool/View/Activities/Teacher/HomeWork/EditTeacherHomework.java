package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.HomeWork;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateCompare;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DatePicker;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.HomeWorkModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialogs;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class EditTeacherHomework extends CustomAppCompatActivity implements View.OnClickListener, ApiCall.ResultListener {
    private View backpress;
    private TextView toolBarTitle;
    private Spinner facultySpinner, classSpinner, sectionSpinner, subjectSpinner;

    private TextView createdTextView, deadlineTextView;
    private RadioButton createdDateButton, deadlineButton;

    private EditText messageEditText;
    private View sendButton;

    private List<FacultyClassDetailModel> facultyClassDetailModelList;
    private String uid = user.Id;
    private final String[] startDate = {""};
    private final String[] endDate = {""};

    private String engstartDate;
    private String engendDate;

    private HomeWorkModel homeWorkModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher_home_wrok);

        Bundle bundle = getIntent().getExtras();
        homeWorkModel = (HomeWorkModel) bundle.getSerializable("homeWork");

        casting();
        offline();

        backpress.setOnClickListener(this);
        sendButton.setOnClickListener(this);

        createdDateButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                pickDate();
            }

            @Override
            public void getValue(String date) {
                String toDayDate = getTodayDate();
                startDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(toDayDate, startDate[0]);
                displayInTextView(success, startDate[0], createdTextView);
                engstartDate = convertNepToEng(startDate[0]);
            }
        });

        deadlineButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                pickDate();
            }

            @Override
            public void getValue(String date) {
                endDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(startDate[0], endDate[0]);
                displayInTextView(success, endDate[0], deadlineTextView);
                engendDate = convertNepToEng(endDate[0]);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                onBackPressed();
                break;

            case R.id.createHlw_post_button:
                //checking fields are null or not
                // String s = provinceSpinner.getSelectedItem().toString();
                // String categoryID = categoryModelList.get(categorySpinner.getSelectedItemPosition()).id;

                int facultyPosition = facultySpinner.getSelectedItemPosition() - 1;
                int classPosition = classSpinner.getSelectedItemPosition() - 1;
                int sectionPosition = sectionSpinner.getSelectedItemPosition() - 1;
                int subjectPosition = subjectSpinner.getSelectedItemPosition() - 1;

                if (sectionPosition == -1 || subjectPosition == -1) {
                    displayMessage("Please Choose Section and Subject.");
                    Log.d("facultyClassDetail", "fac == " + facultyPosition + " class == " + classPosition + " sec == " + sectionPosition + " sub == " + subjectPosition);
                } else {
                    String sectionCode = facultyClassDetailModelList.get(facultyPosition).ClassSectionSubDetail.get(classPosition).SectionList.get(sectionPosition).SectionCode;
                    String subjectCode = facultyClassDetailModelList.get(facultyPosition).ClassSectionSubDetail.get(classPosition).SubjectList.get(subjectPosition).SubjectCode;
                    //Log.d("facultyClassDetail","fac == "+ facultyPosition + " class == "+ classPosition + " sec == "+ sectionCode + " sub == "+ subjectCode);

                    if (TextUtils.isEmpty(createdTextView.getText())) {
                        createdTextView.setError(getString(R.string.Application_Date_error));
                    } else if (TextUtils.isEmpty(deadlineTextView.getText())) {
                        deadlineTextView.setError(getString(R.string.Application_Date_error));
                    } else if (TextUtils.isEmpty(messageEditText.getText())) {
                        messageEditText.setError(getString(R.string.Application_Message_error));
                    } else {
                        postHomework(sectionCode, subjectCode);
                    }
                }
                break;

            default:
                break;
        }
    }

    private void postHomework(String sectionCode, String subjectCode) {
        try {
            String parameters = "?systemCode="+homeWorkModel.SystemCode+"&userId=" + uid + "&sectionCode=" + sectionCode + "&subjectCode=" + subjectCode + "&createdDate=" + engstartDate + "&deadline=" + engendDate + "&homework=" + URLEncoder.encode(messageEditText.getText().toString(), "UTF-8");
            Log.d("facultyClassDetail", Constants.CreateHomeWorkTeacher + parameters);

            //call api
            ApiCall.getInstance().connect(this, Constants.CreateHomeWorkTeacher, Request.Method.POST, parameters, null, this, "Please wait... uploading ...");
            pendingRequestsCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculateLeaveDaysOrTodayPickedDate(String start, String end) {
        DateCompare compare = new DateCompare();
        String result = compare.ComapareStartDateAndEndDate(start, end);
        return result;
    }

    private void displayInTextView(String success, String date, TextView textView) {
        if (success.equals("success")) {
            textView.setText(date);
        } else {
            textView.setText("");
            displayMessage(getString(R.string.Application_select_endDate));
        }
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;
        switch (url) {
            case Constants.CreateHomeWorkTeacher:
                try {
                    if (jsonObject.getBoolean("Status")) {
                        displayMessage(jsonObject.getString("Response"));
                        onBackPressed();
                    } else {
                        displayMessage(jsonObject.getString("Response"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    private void offline() {
        Type type = new TypeToken<List<FacultyClassDetailModel>>() {
        }.getType();
        facultyClassDetailModelList = (List<FacultyClassDetailModel>) TeacherDataStore.FacultyClassDetail.get(this, type);
        populateFaculty();
    }


    @Override
    public void onNetworkFailed(String url, String s) {
        switch (url) {
            case Constants.CreateHomeWorkTeacher:
                CustomAlertDialogs.NetworkError(getApplicationContext());
                break;
        }

    }

    @Override
    public void onRestart() {
        super.onRestart();
        offline();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    final int[] facultyID = {-1};
    boolean flag = false;
    int noOfOccur = 0;

    private void populateFaculty() {
        createSpinnerDropDown(facultyID[0], -1, "- Select Faculty", "Faculty", facultySpinner, flag, -1);
        setFacultySpinner(-1);
        setClassSpinner(-1, -1);


        //faculty
        for (int j = 0; j < facultyClassDetailModelList.size(); j++) {
            if (facultyClassDetailModelList.get(j).FacultyCode.equals(homeWorkModel.FacultyCode)) {
                facultySpinner.setSelection(j + 1);

                //class
                List<FacultyClassDetailModel.ClassSectionSubDetail> sectionSubDetail = facultyClassDetailModelList.get(j).ClassSectionSubDetail;
                for (int k = 0; k < sectionSubDetail.size(); k++) {
                    if (sectionSubDetail.get(k).ClassCode.equals(homeWorkModel.ClassCode)) {
                        flag = true;
                        int aclass = -1;
                        int subject = -1;
                        noOfOccur++;
                        setFacultySpinner(k + 1);


                        //section
                        List<FacultyClassDetailModel.SectionList> sectionLists = facultyClassDetailModelList.get(j).ClassSectionSubDetail.get(k).SectionList;
                        for (int l = 0; l < sectionLists.size(); l++) {
                            if (sectionLists.get(l).SectionCode.equals(homeWorkModel.SectionCode)) {
                                noOfOccur++;
                                aclass = l + 1;
                            }
                        }

                        //subject
                        List<FacultyClassDetailModel.SubjectList> subjectLists = facultyClassDetailModelList.get(j).ClassSectionSubDetail.get(k).SubjectList;
                        for (int l = 0; l < subjectLists.size(); l++) {
                            if (subjectLists.get(l).SubjectCode.equals(homeWorkModel.SubjectCode)) {
                                noOfOccur++;
                                subject = l + 1;
                            }
                        }

                        setClassSpinner(aclass, subject);
                    }
                }
            }
        }

        //text fields
        String createDate = homeWorkModel.CreatedDate;
        if(!createDate.equals("") || createDate != null || !createDate.equals("null")) {
            createdTextView.setText(DateTime.DateConvertToNepali(createDate, "date"));
            engstartDate = DateTime.DateConvertToEnglish(createdTextView.getText().toString(), "date");
        }

        String deadline = homeWorkModel.Deadline;
        if(deadline != null) {
            deadlineTextView.setText(DateTime.DateConvertToNepali(deadline, "date"));
            engendDate = DateTime.DateConvertToEnglish(deadlineTextView.getText().toString(), "date");
        }

        messageEditText.setText(Html.fromHtml(homeWorkModel.Homework));
    }

    private void setFacultySpinner(int p) {
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyID[0] = position - 1;
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Class", "Class", classSpinner, flag, p);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setClassSpinner(int aclass, int subject) {
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Section", "Section", sectionSpinner, flag, aclass);
                createSpinnerDropDown(facultyID[0], position - 1, "- Select Subject", "Subject", subjectSpinner, flag, subject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createSpinnerDropDown(int facultyID, int getId, String addDefaultColumn, String selectType, Spinner spinner, boolean b, int position) {
        ArrayList<String> orderColumns = new ArrayList<>();
        orderColumns.add(addDefaultColumn);
        if (selectType.equals("Faculty")) {
            for (FacultyClassDetailModel model : facultyClassDetailModelList) {
                orderColumns.add(model.FacultyName);
            }
        } else if (selectType.equals("Class")) {
            if (getId > -1) {
                for (FacultyClassDetailModel.ClassSectionSubDetail model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail) {
                    orderColumns.add(model.ClassName);
                }
            }
        } else if (selectType.equals("Section")) {
            if (getId > -1) {
                for (FacultyClassDetailModel.SectionList model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail.get(getId).SectionList) {
                    orderColumns.add(model.SectionName);
                }
            }
        } else if (selectType.equals("Subject")) {
            if (getId > -1) {
                for (FacultyClassDetailModel.SubjectList model : facultyClassDetailModelList.get(facultyID).ClassSectionSubDetail.get(getId).SubjectList) {
                    orderColumns.add(model.SubjectName);
                }
            }
        }

        ArrayAdapter<String> orderColumnsAdapter = createArrayAdapter(orderColumns);
        addToSpinner(spinner, orderColumnsAdapter, b, position);
    }

    private void addToSpinner(Spinner spinner, ArrayAdapter<String> orderColumnsAdapter, boolean b, int position) {
        spinner.setAdapter(orderColumnsAdapter);
        if (b) {
            noOfOccur--;
            spinner.setSelection(position);

            if (noOfOccur == 0) flag = false;
        }
    }

    private ArrayAdapter<String> createArrayAdapter(ArrayList<String> columns) {
        ArrayAdapter<String> columnsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, columns);
        columnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return columnsAdapter;
    }

    @Override
    public void casting() {
        backpress = findViewById(R.id.close);
        toolBarTitle = findViewById(R.id.toolBarTitleText);
        toolBarTitle.setText("Edit "+       getResources().getString(R.string.AssignHomeWork));

        facultySpinner = findViewById(R.id.facultySpinner);
        classSpinner = findViewById(R.id.classSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        subjectSpinner = findViewById(R.id.subjectSpinner);

        createdTextView = findViewById(R.id.createHlw_createdDate_textView);
        deadlineTextView = findViewById(R.id.createHlw_Deadline_textView);
        createdDateButton = findViewById(R.id.createHlw_createdDate_button);
        deadlineButton = findViewById(R.id.createHlw_Deadline_button);

        messageEditText = findViewById(R.id.createHlw_message_editText);
        sendButton = findViewById(R.id.createHlw_post_button);
    }
}
