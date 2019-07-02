package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.SpliteDateOrTime;
import www.softedgenepal.com.softedgenepalschool.Presenter.CalenderPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.CalenderContractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.EventAdapter;

public class EventActivity extends AppCompatActivity implements CalenderContractor.View {
    CalenderPresenter presenter;
    Map<String, String> params;
    List<CalenderCache> eventList;

    private TextView loadTextView;
    private ProgressBar progressBar;

    private EventAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        casting();

        //params to fetch url
        params = new HashMap<>(); //?From=10/10/1995&To=12/12/2023
        params.put("From","10/10/1995");
        params.put("To","12/12/2023");

        FetchData(params);
    }

    //todo fetch data here
    public void FetchData(Map<String, String> params){
        presenter = new CalenderPresenter(this);
        presenter.mapModel();
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

    private void casting() {
        progressBar = findViewById(R.id.event_progressbar);
        loadTextView = findViewById(R.id.event_loading);

        recyclerView = findViewById(R.id.event_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setData(List<CalenderCache> calenderCacheList) {
        setFieldInvisible();
        this.eventList = calenderCacheList;
        loadUI();
    }

    private void loadUI() {
        setMessage(String.valueOf(eventList.size()));
        //todo show in view
        int count = 0;
        List<CalenderCache> getEventList = new ArrayList<>();
        int i[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (CalenderCache cache : eventList){
            if(cache.type.equals("0")){      // type = 0 means :: events
                //todo convert date to nepali
                //split dates
                String[] startD = cache.start.split("-");
                int startMonth = SpliteDateOrTime.convertToNepali(startD).month;

                String[] endD = cache.start.split("-");
                int endMonth = SpliteDateOrTime.convertToNepali(endD).month;

                if(startMonth == endMonth) {
                    for (int m : i) {
                        if (startMonth == m) {
                            String month = getStringsMonth().get(startMonth - 1);
                            cache.month = month;
                            cache.expand = false;
                            getEventList.add(cache);
                        }
                    }
                }
            }
        }

        //todo set data as months wise prewiew

        adapter = new EventAdapter(this, getEventList, getStringsMonth(), ItemAnimation.BOTTOM_UP);
        recyclerView.setAdapter(adapter);
    }


    public List<String> getStringsMonth() {
        List<String> items = new ArrayList<>();
        String arr[] = getResources().getStringArray(R.array.month);
        for (String s : arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadUI();
    }
}
