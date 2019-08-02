package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

public class ShowAllLeaveApplicationDetailViewActivity extends AppCompatActivity {
    private LeaveApplicationDataCache cache;

    private TextView subjectTextView, messageTextView, createDateTextView, fromToTextView, cancelTextView;
    private CardView cardView;
    private View backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_leave_application_detail_view);

        //casting
        casting();

        cache = (LeaveApplicationDataCache) getIntent().getSerializableExtra("leaveApplication");

        showInView(cache);

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showInView(LeaveApplicationDataCache cache) {
        if(cache != null) {
            setMessage("There is leave application");

            subjectTextView.setText(cache.Subject);
            messageTextView.setText(cache.Message);

            String date = cache.CreateDate;
            String From = cache.From;
            String To = cache.To;

            if(!date.equals("null")) {
                createDateTextView.setText("Created Date: "+DateTime.DateConvertToNepali(date, "date"));
            }

            if(!From.equals("null") && !To.equals("null")) {
                fromToTextView.setText("From: "+DateTime.DateConvertToNepali(From, "date")+
                        "\nTo: "+DateTime.DateConvertToNepali(To, "date"));
            }

            //todo show cancel
            if(cache.IsActive.equals("false")) {
                ViewGroup.LayoutParams params = cancelTextView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                cancelTextView.setLayoutParams(params);
                //isActiveText.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                //cardView.setCardBackgroundColor(getResources().getColor(R.color.backgroundLightGray));
                //fromToText.setText("From: "+cache.From+"\nTo: "+cache.To);
            }
        }
    }

    private void casting() {
        backpress = findViewById(R.id.leaveApp_bt_close);

        subjectTextView = findViewById(R.id.showall_detail_leave_subject);
        messageTextView = findViewById(R.id.showall_detail_leave_message);
        createDateTextView = findViewById(R.id.showall_detail_leave_createDate);
        fromToTextView = findViewById(R.id.showall_detail_leave_from_to);
        cancelTextView = findViewById(R.id.showall_detail_leave_isActive);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showInView(cache);
    }

    public void setMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
