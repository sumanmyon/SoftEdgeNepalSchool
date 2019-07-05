package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.R;

/**
 * Fragment containing a ic_calendar_white for a particular year and month.
 */
@SuppressLint("ValidFragment")
public class CalendarFragment extends Fragment {

    private CalendarAdapter mAdapter;
    private GridView mCalendar;
    private GridView mCalendarHeaders;
    private RecyclerView recyclerView;

    private CalenderDate mCurrentDate = new CalenderDate(2000, 1, 1);
    List<CalenderCache> cacheList;
    static Activity activity;

    @SuppressLint("ValidFragment")
    public CalendarFragment(List<CalenderCache> calenderCacheList, Activity activity) {
        this.cacheList=calenderCacheList;
        this.activity =activity;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        changeTitle(view);

        // Initialize grid views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);



        mAdapter = new CalendarAdapter(getContext(), mCurrentDate, cacheList, recyclerView);     //todo dates show
        //mAdapter.recyclerViewUpdate(recyclerView);

        mCalendar = (GridView)view.findViewById(R.id.calendar);
        mCalendar.setAdapter(mAdapter);

        mCalendarHeaders = (GridView)view.findViewById(R.id.calendar_headers);
        mCalendarHeaders.setAdapter(new CalendarHeaderAdapter(getContext()));

        // Set vertical spacing of ic_calendar_white according to display height

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int spacing = (int) (metrics.heightPixels / 800f * 17f);
        mCalendar.setVerticalSpacing(spacing);
        mCalendar.setVerticalScrollBarEnabled(false);

        mCalendarHeaders.setVerticalSpacing(spacing);

        return view;
    }

    private void changeTitle(View view) {
        String nepali = NepaliTranslator.getNumber(mCurrentDate.year + "") + " "
                + NepaliTranslator.getMonth(mCurrentDate.month);

        CalenderDate eDate1 = new CalenderDate(mCurrentDate.year, mCurrentDate.month, 1).convertToEnglish();
        CalenderDate eDate2 = new CalenderDate(mCurrentDate.year, mCurrentDate.month, 26).convertToEnglish();

        String english = getEnglishMonth(eDate1.month) + "/"
                + getEnglishMonth(eDate2.month);
        english += " " + eDate1.year + (eDate1.year==eDate2.year?"":"/"+eDate2.year);

        String monthYear = nepali + "\n" + english;
        ((TextView)view.findViewById(R.id.monthYear)).setText(monthYear);

//        view.findViewById(R.id.calendar_title).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((CalenderActivity)getActivity()).toggleCalendar();
//            }
//        });
    }

    private static String getEnglishMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

    /**
     * Set year and month for this ic_calendar_white.
     * @param year Year to display.
     * @param month Month to display.
     */
    public void set(int year, int month) {
        mCurrentDate.year = year;
        mCurrentDate.month = month;

        if (mAdapter != null){
            mAdapter.changeDate(mCurrentDate);

        }
    }
}
