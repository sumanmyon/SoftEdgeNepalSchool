package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalendarPagerAdapter;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.Date;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.DateUtils;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.CalenderPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.CalenderContractor;
import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment implements CalenderContractor.View {
    private ViewPager mViewPager;
    protected ProgressBar progressBar;
    private TextView loadingTextView;

    private  CalenderPresenter presenter;
    private Context context;
    private Map<String, String> params;

    View view;
    public Calendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_calender, container, false);
        context = getContext();
        //casting
        casting(view);

        //params to fetch url
        params = new HashMap<>(); //?From=10/10/1995&To=12/12/2023
        params.put("From","10/10/1995");
        params.put("To","12/12/2023");

        FetchData(params);
        return view;
    }

    private void casting(View view) {
        progressBar = view.findViewById(R.id.calender_progressbar);
        loadingTextView = view.findViewById(R.id.calender_loading);
    }

    private void calender(List<CalenderCache> calenderCacheList) {
        // Setup ic_calendar_white pages
        mViewPager = (ViewPager)view.findViewById(R.id.calendarPager);
        //mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new CalendarPagerAdapter(getActivity().getSupportFragmentManager(), 0,
                calenderCacheList, getActivity()));

        Date today = new Date(java.util.Calendar.getInstance()).convertToNepali();
        mViewPager.setCurrentItem(
                (today.year - DateUtils.startNepaliYear) * 12 + (today.month - 1)
        );

        setFieldInvisible();
    }

    //todo fetch data here
    public void FetchData(Map<String, String> params){
        presenter = new CalenderPresenter(this);
        presenter.mapModel();
        presenter.fetchData(params);
    }

    @Override
    public void setMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
    }

    private void setFieldInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
        loadingTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getCalContext() {
       return context;
    }

    @Override
    public void setData(List<CalenderCache> calenderCacheList) {
        calender(calenderCacheList);
    }
}
