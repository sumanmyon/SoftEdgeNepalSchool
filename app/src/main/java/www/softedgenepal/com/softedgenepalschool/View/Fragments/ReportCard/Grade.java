package www.softedgenepal.com.softedgenepalschool.View.Fragments.ReportCard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ReportCardDetailCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ReportCardDetailActivity.reportCardDetailCacheList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Grade extends Fragment {
    private Integer red, green, blue;
    private List<ReportCardDetailCache> reportCardDetailCacheLists;

    private RecyclerView recyclerView;
    private TextView gradeTextView, attendanceTextView, remarksTextView, terminalTextView;

    private String title = null;

    public Grade() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        red = getContext().getResources().getColor(R.color.red_500);
        green = getContext().getResources().getColor(R.color.green_500);
        blue = getContext().getResources().getColor(R.color.blue_500);

        casting(view);
        terminalTextView.setText(title);
        this.reportCardDetailCacheLists = reportCardDetailCacheList;
        try {
            showInView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setLog(String topic, String body) {
        Log.d(topic, body);
    }

    private void casting(View  view ) {
        recyclerView = view.findViewById(R.id.reportCard_grade_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);

        gradeTextView = view.findViewById(R.id.repordCardGrade);
        attendanceTextView = view.findViewById(R.id.repordCardAttendance);
        remarksTextView = view.findViewById(R.id.repordCardRemarks);
        terminalTextView = view.findViewById(R.id.terminalTextView);
    }


    private void showInView() throws Exception{
        final int size = reportCardDetailCacheLists.get(0).Marks.size();

        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), size) {
            private TextView recyclerReportCardSN, recyclerReportCardSubject,
                    recyclerReportCardTheroy, recyclerReportCardPractical,
                    recyclerReportCardFinalGrade,recyclerReportCardFinalGradePoint;

            float obtainGradePoint  = 0.0f;

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                animation_type = ItemAnimation.NONE;

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportcard_recyclerview_grade, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                recyclerReportCardSN = itemView.findViewById(R.id.reportCard_sn);
                recyclerReportCardSubject = itemView.findViewById(R.id.reportCard_subject);
                recyclerReportCardTheroy = itemView.findViewById(R.id.reportCard_theory);
                recyclerReportCardPractical= itemView.findViewById(R.id.reportCard_Practical);
                recyclerReportCardFinalGrade = itemView.findViewById(R.id.reportCard_finalGrade);
                recyclerReportCardFinalGradePoint = itemView.findViewById(R.id.reportCard_gradePoint);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                ReportCardDetailCache.Marks marks =  reportCardDetailCacheLists.get(0).Marks.get(position);
                recyclerReportCardSN.setText(String.valueOf(position+1));
                recyclerReportCardSubject.setText(marks.Subject);

                recyclerReportCardTheroy.setText(marks.ThGrade+"");
                recyclerReportCardPractical.setText(marks.PrGrade+"");
                recyclerReportCardFinalGrade.setText(marks.Grade);
                recyclerReportCardFinalGradePoint.setText(marks.GradePoint);

                if(marks.IsAbsentTheory.equals("true")){
                    recyclerReportCardTheroy.setText("Abs");
                    recyclerReportCardTheroy.setTextColor(red);
                }

                if(marks.IsAbsentPractical.equals("true")){
                    recyclerReportCardPractical.setText("Abs");
                    recyclerReportCardPractical.setTextColor(red);
                }

                obtainGradePoint = obtainGradePoint + Float.valueOf(marks.GradePoint);

                if(position == size-1){
                    float avgGradePoint = calculateAverageGrade(size, obtainGradePoint);
                    showOverallResult(avgGradePoint);
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private float calculateAverageGrade(int size, float obtainGradePoint) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String per = df.format(obtainGradePoint/size);

        return Float.valueOf(per);
    }

    private void showOverallResult(float avgGradePoint) {
        gradeTextView.setText(avgGradePoint+"");
    }

    public void setTitle(String examNameEng) {
        this.title = examNameEng;
    }
}
