package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.AssignmentPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.CalenderPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.AssignmentAdapter;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime.getTodayDate;

public class AssignmentActivity extends AppCompatActivity implements AssignmentContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;

    Map<String, String> params;
    AssignmentPresenter presenter;

    private AssignmentAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        //casting
        casting();

        //get Student Id :: registrationNo

        //getToday CalenderDate
        String today = DateTime.getTodayDate();

        //params to fetch url
        params = new HashMap<>();
        params.put("From","6/2/2019");
        params.put("To",today);
        params.put("studentId","1024");

        FetchData(params);
    }

    //todo fetch data here
    public void FetchData(Map<String, String> params){
        presenter = new AssignmentPresenter(this);
        presenter.mapToModel();
        presenter.fetchData(params);
    }


    @Override
    public void setMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getCalContext() {
        return getApplicationContext();
    }

    private void setFieldVisible(){
        progressBar.setVisibility(View.VISIBLE);
        loadTextView.setVisibility(View.VISIBLE);
    }

    private void setFieldInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
        loadTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setData(List<AssignmentCache> assignmentCacheList) {
        setFieldInvisible();
        //setMessage(assignmentCacheList.get(0).Homework);

        //todo work here
        adapter = new AssignmentAdapter(this,assignmentCacheList, ItemAnimation.BOTTOM_UP);
        recyclerView.setAdapter(adapter);
    }


    private void casting() {
        progressBar = findViewById(R.id.assignment_progressbar);
        loadTextView = findViewById(R.id.assignment_loading);

        recyclerView = findViewById(R.id.assignmentRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
