package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Calendar;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;


/**
 * The start-up activity.
 */
public class CalenderActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.AlternateAppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        FetchData();

        //calender();
    }

    private void calender() {
        // Setup ic_calendar_white pages
        mViewPager = (ViewPager)findViewById(R.id.calendarPager);
        //mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new CalendarPagerAdapter(getSupportFragmentManager(), 0, calenderCacheList, this));

        Date today = new Date(Calendar.getInstance()).convertToNepali();
        mViewPager.setCurrentItem(
                (today.year - DateUtils.startNepaliYear) * 12 + (today.month - 1)
        );
    }

    private int currentType = 0;
    public void toggleCalendar() {
        currentType = 1 - currentType;

        if (mViewPager != null) {
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setAdapter(new CalendarPagerAdapter(getSupportFragmentManager(),
                    currentType, calenderCacheList, this));
            mViewPager.setCurrentItem(currentItem);
        }
    }

    //todo fetch data here
    List<CalenderCache> calenderCacheList;
    public void FetchData(){
        String Url = "http://192.168.100.100:423/api/data/geteventinfo?From=10/10/1995&To=12/12/2020";
        calenderCacheList = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("Status").equals("true")){
                        if(response.getString("Response").equals("Success")) {
                            JSONArray dataArray = response.getJSONArray("Data");

                            if (dataArray.length() >= 0) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject data = dataArray.getJSONObject(i);

                                    String title = data.getString("Title");
                                    String description = data.getString("Description");
                                    String startDate = splitDate(data.getString("Start"), "date");
                                    String endDate = splitDate(data.getString("End"), "date");
                                    String type = data.getString("Type");
                                    String backgroundColor = data.getString("BackgroundColor");
                                    String isActive = data.getString("IsActive");

                                    calenderCacheList.add(new CalenderCache(title, description, startDate, endDate, type, backgroundColor, isActive));

                                    calender();
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    setMessage(e.getMessage());
                    calender();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setMessage(error.toString());
                calender();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String splitDate(String date, String type){
        //removing created time from created date
        String convert = DateOrTimeFormate2(date);
        String split[] = convert.split("T");
        String dateOrTime = "";
        if(type.equals("date"))
            dateOrTime = split[0];
        else if(type.equals("time")){
            dateOrTime =split[1];
        }
        return dateOrTime;
    }

    public void setMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
