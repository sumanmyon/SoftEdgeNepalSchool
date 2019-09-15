package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateCompare;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DatePicker;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialogs;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class EditTeacherLeaveAppActivity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private List<LeaveTypeModel> leaveTypeModelList;

    private TextView fromDateTextView, toDateTextView, halfDateTextView;
    private RadioButton fromDateButton, toDateButton, halfDateButton;
    private Spinner leaveTypeSpinner, halfDayTypeSpinner;
    private Switch halfDaySwitch;
    private View halfdayView;
    private AppCompatEditText remarksEditText;
    private FloatingActionButton sendButton;

    private String uid = user.Id;
    private final String[] startDate = {""};
    private final String[] endDate = {""};
    private final String[] halfDate = {""};

    private String engstartDate;
    private String engendDate;
    private String engHalfDate;

    private LeaveApplicationModel leaveApplicationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher_leave_app);

        casting();
        toolBarTitle.setText(R.string.CreateLeaveApplication);

        Bundle bundle = getIntent().getExtras();
        leaveApplicationModel = (LeaveApplicationModel) bundle.getSerializable("leaveModel");

        offline();

        fromDateButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                pickDate();
            }

            @Override
            public void getValue(String date) {
                String toDayDate = getTodayDate();
                startDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(toDayDate, startDate[0]);
                displayInTextView(success, startDate[0], fromDateTextView);
                engstartDate = convertNepToEng(startDate[0]);
            }
        });
        toDateButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                if (!fromDateTextView.getText().toString().equals("")) pickDate();
                else displayMessage(getString(R.string.Application_select_startDate));
            }

            @Override
            public void getValue(String date) {
                endDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(startDate[0], endDate[0]);
                displayInTextView(success, endDate[0], toDateTextView);
                engendDate = convertNepToEng(endDate[0]);
            }
        });
        halfDateButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                if (!fromDateTextView.getText().toString().equals("") && !toDateTextView.getText().toString().equals(""))
                    pickDate();
                else displayMessage(getString(R.string.Application_select_startDate));
            }

            @Override
            public void getValue(String date) {
                halfDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(startDate[0], halfDate[0]);
                if (success.equals("success")) {
                    String successess = calculateLeaveDaysOrTodayPickedDate(halfDate[0], endDate[0]);
                    if (halfDate[0].equals(startDate[0]) || halfDate[0].equals(endDate[0])) {
                        displayInTextView(successess, halfDate[0], halfDateTextView);
                        engHalfDate = convertNepToEng(halfDate[0]);
                    } else {
                        displayMessage("Half date should be either Date From or Date To");
                    }
                } else {
                    displayInTextView(success, halfDate[0], halfDateTextView);
                }
            }
        });
        halfDaySwitch.setOnClickListener(this);
        sendButton.setOnClickListener(this);
    }

    private void offline(){
        loadLeaveType();
        loadHalfDayLeave();

        //loading data from model
        for (int i=0; i<leaveTypeModelList.size(); i++){
            if(leaveApplicationModel.LeaveType.equals(leaveTypeModelList.get(i).LeaveTypeCode)){
                leaveTypeSpinner.setSelection(i+1);
            }
        }

        setText(fromDateTextView, DateTime.DateConvertToNepali(leaveApplicationModel.LeaveFromDate, "date"));
        engstartDate = DateTime.DateConvertToEnglish(fromDateTextView.getText().toString(), "date");

        setText(toDateTextView, DateTime.DateConvertToNepali(leaveApplicationModel.LeaveToDate, "date"));
        engendDate = DateTime.DateConvertToEnglish(toDateTextView.getText().toString(), "date");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            remarksEditText.setText(Objects.toString(leaveApplicationModel.Remarks, ""));
        }

        if(leaveApplicationModel.HalfdayLeave.equals("true")){
            halfDaySwitch.setChecked(true);
            halfDaySwitch.setText(getString(R.string.yes));
            halfdayView.setVisibility(View.VISIBLE);
        } else {
            halfDaySwitch.setChecked(false);
            halfDaySwitch.setText(getString(R.string.no));
            halfdayView.setVisibility(View.GONE);
        }

        if(halfDaySwitch.isChecked()){
            setText(halfDateTextView, DateTime.DateConvertToNepali(leaveApplicationModel.HalfdayDate, "date"));
            engHalfDate = DateTime.DateConvertToEnglish(halfDateTextView.getText().toString(), "date");

            if(leaveApplicationModel.HalfdayType.equals("1")){
                halfDayTypeSpinner.setSelection(1);
            }else if(leaveApplicationModel.HalfdayType.equals("2")){
                halfDayTypeSpinner.setSelection(2);
            }
        }

    }

    private void loadLeaveType() {
        Type type = new TypeToken<List<LeaveTypeModel>>() {
        }.getType();
        leaveTypeModelList = (List<LeaveTypeModel>) TeacherDataStore.GetLeaveType.get(this, type);

        createSpinnerDropDown("- Select Leave Type", "LeaveType", leaveTypeSpinner);
    }

    private void loadHalfDayLeave() {
        createSpinnerDropDown("- Select Half Day", "HalfDayLeave", halfDayTypeSpinner);
    }

    private void createSpinnerDropDown(String addDefaultColumn, String selectType, Spinner spinner) {
        ArrayList<String> orderColumns = new ArrayList<>();
        orderColumns.add(addDefaultColumn);
        if (selectType.equals("LeaveType")) {
            for (LeaveTypeModel model : leaveTypeModelList) {
                orderColumns.add(model.LeaveTypeName);
            }
        } else if (selectType.equals("HalfDayLeave")) {
            orderColumns.add("1st Half Leave");
            orderColumns.add("2nd Half Leave");
        }

        ArrayAdapter<String> orderColumnsAdapter = createArrayAdapter(orderColumns);
        addToSpinner(spinner, orderColumnsAdapter);
    }

    private ArrayAdapter<String> createArrayAdapter(ArrayList<String> columns) {
        ArrayAdapter<String> columnsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, columns);
        columnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return columnsAdapter;
    }

    private void addToSpinner(Spinner spinner, ArrayAdapter<String> orderColumnsAdapter) {
        spinner.setAdapter(orderColumnsAdapter);
    }

    private void halfDaySwitch() {
        if (halfDaySwitch.isChecked()) {
            halfDaySwitch.setText(getString(R.string.yes));
            halfdayView.setVisibility(View.VISIBLE);
        } else {
            halfDaySwitch.setText(getString(R.string.no));
            halfdayView.setVisibility(View.GONE);
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

    private void sendButton() {
        clearErrorMessages();

        if (leaveTypeSpinner.getSelectedItemPosition() <= 0) {
            displayMessage("Please select leave type");
        } else if (TextUtils.isEmpty(fromDateTextView.getText())) {
            showErrorInTextView(fromDateTextView, "Please select date.");
        } else if (TextUtils.isEmpty(toDateTextView.getText())) {
            showErrorInTextView(toDateTextView, "Please select date.");
        } else if (TextUtils.isEmpty(remarksEditText.getText())) {
            remarksEditText.setError("Please add your remarks.");
        } else if (!halfDaySwitch.isChecked()) {
            createApplication();
        } else {
            if (TextUtils.isEmpty(halfDateTextView.getText())) {
                showErrorInTextView(halfDateTextView, "Please select date.");
            } else if (halfDayTypeSpinner.getSelectedItemPosition() <= 0) {
                displayMessage("Please select half day leave");
            } else {
                createApplication();
            }
        }
    }

    private void clearErrorMessages() {
        showErrorInTextView(fromDateTextView, null);
        showErrorInTextView(toDateTextView, null);
        remarksEditText.setError(null);
    }

    private String parameters(String parameters) {
        parameters = parameters +"?SystemCode=" + leaveApplicationModel.SystemCode;
        parameters = parameters + "&UserId=" + uid;
        parameters = parameters + "&LeaveTypeCode=" + leaveTypeModelList.get(leaveTypeSpinner.getSelectedItemPosition() - 1).LeaveTypeCode;
        parameters = parameters + "&From=" + engstartDate;
        parameters = parameters + "&To=" + engendDate;
        parameters = parameters + "&Remarks=" + URLEncoder.encode(remarksEditText.getText().toString());
        parameters = parameters + "&AppliedDate=" + DateTime.getTodayDate();
        parameters = parameters + "&IsHalfDayLeave=" + halfDaySwitch.isChecked();
        return parameters;
    }

    private void createApplication() {
        //for edit there will be SystemCode
        String parameters = "";
        if (!halfDaySwitch.isChecked()) {
            parameters = parameters(parameters);
            parameters = parameters + "&HalfDayDate=";
            parameters = parameters + "&HalfDayType=" + "0";
        } else {
            parameters = parameters(parameters);
            parameters = parameters + "&HalfDayDate=" + engHalfDate;
            parameters = parameters + "&HalfDayType=" + halfDayTypeSpinner.getSelectedItemPosition();
        }
        Log.d("LeaveSending", parameters);
        ApiCall.getInstance().connect(this, Constants.StaffLeaveApplication, Request.Method.POST, parameters, null, this, "Sending your application request.");
        pendingRequestsCount++;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == halfDaySwitch.getId()) halfDaySwitch();
        else if (id == sendButton.getId()) sendButton();
    }

    @Override
    protected void casting() {
        super.casting();
        leaveTypeSpinner = findViewById(R.id.leaveTypeSpinner);
        halfDayTypeSpinner = findViewById(R.id.halfDayTypeSpinner);
        halfDaySwitch = findViewById(R.id.halfDayLeaveSwitch);
        halfdayView = findViewById(R.id.halfdayLyl);

        fromDateButton = findViewById(R.id.fromDateButton);
        toDateButton = findViewById(R.id.toDateButton);
        halfDateButton = findViewById(R.id.halfDateButton);

        fromDateTextView = findViewById(R.id.fromDateTextView);
        toDateTextView = findViewById(R.id.toDateTextView);
        halfDateTextView = findViewById(R.id.halfDateTextView);

        remarksEditText = findViewById(R.id.remarksEditText);

        sendButton = findViewById(R.id.sendButton);
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;

        if (isSuccess){
            try{
                displayMessage(jsonObject.getString("Response"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        CustomAlertDialogs.NetworkError(getApplicationContext());
    }

    private void setText(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setText(Objects.toString(s, ""));
        }
    }
}
