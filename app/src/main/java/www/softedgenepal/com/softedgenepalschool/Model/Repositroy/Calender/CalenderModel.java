package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Calender;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.CalenderPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.CalenderContractor;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

public class CalenderModel implements CalenderContractor.Model {
    private CalenderPresenter calenderPresenter;
    private List<CalenderCache> calenderCacheList;

    public CalenderModel(CalenderPresenter calenderPresenter) {
        this.calenderPresenter=calenderPresenter;
    }

    public void fetchDataFromServer(Map<String, String> params){
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online(params);
        }else {
            //todo go offline
            offline();
        }
    }

    private void online(Map<String, String> params) {
        String Url = new URL().calenderUrl()+"?From="+params.get("From")+"&To="+params.get("To");//"http://192.168.100.100:423/api/data/geteventinfo?From=10/10/1995&To=12/12/2025";//

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.Calender);
                preference.storeData(response.toString(), params.get("studentID"));
                offline();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setMessage(error.toString());
                offline();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(calenderPresenter.getCalContext());
        requestQueue.add(request);
    }

    private void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(getContext());
        preference.setType(preference.Calender);
        String data = preference.getData();

        if(data==null){
            calender();
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
            parseJson(response);
        } catch (JSONException e) {
            e.printStackTrace();
            calender();
        }
    }

    private void parseJson(JSONObject response){
        calenderCacheList = new ArrayList<>();

        try {
            if(response.getString("Status").equals("true")){
                if(response.getString("Response").equals("Success")) {
                    JSONArray dataArray = response.getJSONArray("Data");
                    if(!dataArray.toString().equals("[]")) {
                        if (dataArray.length() >= 0) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject data = dataArray.getJSONObject(i);
                                if (!data.toString().equals("")) {
                                    String title = data.getString("Title");
                                    String description = data.getString("Description");
                                    String startDate = splitDate(data.getString("Start"), "date");
                                    String endDate = splitDate(data.getString("End"), "date");
                                    String type = data.getString("Type");
                                    String backgroundColor = data.getString("BackgroundColor");
                                    String isActive = data.getString("IsActive");

                                    calenderCacheList.add(new CalenderCache(title, description, startDate, endDate, type, backgroundColor, isActive));
                                }
                            }
                        }
                    }
                }
            }

            calender();
        }catch (Exception e){
            setMessage(e.getMessage());
            calender();
        }
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

    private Context getContext() {
        return calenderPresenter.getCalContext();
    }

    private void calender() {
        calenderPresenter.setData(calenderCacheList);
    }

    @Override
    public void setMessage(String message) {
        calenderPresenter.setMessage(message);
    }
}
