package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DatePicker;
import www.softedgenepal.com.softedgenepalschool.R;

public class LeaveApplicationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText subjectEditText, messageEditText;
    private TextView fromTextView, toTextView;
    private Button sendButton;

    private final String[] startDate = {""};
    private final String[] endDate = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        //casting
        casting();

        //toolbar
        setToolBar();

        //pick start and end date
        fromTextView.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                pickDate();
            }
            @Override
            public void getValue(String date) {
                String toDayDate = getTodayDate();
                startDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(toDayDate,startDate[0]);
                displayInTextView(success, startDate[0], fromTextView);
            }
        });

        toTextView.setOnClickListener(new DatePicker(getSupportFragmentManager()) {
            @Override
            public void show(View v) {
                if(startDate[0].equals("")){
                    showMessage("First select start date");
                }else {
                    pickDate();
                }
            }

            @Override
            public void getValue(String date) {
                endDate[0] = date;
                String success = calculateLeaveDaysOrTodayPickedDate(startDate[0],endDate[0]);
                displayInTextView(success, endDate[0], toTextView);
            }
        });

        sendButton();
    }

    public String calculateLeaveDaysOrTodayPickedDate(String start, String end) {
        String from = start;
        String to = end;

        //getting day, month and years from
        int fDay, fMonth, fYear;
        int tDay, tMonth, tYear;

        String[] f = from.split("/");
        fDay = Integer.parseInt(f[0]);
        fMonth = Integer.parseInt(f[1]);
        fYear = Integer.parseInt(f[2]);

        String[] t = to.split("/");
        tDay = Integer.parseInt(t[0]);
        tMonth = Integer.parseInt(t[1]);
        tYear = Integer.parseInt(t[2]);

        //todo calculate date for from and to dates.....
        //todo make sure user wont select past date in from
        if(from.equals(to)){
            return "success";
        }else{
            if(tYear-fYear>=0){
                if(tMonth-fMonth>=0){
                    int differenceInYear = (tMonth - fMonth) * 30;
                    if(tDay+differenceInYear-fDay>=0){
                        return "success";
                    }
                }
            }
        }
        return "failed";
    }

    //todo Calculate Total Leave Days
    private void calculateTotalLeaveDays(){

    }

    private void displayInTextView(String success, String date, TextView textView) {
        if(success.equals("success")){
            textView.setText(date);
        }else {
            showMessage("You should pick today date or future day for leave.");
        }
    }

    private void sendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking fields are null or not
                if(TextUtils.isEmpty(subjectEditText.getText())){
                    subjectEditText.setError("What is your subject for leave.");
                } else if(TextUtils.isEmpty(messageEditText.getText())){
                    messageEditText.setError("Please type message for leave.");
                }else if(TextUtils.isEmpty(fromTextView.getText())){
                    fromTextView.setError("Please select date for leave");
                }else if(TextUtils.isEmpty(toTextView.getText())){
                    toTextView.setError("Please select date for leave");
                }else {
                    sendLeaveMessage();
                }
            }
        });
    }

    private void sendLeaveMessage() {
        showMessage("Successfully send.");
    }

    private void setToolBar() {
        toolbar.setTitle("Leave Application");
    }

    private void casting() {
        toolbar = findViewById(R.id.leave_toolbar);
        subjectEditText = findViewById(R.id.leave_application_edit_text_leave_subject);
        messageEditText = findViewById(R.id.leave_application_edit_text_message);
        fromTextView = findViewById(R.id.leave_application_text_view_leave_from);
        toTextView = findViewById(R.id.leave_application_text_view_leave_to);
        sendButton = findViewById(R.id.leave_application_send_button);
    }

    private void showMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
