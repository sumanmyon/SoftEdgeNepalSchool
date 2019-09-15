package www.softedgenepal.com.softedgenepalschool.View.Activities.Student;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.ReportCardSetting;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ReportCardCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ReportCardDetailCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.ReportCardPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.FragmentAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.ReportCard.Grade;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.ReportCard.Percentage;

public class ReportCardDetailActivity extends AppCompatActivity implements IContractor.View{
    private TextView loadTextView, titleTextView;
    private ProgressBar progressBar;
    private View backpress;

    private LanguageSetting languageSetting;
    private String lang;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ReportCardPresenter presenter;

    private ReportCardCache reportCardCache;
    private String studentId = MainActivity.user.Id;
    private String examId;

    public static List<ReportCardDetailCache> reportCardDetailCacheList;
    private List<String> typeList = new ArrayList<>();
    private String type;
    private int typeChoose = 2;   //todo it can be 0, 1 or 2

    private void setType(){
        typeList.add("percentage");
        typeList.add("grade");
        typeList.add("both");

        type = typeList.get(typeChoose);
        type = ReportCardSetting.getCardFormate(this);
    }

    protected void refreshLayout() {
        Intent refresh = new Intent(getApplicationContext(), ReportCardDetailActivity.class);
        finish();
        startActivity(refresh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageSetting = new LanguageSetting(this);
        languageSetting.loadLanguage();

        setContentView(R.layout.activity_report_card);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            reportCardCache = (ReportCardCache) bundle.getSerializable("cache");
        }

        setType();
        casting();

        if(reportCardCache!=null){
            examId = reportCardCache.ExamCode;
            //titleTextView.setText(reportCardCache.ExamNameEng);
        }

        presenter = new ReportCardPresenter(this);
        getJsonData();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void casting() {
        progressBar = findViewById(R.id.reportCard_progressbar);
        loadTextView = findViewById(R.id.reportCard_loading);
        backpress = findViewById(R.id.reportCard_bt_close);

        titleTextView = findViewById(R.id.reportCard_toolbar);
        tabLayout = findViewById(R.id.reportCardTab);
        viewPager = findViewById(R.id.viewpager);
    }

    @Override
    public Context getCalContext() {
        return this;
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLog(String topic, String body) {
        Log.d(topic, body);
    }

    @Override
    public void getJsonData() {
        presenter.getJsonData();
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("studentId",studentId);
        params.put("examId", examId);
        return params;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();

        String res = null;
        if(response != null) {
            reportCardDetailCacheList = new ArrayList<>();
            try {
                if (response.getString("Status").equals("true")) {
                    res  = response.getString("Response");
                    JSONObject dataObject = response.getJSONObject("Data");
                    if (!dataObject.equals("{}")) {                                 // || !dataObject.equals("null")
                        String Position = dataObject.getString("Position");
                        String Result = dataObject.getString("Result");

                        List<ReportCardDetailCache.Marks> marksList = new ArrayList<>();
                        JSONArray reportMarksArray = dataObject.getJSONArray("Marks");
                        for (int i = 0; i < reportMarksArray.length(); i++) {
                            if (!reportMarksArray.toString().equals("[]")) {
                                JSONObject routeDetailsObject = reportMarksArray.getJSONObject(i);
                                String SubjectCode = routeDetailsObject.getString("SubjectCode");
                                String Subject = routeDetailsObject.getString("Subject");

                                String FullMarks = routeDetailsObject.getString("FullMarks");
                                String PassMarks = routeDetailsObject.getString("PassMarks");
                                String ObtainedMarks = routeDetailsObject.getString("ObtainedMarks");

                                String PracticalFullMarks = routeDetailsObject.getString("PracticalFullMarks");
                                String PracticalPassMarks = routeDetailsObject.getString("PracticalPassMarks");
                                String PracticalObtainedMarks = routeDetailsObject.getString("PracticalObtainedMarks");

                                String IsAbsentPractical = routeDetailsObject.getString("IsAbsentPractical");
                                String IsAbsentTheory = routeDetailsObject.getString("IsAbsentTheory");

                                String Highest = routeDetailsObject.getString("Highest");
                                String Grade = routeDetailsObject.getString("Grade");
                                String GradePoint = routeDetailsObject.getString("GradePoint");
                                String ThGrade = routeDetailsObject.getString("ThGrade");
                                String PrGrade = routeDetailsObject.getString("PrGrade");

                                marksList.add(new ReportCardDetailCache.Marks(
                                        SubjectCode,Subject,
                                        FullMarks, PassMarks, ObtainedMarks,
                                        PracticalFullMarks, PracticalPassMarks, PracticalObtainedMarks,
                                        IsAbsentPractical, IsAbsentTheory,
                                        Highest, Grade, GradePoint, ThGrade, PrGrade));
                            }

                            reportCardDetailCacheList.add(new ReportCardDetailCache(Position, Result, marksList));
                        }
                    }
                }
                showInView();
            } catch (Exception e) {
                setMessage(e.getMessage());
            }

            //showInView();
        }else {
            loadTextView.setVisibility(View.VISIBLE);
            loadTextView.setText(getResources().getString(R.string.ReportCard_comeOnline));
        }
    }

    FragmentAdapter adapter;
    Fragment[] fragments = null;
    String[] title = null;
    int[] icon = new int[]{R.drawable.logo, R.drawable.logo};
    private void showInView() throws Exception{
        //todo set fragment according to percentage, grade and both
        Percentage percentage = new Percentage();
        percentage.setTitle(reportCardCache.ExamNameEng);

        Grade grade = new Grade();
        grade.setTitle(reportCardCache.ExamNameEng);

        if(type.equals("Percentage")){
            fragments = new Fragment[]{percentage};
            title = new String[]{getString(R.string.percentage)};
        }else if(type.equals("GPA")){
            fragments = new Fragment[]{grade};
            title = new String[]{getString(R.string.grade)};
        }else {
            fragments = new Fragment[]{percentage, grade};
            title = new String[]{getString(R.string.percentage),getString(R.string.grade)};
        }
        adapter = new FragmentAdapter(ReportCardDetailActivity.this, tabLayout, viewPager, getSupportFragmentManager(), fragments, title, icon);
        adapter.setTablayout(true);
    }

    private void setFieldInvisible() {
        loadTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

        @Override
    protected void onRestart() {
        super.onRestart();
        //refreshLayout();
            try {
                showInView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
