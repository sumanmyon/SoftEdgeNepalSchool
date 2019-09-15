package www.softedgenepal.com.softedgenepalschool.View.Activities.Student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ReportCardCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.ReportCardPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

public class ReportCardActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;
    private View backpress;
    private RecyclerView recyclerView;

    private ReportCardPresenter presenter;
    private String classCode = "7";

    private List<ReportCardCache> reportCardCacheList;

    private LanguageSetting languageSetting;
    private String lang;

    protected void refreshLayout() {
        Intent refresh = new Intent(getApplicationContext(), ReportCardActivity.class);
        finish();
        startActivity(refresh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSetting(this);
        languageSetting.loadLanguage();

        setContentView(R.layout.activity_report_card2);

        casting();
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

        recyclerView = findViewById(R.id.reportCard_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        params.put("classcode", classCode);
        return params;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();
        String res = null;
        if (response != null) {
            reportCardCacheList = new ArrayList<>();
            try {
                if (response.getString("Status").equals("true")) {
                    res = response.getString("Response");
                    JSONArray dataArray = response.getJSONArray("Data");

                    if (!dataArray.equals("{}")) {                                 // || !dataObject.equals("null")
                        for (int i = 0; i < dataArray.length(); i++) {
                            if (!dataArray.toString().equals("[]")) {
                                JSONObject routeDetailsObject = dataArray.getJSONObject(i);
                                String ExamNameEng = routeDetailsObject.getString("ExamNameEng");
                                String ExamCode = routeDetailsObject.getString("ExamCode");
                                reportCardCacheList.add(new ReportCardCache(ExamNameEng, ExamCode));
                            }
                        }
                    }
                }
                //showInView();
            } catch (Exception e) {
                setMessage(e.getMessage());
            }

            showInView();
        } else {
            loadTextView.setVisibility(View.VISIBLE);
            loadTextView.setText(getResources().getString(R.string.ReportCard_comeOnline));
        }
    }

    private void showInView() {
        //todo load in ui
        RecyclerAdapter adapter = new RecyclerAdapter(this, reportCardCacheList.size()) {
            private TextView examlistTextView;
            private MaterialRippleLayout rippleLayout;

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportcard_recycleritems, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                examlistTextView = itemView.findViewById(R.id.reportCard_examList);
                rippleLayout = itemView.findViewById(R.id.reportCard_listSelect);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                ReportCardCache reportCardCache = reportCardCacheList.get(position);
                examlistTextView.setText(reportCardCache.ExamNameEng);
                rippleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       redirectIntent(reportCardCache);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);

    }
    private void redirectIntent(ReportCardCache reportCardCache){
        Intent intent = new Intent(this, ReportCardDetailActivity.class);
        intent.putExtra("cache", reportCardCache);
        startActivity(intent);
    }

    private void setFieldInvisible() {
        loadTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //showInView();
        refreshLayout();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
