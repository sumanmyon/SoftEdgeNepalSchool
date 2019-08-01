package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.BusRouteCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.RoutineCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.RoutinePresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.EventAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.BottomSheetFragment;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.RoutineFragment;

public class RoutineActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View backpress;

    private RoutinePresenter presenter;
    List<RoutineCache> routineCacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        casting();

        presenter = new RoutinePresenter(this);
        getJsonData();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    @Override
    public void casting() {
        progressBar = findViewById(R.id.routine_progressbar);
        loadTextView = findViewById(R.id.routine_loading);
        backpress = findViewById(R.id.routine_bt_close);

        recyclerView = findViewById(R.id.routine_recyclerView);
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
        params.put("studentID", "3090");
        return params;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();

        if(response != null) {
            //setLog("BusRouteActivity", response.toString());
            routineCacheList = new ArrayList<>();
            try {
                if (response.getString("Status").equals("true")) {
                    if (response.getString("Response").equals("Success")) {
                        JSONArray dataArray = response.getJSONArray("Data");
                        if (!dataArray.toString().equals("[]")||!dataArray.toString().equals("null")) {
                            if (dataArray.length() >= 0) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataObject = dataArray.getJSONObject(i);
                                    String SystemCode = dataObject.getString("ExamNameEng");

                                    JSONArray routeDetailsArray = dataObject.getJSONArray("Routine");
                                    List<RoutineCache.Routine> routeDetailsList = new ArrayList<>();
                                    for (int j = 0; j < routeDetailsArray.length(); j++) {
                                        if (!routeDetailsArray.toString().equals("[]")) {
                                            JSONObject routeDetailsObject = routeDetailsArray.getJSONObject(j);
                                            String ExamDate = routeDetailsObject.getString("ExamDate");
                                            String StartTime = routeDetailsObject.getString("StartTime");
                                            String EndTime = routeDetailsObject.getString("EndTime");
                                            String SubjectNameEng = routeDetailsObject.getString("SubjectNameEng");
                                            routeDetailsList.add(new RoutineCache.Routine(ExamDate, StartTime, EndTime, SubjectNameEng));
                                        }
                                    }
                                    routineCacheList.add(new RoutineCache(SystemCode, routeDetailsList));
                                }
                            }
                        }
                    }
                }

                showInView();
            } catch (Exception e) {
                setLog("RoutineActivity", e.getMessage());
            }
        }else {
            loadTextView.setVisibility(View.VISIBLE);
            loadTextView.setText("Please come online to get routine.");
        }
    }

    private void showInView() {
        //setMessage(routineCacheList.get(0).routine.get(0).SubjectNameEng);
        //todo recycler view
        RecyclerAdapter adapter = new RecyclerAdapter(this, routineCacheList.size()) {
            private TextView examinationTextView, startDateTextView, endDateTextView;
            private View listItemSelect;

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routine_list, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                examinationTextView = itemView.findViewById(R.id.routine_listExamination);
                startDateTextView = itemView.findViewById(R.id.routine_listStartDate);
                endDateTextView = itemView.findViewById(R.id.routine_listEndDate);
                listItemSelect = itemView.findViewById(R.id.routine_listSelect);
            }

            @Override
            public void onBind(final ViewHolder viewHolder, int position) {
                final RoutineCache routineCache = routineCacheList.get(position);
                examinationTextView.setText(routineCache.ExamNameEng);

                String startDate = "";
                String endDate = "";
                if(!routineCache.routine.get(0).ExamDate.equals("null")) {
                     startDate = DateTime.DateConvertToNepali(routineCache.routine.get(0).ExamDate, "date");
                }
                if(!routineCache.routine.get(routineCache.routine.size()-1).ExamDate.equals("null")){
                    endDate = DateTime.DateConvertToNepali(routineCache.routine.get(routineCache.routine.size()-1).ExamDate, "date");
                }

                startDateTextView.setText(startDate);
                endDateTextView.setText(endDate);

                listItemSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoutineFragment fragment = new RoutineFragment();
                        //some thing here
                        fragment.setAssignment(routineCache);
                        fragment.show(getSupportFragmentManager(), fragment.getTag());
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void setFieldInvisible() {
        loadTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    public void close(){
        onBackPressed();
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
