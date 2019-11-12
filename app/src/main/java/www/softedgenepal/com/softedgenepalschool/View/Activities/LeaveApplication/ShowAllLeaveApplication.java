package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.LeaveApplicationPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SiblingActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.AdapterListAnimation;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class ShowAllLeaveApplication extends AppCompatActivity implements LeaveApplicationContractor.View,
        LeaveApplicationContractor.View.Get {

    protected ProgressBar progressBar;
    protected FloatingActionButton floatingActionButton;
    //protected Toolbar toolbar;
    protected TextView messageHandleTextView;
    private View backpress;

    private List<LeaveApplicationDataCache> leaveApplicationDataCacheList;
    private final String from = "12/12/2018";
    private String StudentId;
    private String Role;

    //for animation
    private RecyclerView recyclerView;
    private int animation_type = ItemAnimation.BOTTOM_UP;
    private AdapterListAnimation mAdapter;

    //for bottom sheet
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    LanguageSettingv2 languageSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();
        setContentView(R.layout.activity_show_all_leave_application);

        //casting
        casting();

        Bundle bundle = getIntent().getExtras();
        String registrationNo = null;
        if(bundle!=null) {
            registrationNo = bundle.getString("registrationNo");
        }

        StudentDataCache studentDataList = StudentHomePage.studentProfileModellist.StudentDetail;
        if (studentDataList != null) {
            if(registrationNo.equals(MainActivity.user.Id)){
                StudentId  = MainActivity.user.Id;
            }else {
                StudentProfileModel sibModel = SiblingActivity.siblingProfileModel;
                if(registrationNo.equals(sibModel.StudentDetail.RegistrationNo)){
                    StudentId = sibModel.StudentDetail.RegistrationNo;
                }
            }
            Role = MainActivity.user.Role;
        }

        //get all leave application of user
        getLeaveApplication();

        //fab
        floatingActionButton();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void getLeaveApplication() {
        LeaveApplicationPresenter presenter = new LeaveApplicationPresenter(this);

        Map<String, String> params = new HashMap<>();
        params.put("UserId", StudentId);
        params.put("Role", Role);
        params.put("From", from);

        presenter.getAllLeaveApplication(params, StudentId);
    }

    @Override
    public void setAllLeaveApplication(List<LeaveApplicationDataCache> leaveApplicationDataCacheList) {
        setProgressBarInVisibility();
        this.leaveApplicationDataCacheList = leaveApplicationDataCacheList;

        mAdapter = new AdapterListAnimation(this, leaveApplicationDataCacheList, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterListAnimation.OnItemClickListener() {
            @Override
            public void onItemClick(View view, LeaveApplicationDataCache obj, int position) {
                Intent intent = new Intent(getApplicationContext(), ShowAllLeaveApplicationDetailViewActivity.class);
                intent.putExtra("leaveApplication", obj);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemLongClickListener(new AdapterListAnimation.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, LeaveApplicationDataCache obj, int positio) {
                if(obj.IsActive.equals("true")) {
                    showBottomSheetDialog(obj);
                }
            }
        });
    }

    private void showBottomSheetDialog(final LeaveApplicationDataCache obj) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        final View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        ((TextView) view.findViewById(R.id.alertDialogTitle)).setText(getResources().getString(R.string.LeaveApplication_Dialoge_Title));
        Button deleteButton = (view.findViewById(R.id.alertDialogExitButton));
        ViewGroup.LayoutParams params = deleteButton.getLayoutParams();
        params.width = 0;
        params.height = 0;

        deleteButton.setLayoutParams(params);

        Button cancelButton = (view.findViewById(R.id.alertDialogCancelButton));
        cancelButton.setText(getResources().getString(R.string.yes));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("Role", Role);
                params.put("SystemCode",obj.SystemCode);
                CancelLeaveApplication cancelLeaveApplication = new CancelLeaveApplication(ShowAllLeaveApplication.this, this);
                cancelLeaveApplication.cancelRequest(params);
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }

    @Override
    public void setMessageInTextView(String messageInTextView) {
        messageHandleTextView.setText(messageInTextView);
    }

    public void setTextViewVisibilityOff(){
        messageHandleTextView.setVisibility(View.INVISIBLE);
    }

    public void setProgressBarVisibility(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressBarInVisibility(){
        progressBar.setVisibility(View.GONE);
        messageHandleTextView.setVisibility(View.GONE);
    }

    private void redirect(Class<?> activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("registrationNo", StudentId);
        startActivity(intent);
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void floatingActionButton() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(LeaveApplicationActivity.class);
            }
        });
    }

    private void casting() {
        //toolbar = findViewById(R.id.showall_leave_toolbar);
        backpress = findViewById(R.id.leaveApp_bt_close);

        progressBar = findViewById(R.id.showall_leave_application_progress_bar);
        floatingActionButton = findViewById(R.id.showall_leave_floating_button);
        messageHandleTextView = findViewById(R.id.show_leave_app_message_textview);

        //for recycler view
        recyclerView = (RecyclerView) findViewById(R.id.showall_leave_recycleView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //progress bar loading color
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,
                android.R.color.holo_red_dark),
                android.graphics.PorterDuff.Mode.SRC_IN );


        //for bottom sheet
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLeaveApplication();
    }

    public Context getActivity() {
        return getApplicationContext();
    }

    public void refreshRecyclerView(){
        getLeaveApplication();
    }
}
