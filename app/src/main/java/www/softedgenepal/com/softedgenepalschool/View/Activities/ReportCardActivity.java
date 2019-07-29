package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.support.v4.view.ViewCompat;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ReportCardCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.ReportCardPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

public class ReportCardActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView,
            reportCardTheoryFullMarksTotal, reportCardTheoryPassMarksTotal,
            reportCardPracticalFullMarksTotal, reportCardPracticalPassMarksTotal,
            reportCardObtainMarks;
    private TextView percentageTextView, positionTextView, resultTextView;
    private ProgressBar progressBar;
    private View backpress;
    private RecyclerView recyclerView;

    private ReportCardPresenter presenter;

    private String studentId = "3074";
    private String examId = "2";

    private List<ReportCardCache> reportCardCacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card);

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
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);

        reportCardTheoryFullMarksTotal = findViewById(R.id.reportCard_theory_total_fm);
        reportCardTheoryPassMarksTotal = findViewById(R.id.reportCard_theory_total_pm);
        reportCardPracticalFullMarksTotal = findViewById(R.id.reportCard_practical_total_fm);
        reportCardPracticalPassMarksTotal = findViewById(R.id.reportCard_practical_total_pm);
        reportCardObtainMarks = findViewById(R.id.reportCard_total_marks);

        percentageTextView = findViewById(R.id.repordCardPercentage);
        positionTextView = findViewById(R.id.repordCardPosition);
        resultTextView = findViewById(R.id.repordCardResult);
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
        //studentId=3074&examId=2
        Map<String, String> params = new HashMap<>();
        params.put("studentId",studentId);
        params.put("examId", examId);
        return params;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();
       // setMessage(response.toString());

        String res = null;
        if(response != null) {
            //setLog("BusRouteActivity", response.toString());
            reportCardCacheList = new ArrayList<>();
            try {
                if (response.getString("Status").equals("true")) {
                    //if (response.getString("Response").equals("Success")) {
                    res  = response.getString("Response");
                        JSONObject dataObject = response.getJSONObject("Data");
                        if (!dataObject.equals("{}")) {
                            String Position = dataObject.getString("Position");
                            String Result = dataObject.getString("Result");

                            List<ReportCardCache.Marks> marksList = new ArrayList<>();
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
                                    String CGPA = "";

                                    marksList.add(new ReportCardCache.Marks(
                                            SubjectCode,Subject,
                                            FullMarks, PassMarks, ObtainedMarks,
                                            PracticalFullMarks, PracticalPassMarks, PracticalObtainedMarks,
                                            IsAbsentPractical, IsAbsentTheory,
                                            Highest, Grade, GradePoint, CGPA));
                                    //routeDetailsList.add(new RoutineCache.Routine(ExamDate, StartTime, EndTime, SubjectNameEng));
                                }

                                reportCardCacheList.add(new ReportCardCache(Position, Result, marksList));
                                //routineCacheList.add(new RoutineCache(SystemCode, routeDetailsList));
                            }
                    }
                   // }
                }

                showInView();
            } catch (Exception e) {
                setLog("ReportCardActivity", e.getMessage());
                setMessage(res);
            }
        }else {
            loadTextView.setVisibility(View.VISIBLE);
            loadTextView.setText("Please come online to get routine.");
        }
    }

    private void showInView(){
        setMessage(reportCardCacheList.get(0).Result);
        final int size = reportCardCacheList.get(0).Marks.size();

        RecyclerAdapter adapter = new RecyclerAdapter(this, size) {
            private TextView recyclerReportCardSN, recyclerReportCardSubject,
                    recyclerReportCardTheroyFM, recyclerReportCardTheroyPM, recyclerReportCardTheroyOM,
                    recyclerReportCardPracticalFM, recyclerReportCardPracticalPM, recyclerReportCardPracticalOM,
                    recyclerReportCardSubjectTotal;
            float totalTheroyMarks = 0.0f;
            float passTotalTheroyMarks  = 0.0f;
            float totalPracticalMarks = 0.0f;
            float passTotalPracticalMarks  = 0.0f;
            float obtainTotalMarks  = 0.0f;

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                animation_type = ItemAnimation.NONE;

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportcard_recyclerview_percentage, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                recyclerReportCardSN = itemView.findViewById(R.id.reportCard_sn);
                recyclerReportCardSubject = itemView.findViewById(R.id.reportCard_subject);
                recyclerReportCardTheroyFM = itemView.findViewById(R.id.reportCard_theory_fm);
                recyclerReportCardTheroyPM = itemView.findViewById(R.id.reportCard_theroy_pm);
                recyclerReportCardTheroyOM = itemView.findViewById(R.id.reportCard_theroy_om);

                recyclerReportCardPracticalFM= itemView.findViewById(R.id.reportCard_practical_fm);
                recyclerReportCardPracticalPM= itemView.findViewById(R.id.reportCard_practical_pm);
                recyclerReportCardPracticalOM= itemView.findViewById(R.id.reportCard_practical_om);

                recyclerReportCardSubjectTotal= itemView.findViewById(R.id.reportCard_subject_total);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                ReportCardCache.Marks marks =  reportCardCacheList.get(0).Marks.get(position);
                recyclerReportCardSN.setText(String.valueOf(position+1));
                recyclerReportCardSubject.setText(marks.Subject);

                recyclerReportCardTheroyFM.setText(marks.FullMarks);
                recyclerReportCardTheroyPM.setText(marks.PassMarks);
                recyclerReportCardTheroyOM.setText(marks.ObtainedMarks);

                recyclerReportCardPracticalFM.setText(marks.PracticalFullMarks);
                recyclerReportCardPracticalPM.setText(marks.PracticalPassMarks);
                recyclerReportCardPracticalOM.setText(marks.PracticalObtainedMarks);

                totalTheroyMarks = totalTheroyMarks + Float.valueOf(marks.FullMarks);// + Float.valueOf(marks.PracticalFullMarks);
                passTotalTheroyMarks = passTotalTheroyMarks + Float.valueOf(marks.PassMarks); // + Float.valueOf(marks.PracticalPassMarks);
                totalPracticalMarks = totalPracticalMarks + Float.valueOf(marks.PracticalFullMarks);
                passTotalPracticalMarks = passTotalPracticalMarks + Float.valueOf(marks.PracticalPassMarks);

                float total = Float.valueOf(marks.ObtainedMarks) + Float.valueOf(marks.PracticalObtainedMarks);
                obtainTotalMarks = obtainTotalMarks + total;

                recyclerReportCardSubjectTotal.setText(total+"");

                if(position == size-1){
                    calculatePercentage(totalTheroyMarks, passTotalTheroyMarks, totalPracticalMarks, passTotalPracticalMarks, obtainTotalMarks);
                }
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void calculatePercentage(float totalTheroyMarks, float passTotalTheroyMarks,
                                     float totalPracticalMarks, float passTotalPracticalMarks,
                                     float obtainTotalMarks) {
        reportCardTheoryFullMarksTotal.setText(totalTheroyMarks+"");
        reportCardTheoryPassMarksTotal.setText(passTotalTheroyMarks+"");
        reportCardPracticalFullMarksTotal.setText(totalPracticalMarks+"");
        reportCardPracticalPassMarksTotal.setText(passTotalPracticalMarks+"");
        reportCardObtainMarks.setText(obtainTotalMarks+"");

        //todo for percentage
        float percentage = obtainTotalMarks / (totalTheroyMarks + totalPracticalMarks) * 100;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        String per = df.format(percentage);

        percentageTextView.setText(per);
        positionTextView.setText(reportCardCacheList.get(0).Position);

        String result = reportCardCacheList.get(0).Result;
        resultTextView.setText(result);

        if(result.equals("Pass")) {
            resultTextView.setTextColor(getResources().getColor(R.color.green_500));
        }else {
            resultTextView.setTextColor(getResources().getColor(R.color.red_500));
        }
    }

    private void setFieldInvisible() {
        loadTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showInView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
