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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalenderDate;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.AssignmentPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.CalenderPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.AssignmentAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.BottomSheetFragment;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime.getTodayDate;

public class HomeWorkActivity extends AppCompatActivity implements AssignmentContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;

    Map<String, String> params;
    AssignmentPresenter presenter;

    //private List<AssignmentCache> assignmentCacheList;
    private AssignmentAdapter adapter;
    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        //casting
        casting();

        //get Student Id :: registrationNo

        //getToday CalenderDate
        String[] todaySplit = DateTime.getTodayDate().split("/");

        today = DateTime.getTodayDate();//String.valueOf(DateTime.convertToNepali(todaySplit));

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


    public void setData(List<AssignmentCache> assignmentCacheList) {
        setFieldInvisible();

        //todo work here
//        adapter = new AssignmentAdapter(this,assignmentCacheList, ItemAnimation.BOTTOM_UP);
//        recyclerView.setAdapter(adapter);

        //todo show in detail view
//        adapter.setOnItemClickListener(new AssignmentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, AssignmentCache cache, int position) {
//                BottomSheetFragment fragment = new BottomSheetFragment();
//                //some thing here
//                fragment.setAssignment(cache);
//                fragment.show(getSupportFragmentManager(), fragment.getTag());
//            }
//        });
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();

        try {
            if(response.getString("Status").equals("true")){
                if(response.getString("Response").equals("Success")) {
                    JSONArray dataArray = response.getJSONArray("Data");
                    if(!dataArray.toString().equals("[]")) {
                        if (dataArray.length() >= 0) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONArray array = dataArray.getJSONArray(i);
                                List<AssignmentCache> assignmentCacheList = new ArrayList<>();

                                for(int j=0; j<array.length(); j++) {
                                    JSONObject data = array.getJSONObject(j);
                                    AssignmentCache assignmentCache = null;

                                    if (!data.toString().equals("")) {
                                        String Class = data.getString("Class");
                                        String ClassConfigurationCode = data.getString("ClassConfigurationCode");
                                        String Homework = data.getString("Homework");
                                        String CreateDate = "";
                                        if(!data.getString("Date").equals("null")) {
                                            CreateDate = DateTime.splitDateOrTime(data.getString("Date"), "date");
                                        }
                                        String Deadline = "";
                                        if(!data.getString("Deadline").equals("null")) {
                                            Deadline = DateTime.splitDateOrTime(data.getString("Deadline"), "date");
                                        }
                                        String SubjectNameEng = data.getString("SubjectNameEng");
                                        String SubjectCode = data.getString("SubjectCode");
                                        String ImageUrl = data.getString("ImageUrl");
                                        String FontType = "";

                                         assignmentCache = new AssignmentCache(Class, ClassConfigurationCode, Homework, CreateDate, Deadline, SubjectNameEng, SubjectCode, ImageUrl, FontType);
                                         assignmentCacheList.add(assignmentCache);
                                    }
                                }

                                AssignmentAdapter adapter1 = new AssignmentAdapter(this,assignmentCacheList, ItemAnimation.BOTTOM_UP);

                                LinearLayout relativeLayout;
                                View view;
                                relativeLayout = findViewById(R.id.assignment_rel);
                                view = getLayoutInflater().inflate(R.layout.homework_recyclerview,null);
                                relativeLayout.addView(view);

                                TextView textView = view.findViewById(R.id.recyclerView_date);
                                if(assignmentCacheList.get(0).CreateDate.equals(today)){
                                    textView.setText("Today");
                                }else {
                                    textView.setText("Date: "+assignmentCacheList.get(0).CreateDate);
                                }
                                RecyclerView recyclerView;
                                recyclerView = view.findViewById(R.id.assignmentRecyclerView);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                                recyclerView.setAdapter(adapter1);

                                adapter1.setOnItemClickListener(new AssignmentAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, AssignmentCache cache, int position) {
                                        BottomSheetFragment fragment = new BottomSheetFragment();
                                        //some thing here
                                        fragment.setAssignment(cache);
                                        fragment.show(getSupportFragmentManager(), fragment.getTag());
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            setMessage(e.getMessage());
        }
    }


    private void casting() {
        progressBar = findViewById(R.id.assignment_progressbar);
        loadTextView = findViewById(R.id.assignment_loading);
    }
}
