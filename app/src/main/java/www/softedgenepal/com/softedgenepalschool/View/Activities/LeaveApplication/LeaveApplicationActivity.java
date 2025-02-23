package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DatePicker;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateCompare;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.LeaveApplicationPresenter;
import www.softedgenepal.com.softedgenepalschool.R;

public class LeaveApplicationActivity extends AppCompatActivity implements LeaveApplicationContractor.View {
    private Activity activity;

    //private View toolbar;
    private EditText subjectEditText, messageEditText;
    private TextView fromTextView, toTextView;
    private View fromButton, toButton;
    private View sendButton;
    private ProgressBar progressBar;
    private View closeButton;

    private final String[] startDate = {""};
    private final String[] endDate = {""};

    private String engstartDate;
    private String engendDate;

    private final String uid = "1";
    LanguageSettingv2 languageSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();
        setContentView(R.layout.activity_leave_application2);
        this.activity = this;
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //casting
        casting();
        setProgressBarInVisibility();

        //sendLeave();

        //toolbar
        setToolBar();

        //pick start and end date
        fromButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                pickDate();
            }
            @Override
            public void getValue(String date) {
                //String toDayDate = getTodayDate();
                startDate[0] = date;
                //String success = calculateLeaveDaysOrTodayPickedDate(toDayDate,startDate[0]);
                displayInTextView("success", startDate[0], fromTextView);
                engstartDate = convertNepToEng(startDate[0]);
                //setMessage(engstartDate);
            }
        });

        toButton.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                if(startDate[0].equals("")){
                    setMessage(getString(R.string.Application_select_startDate));
                }else {
                    pickDate();
                }
            }

            @Override
            public void getValue(String date) {
                endDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(startDate[0],endDate[0]);
                displayInTextView(success, endDate[0], toTextView);
                engendDate = convertNepToEng(endDate[0]);
                //setMessage(engendDate);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sendButton();
    }

    public String calculateLeaveDaysOrTodayPickedDate(String start, String end) {
        DateCompare compare = new DateCompare();
        String result = compare.ComapareStartDateAndEndDate(start,end);
        return result;
    }

    private void displayInTextView(String success, String date, TextView textView) {
        if(success.equals("success")){
            textView.setText(date);
        }else {
            textView.setText("");
            setMessage(getString(R.string.Application_select_endDate));
        }
    }

    private void sendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking fields are null or not
                if(TextUtils.isEmpty(subjectEditText.getText())){
                    subjectEditText.setError(getString(R.string.Application_Subject_error));
                }else if(TextUtils.isEmpty(fromTextView.getText())){
                    fromTextView.setError(getString(R.string.Application_Date_error));
                }else if(TextUtils.isEmpty(toTextView.getText())){
                    toTextView.setError(getString(R.string.Application_Date_error));
                }else if(TextUtils.isEmpty(messageEditText.getText())){
                    messageEditText.setError(getString(R.string.Application_Message_error));
                }else {
                    sendLeaveMessage();
                }
            }
        });
    }

    private void sendLeaveMessage() {
        setProgressBarVisibility();
        LeaveApplicationPresenter presenter = new LeaveApplicationPresenter(this);

        List<String> data = new ArrayList<>();
        data.add(uid);
        data.add(subjectEditText.getText().toString());
        data.add(messageEditText.getText().toString());
        data.add(engstartDate);
        data.add(engendDate);

        presenter.createLeaveApplication(data);
    }

    private void setToolBar() {
        //toolbar.setTitle("Create your Leave Application");
    }

    private void casting() {
       // toolbar = findViewById(R.id.leave_toolbar);
        subjectEditText = findViewById(R.id.leave_application_edit_text_leave_subject);
        messageEditText = findViewById(R.id.leave_application_edit_text_message);
        fromTextView = findViewById(R.id.leave_application_text_view_leave_from);
        toTextView = findViewById(R.id.leave_application_text_view_leave_to);
        sendButton = findViewById(R.id.leave_application_send_button);
        progressBar = findViewById(R.id.leave_application_progress_bar);
        closeButton = findViewById(R.id.leave_create_bt_close);
        //progress bar loading color
//        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,
//                android.R.color.holo_red_dark),
//                android.graphics.PorterDuff.Mode.SRC_IN );

        //progress bar loading color
        fromButton = findViewById(R.id.leave_application_from_button);
        toButton = findViewById(R.id.leave_application_to_button);

    }

    public void setProgressBarVisibility(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressBarInVisibility(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
