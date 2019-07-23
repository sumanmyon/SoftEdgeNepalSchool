package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AttendanceCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.AttendancePresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

import static www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage.studentDataCacheList;

public class AttendanceActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView, attendancePresentDays, attendanceUserName, attendanceClass;
    private ImageView attendanceProfile;
    private CardView attendanceCardPresentDays;
    private ProgressBar progressBar;
    private View backpress;
    private RecyclerView recyclerView;
    private PieChart attendancePichart;

    AttendancePresenter presenter;
    private final String StudentId = "3074";

    private List<AttendanceCache> attendanceCacheList;

    final float[] totalWorkingDays = new float[1];
    final float[] totalPresentDays = new float[1];

    public List<StudentDataCache> studentDataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        this.studentDataList = studentDataCacheList;

        casting();
        loadProfile();

        presenter = new AttendancePresenter(this);
        getJsonData();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadProfile() {
        if(studentDataList != null) {
            attendanceUserName.setText(studentDataList.get(0).username);
            attendanceClass.setText(studentDataList.get(0).userclass);

            ShowInGlide glide = new ShowInGlide(this);
            glide.loadURL(studentDataList.get(0).imageUrl);
            glide.loadFailed(R.drawable.ic_profile_img);
            glide.show(attendanceProfile);
        }
    }

    @Override
    public void casting() {
        progressBar = findViewById(R.id.attendane_progressbar);
        loadTextView = findViewById(R.id.attendance_loading);
        backpress = findViewById(R.id.attendance_bt_close);

        attendanceUserName = findViewById(R.id.attendanceUserName);
        attendanceClass = findViewById(R.id.attendanceClass);
        attendanceProfile = findViewById(R.id.attendanceProfile);

        attendancePresentDays = findViewById(R.id.attendancePresentDays);
        attendanceCardPresentDays = findViewById(R.id.attendanceCardPresentDays);
        attendancePichart = findViewById(R.id.attendancePichart);

       recyclerView = findViewById(R.id.attendanceRecycler);
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
        params.put("studentID", StudentId);
        return params;
    }

    @Override
    public void setJsonData(JSONObject response) {
        //setMessage(response.toString());
        setFieldInvisible();

        try {
            if(response.getString("Status").equals("true")){
                JSONObject data = response.getJSONObject("Data");
                if(!data.toString().equals("null")) {
                    attendanceCacheList = new ArrayList<>();
                    String isPresentToday = data.getString("IsPresentToday");

                    JSONArray monthWiseArray = data.getJSONArray("MonthWise");
                    //setMessage(monthWiseArray.length()+"");

                    List<AttendanceCache.MonthWise> monthWiseList = new ArrayList<>();
                    for (int i = 0; i < monthWiseArray.length(); i++) {
                        JSONObject monthWise = monthWiseArray.getJSONObject(i);
                        String MonthCode = monthWise.getString("MonthCode");
                        String MonthName = monthWise.getString("MonthName");
                        String WorkingDays = monthWise.getString("WorkingDays");
                        String PresentDays = monthWise.getString("PresentDays");

                        //setMessage(monthWise.getString("MonthName"));
                        AttendanceCache.MonthWise monthWiseData;
                        monthWiseData = new AttendanceCache.MonthWise(MonthCode, MonthName, WorkingDays, PresentDays);
                        monthWiseList.add(monthWiseData);
                    }

                    AttendanceCache attendanceCache = new AttendanceCache(isPresentToday, monthWiseList);
                    attendanceCacheList.add(attendanceCache);
                    setInView();
                }
            }
        }catch (Exception e){
            setMessage(e.getMessage());
        }
    }

    private void setInView() {
        if(attendanceCacheList.get(0).IsPresentToday.equals("true")){
            attendancePresentDays.setText("Present Today");
            attendanceCardPresentDays.setCardBackgroundColor(getResources().getColor(R.color.green_500));
        }else {
            attendancePresentDays.setText("Absent Today");
            attendanceCardPresentDays.setCardBackgroundColor(getResources().getColor(R.color.red_500));
        }

        //todo set username, class and total attedence of whole years
        RecyclerAdapter adapter = new RecyclerAdapter(this, attendanceCacheList.get(0).MonthWiseList.size()) {
            private TextView monthTextView, workingDaysTextView, presentDaysTextView;
            private PieChart pieChart;
            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_attendance, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                monthTextView = itemView.findViewById(R.id.recyclerAttendanceTopic);
                workingDaysTextView = itemView.findViewById(R.id.recyclerAttendanceWorkingDays);
                presentDaysTextView = itemView.findViewById(R.id.recyclerAttendancePresentDays);
                pieChart = itemView.findViewById(R.id.recyclerAttendancePichart);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                AttendanceCache.MonthWise monthWise = attendanceCacheList.get(0).MonthWiseList.get(position);

                monthTextView.setText(monthWise.MonthName);
                workingDaysTextView.setText(monthWise.WorkingDays);
                presentDaysTextView.setText(monthWise.PresentDays);

                float workingDays = Float.parseFloat(monthWise.WorkingDays);
                float presentDays = Float.parseFloat(monthWise.PresentDays);
                int percentage = Math.round(presentDays / workingDays * 100);

                totalWorkingDays[0] = totalWorkingDays[0] + workingDays;
                totalPresentDays[0] = totalPresentDays[0] + presentDays;

                //todo for chart total attedence of whole month
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(workingDays));
                pieEntries.add(new PieEntry(presentDays));
                pieChart(pieChart, pieEntries, percentage);

                //setMessage(position+"");

                if(position == getSize()-1){
                    setMessage(position+"");
                    setTotalAtendance();
                }
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void setTotalAtendance() {
        int percentage = Integer.valueOf(Math.round(totalPresentDays[0]/totalWorkingDays[0] * 100));

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(totalWorkingDays[0]));
        pieEntries.add(new PieEntry(totalPresentDays[0]));
        pieChart(attendancePichart, pieEntries, percentage);
    }

    private void pieChart(PieChart pieChart, ArrayList<PieEntry> pieEntries, Integer percentage) {
        //setting parameters for pie chart
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(16f);
        pieChart.setFilterTouchesWhenObscured(false);

        pieChart.setHoleRadius(84f);
        pieChart.setHoleColor(Color.WHITE);

        //setting entry to make pie chart
        ArrayList<PieEntry> entries = pieEntries;
        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setDrawIcons(false);
        int[] colors = {
                Color.parseColor("#4CAF50"),
                Color.parseColor("#1B5E20")
        };
        pieDataSet.setColors(colors);

        //putting PieDataSet to PieData
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setDrawSliceText(false);
        pieChart.getData().setDrawValues(false);
        pieChart.getLegend().setEnabled(false);
        // refresh/update pie chart

        pieChart.setCenterText(percentage+"%");
        pieChart.invalidate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setFieldVisible(){
        progressBar.setVisibility(View.VISIBLE);
        loadTextView.setVisibility(View.VISIBLE);
    }

    private void setFieldInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
        loadTextView.setVisibility(View.INVISIBLE);
    }
}
