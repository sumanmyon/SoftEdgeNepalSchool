package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.LeaveApplicationPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.CustomAdapters.RecyclerAdapter;

public class ShowAllLeaveApplication extends AppCompatActivity implements LeaveApplicationContractor.View,
        LeaveApplicationContractor.View.Get {

    protected ProgressBar progressBar;
    protected FloatingActionButton floatingActionButton;
    protected Toolbar toolbar;
    protected TextView messageHandleTextView;

    private final String uid = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_leave_application);

        //casting
        casting();

        //toolbar
        toolbar();

        //get all leave application of user
        getLeaveApplication();

        //fab
        floatingActionButton();
    }


    private void getLeaveApplication() {
        LeaveApplicationPresenter presenter = new LeaveApplicationPresenter(this);

        Map<String, String> params = new HashMap<>();
        params.put("UserId", uid);
        params.put("datebefore","12/12/2055");

        presenter.getAllLeaveApplication(params);
    }

    private void floatingActionButton() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(LeaveApplicationActivity.class);
            }
        });
    }

    public void setProgressBarVisibility(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressBarInVisibility(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void redirect(Class<?> activityClass){
        startActivity(new Intent(this, activityClass));
    }


    private void toolbar() {
        toolbar.setTitle("Your Leave Applications.");
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void casting() {
        toolbar = findViewById(R.id.showall_leave_toolbar);
        progressBar = findViewById(R.id.showall_leave_application_progress_bar);
        floatingActionButton = findViewById(R.id.showall_leave_floating_button);
        messageHandleTextView = findViewById(R.id.show_leave_app_message_textview);
    }

    public Context getActivity() {
        return getApplicationContext();
    }

    @Override
    public void setAllLeaveApplication(final List<LeaveApplicationDataCache> leaveApplicationDataCacheList) {
        setTextViewVisibilityOff();

        final int size = leaveApplicationDataCacheList.size();

        final RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(),size) {
            private TextView subjectText, messageText, createDateText;//, fromToText;
            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.show_leave_application_recyclerview, null);
                return new ViewHolder(view);
            }
            @Override
            public void inflateUIFields(View itemView) {
                subjectText = itemView.findViewById(R.id.show_leave_app_recycle_subject);
                messageText = itemView.findViewById(R.id.show_leave_app_recycle_message);
                createDateText = itemView.findViewById(R.id.show_leave_app_recycle_createDate);
                //fromToText = itemView.findViewById(R.id.show_leave_app_recycle_from_to);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                //todo stuff here
                LeaveApplicationDataCache cache = leaveApplicationDataCacheList.get(position);
                subjectText.setText(cache.Subject);
                messageText.setText(cache.Message);
                createDateText.setText(cache.CreateDate);
                //fromToText.setText("From: "+cache.From+"\nTo: "+cache.To);
            }
        };

        RecyclerView recyclerView = findViewById(R.id.showall_leave_recycleView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setMessageInTextView(String messageInTextView) {
        messageHandleTextView.setText(messageInTextView);
    }

    public void setTextViewVisibilityOff(){
        messageHandleTextView.setVisibility(View.INVISIBLE);
    }
}
