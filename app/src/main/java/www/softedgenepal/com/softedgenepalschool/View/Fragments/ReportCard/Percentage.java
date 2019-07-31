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
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ReportCardCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.ReportCardActivity.reportCardCacheList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Percentage extends Fragment {

    private TextView reportCardTheoryFullMarksTotal, reportCardTheoryPassMarksTotal,
            reportCardPracticalFullMarksTotal, reportCardPracticalPassMarksTotal,
            reportCardObtainMarks;
    private TextView percentageTextView, positionTextView, resultTextView;
    private RecyclerView recyclerView;

    private Integer red, green, blue;

    private List<ReportCardCache> reportCardCacheLists;


    public Percentage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_percentage, container, false);

        red = getContext().getResources().getColor(R.color.red_500);
        green = getContext().getResources().getColor(R.color.green_500);
        blue = getContext().getResources().getColor(R.color.blue_500);

        casting(view);
        this.reportCardCacheLists = reportCardCacheList;
        showInView();

        return view;
    }

    private void casting(View  view ) {
        recyclerView = view.findViewById(R.id.reportCard_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);

        reportCardTheoryFullMarksTotal = view.findViewById(R.id.reportCard_theory_total_fm);
        reportCardTheoryPassMarksTotal = view.findViewById(R.id.reportCard_theory_total_pm);
        reportCardPracticalFullMarksTotal = view.findViewById(R.id.reportCard_practical_total_fm);
        reportCardPracticalPassMarksTotal = view.findViewById(R.id.reportCard_practical_total_pm);
        reportCardObtainMarks = view.findViewById(R.id.reportCard_total_marks);

        percentageTextView = view.findViewById(R.id.repordCardPercentage);
        positionTextView = view.findViewById(R.id.repordCardPosition);
        resultTextView = view.findViewById(R.id.repordCardResult);
    }


    private void setMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setLog(String topic, String body) {
        Log.d(topic, body);
    }

    private void showInView(){
        final int size = reportCardCacheLists.get(0).Marks.size();

        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), size) {
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
                ReportCardCache.Marks marks =  reportCardCacheLists.get(0).Marks.get(position);
                recyclerReportCardSN.setText(String.valueOf(position+1));
                recyclerReportCardSubject.setText(marks.Subject);

                float fullM = Float.valueOf(marks.FullMarks);
                float passM = Float.valueOf(marks.PassMarks);
                float obtainM = Float.valueOf(marks.ObtainedMarks);
                recyclerReportCardTheroyFM.setText(fullM+"");
                recyclerReportCardTheroyPM.setText(passM+"");
                recyclerReportCardTheroyOM.setText(obtainM+"");
                if(passM>obtainM){
                    recyclerReportCardTheroyOM.setTextColor(red);
                }

                float fullPM = Float.valueOf(marks.PracticalFullMarks);
                float passPM = Float.valueOf(marks.PracticalPassMarks);
                float obtainPM = Float.valueOf(marks.PracticalObtainedMarks);
                recyclerReportCardPracticalFM.setText(fullPM+"");
                recyclerReportCardPracticalPM.setText(passPM+"");
                recyclerReportCardPracticalOM.setText(obtainPM+"");
                if(passPM>obtainPM){
                    recyclerReportCardPracticalOM.setTextColor(red);
                }

                totalTheroyMarks = totalTheroyMarks + Float.valueOf(marks.FullMarks);
                passTotalTheroyMarks = passTotalTheroyMarks + Float.valueOf(marks.PassMarks);
                totalPracticalMarks = totalPracticalMarks + Float.valueOf(marks.PracticalFullMarks);
                passTotalPracticalMarks = passTotalPracticalMarks + Float.valueOf(marks.PracticalPassMarks);

                float total = Float.valueOf(marks.ObtainedMarks) + Float.valueOf(marks.PracticalObtainedMarks);
                obtainTotalMarks = obtainTotalMarks + total;

                recyclerReportCardSubjectTotal.setText(total+"");
                if((passM+passPM)>total){
                    recyclerReportCardSubjectTotal.setTextColor(red);
                }

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

        percentageTextView.setText(per+"%");
        positionTextView.setText(reportCardCacheList.get(0).Position);

        String result = reportCardCacheList.get(0).Result;
        resultTextView.setText(result);

        if(result.equals("Pass")) {
            resultTextView.setTextColor(green);
        }else if(result.equals("Fail")) {
            resultTextView.setTextColor(red);
        }else {
            resultTextView.setTextColor(blue);
        }
    }
}
