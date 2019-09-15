package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

public class ShowTeacherLeaveAppDetailActivity extends CustomAppCompatActivity {
    private TextView leaveTypeTextView, createDateTextView, fromToTextView, halfDayLeaveTextView, remarksTextView, isAppraovedTextView;
    private List<LeaveTypeModel> leaveTypeModelList;
    private LeaveApplicationModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_leave_app_detail);

        loadLeaveType();
        Bundle bundle = getIntent().getExtras();
        model = (LeaveApplicationModel) bundle.getSerializable("leaveModel");

        casting();

        for (int i = 0; i < leaveTypeModelList.size(); i++) {
            if (leaveTypeModelList.get(i).LeaveTypeCode.equals(model.LeaveType)) {
                setText(leaveTypeTextView, leaveTypeModelList.get(i).LeaveTypeName);
            }
        }

        setText(createDateTextView, getString(R.string.Created_Date)+": "+DateTime.DateConvertToNepali(model.LeaveAppliedDate, "date"));

        setText(fromToTextView, getString(R.string.From)+": "+DateTime.DateConvertToNepali(model.LeaveFromDate, "date")+
                "\n"+getString(R.string.To)+": "+DateTime.DateConvertToNepali(model.LeaveToDate, "date"));

        remarksTextView.setText(model.Remarks);

        String half = "";
        if(model.HalfdayType.equals("0")){      //1 = first half, 2 = second half

        }else if(model.HalfdayType.equals("1")){
            half = "First";
        }else if(model.HalfdayType.equals("2")){
            half = "Second";
        }

        if(model.HalfdayLeave.equals("true")){
           setText( halfDayLeaveTextView,"Half Day Leave: "+ half + "\n" + DateTime.DateConvertToNepali(model.HalfdayDate, "date"));
        }else {
            halfDayLeaveTextView.setVisibility(View.GONE);
        }

        String isApproved = model.IsApproved;
        if(isApproved.equals("0")){
            isAppraovedTextView.setVisibility(View.GONE);
        }else if(isApproved.equals("1")){
            setText(isAppraovedTextView, "Approved");
            isAppraovedTextView.setTextColor(getResources().getColor(R.color.green_500));
        }else if(isApproved.equals("2")){
            setText(isAppraovedTextView, "Canceled");
            isAppraovedTextView.setTextColor(getResources().getColor(R.color.red_500));
        }
    }

    private void setText(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setText(Objects.toString(s, ""));
        }
    }

    private void loadLeaveType() {
        Type type = new TypeToken<List<LeaveTypeModel>>() {
        }.getType();
        leaveTypeModelList = (List<LeaveTypeModel>) TeacherDataStore.GetLeaveType.get(this, type);
    }

    @Override
    protected void casting() {
        leaveTypeTextView = findViewById(R.id.leaveTypeTextView);
        createDateTextView = findViewById(R.id.createDateTextView);
        fromToTextView = findViewById(R.id.fromToTextView);
        halfDayLeaveTextView = findViewById(R.id.halfDayLeaveTextView);
        remarksTextView = findViewById(R.id.remarksTextView);
        isAppraovedTextView = findViewById(R.id.isAppraovedTextView);
    }
}
