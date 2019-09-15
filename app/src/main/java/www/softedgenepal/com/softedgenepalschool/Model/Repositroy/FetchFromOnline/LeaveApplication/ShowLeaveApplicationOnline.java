package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication.GetAllUserLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;

public class ShowLeaveApplicationOnline {
    private GetAllUserLeaveApplication getAllUserLeaveApplication;
    private Context context;
    private String url;

    public ShowLeaveApplicationOnline(GetAllUserLeaveApplication getAllUserLeaveApplication, Context context) {
        this.getAllUserLeaveApplication=getAllUserLeaveApplication;
        this.context=context;
        url = new URL(context).getLeaveApplicationUrl();
    }

    public void get(Map<String, String> params){
        //url = url + "?UserId=1&datebefore=12/12/2055";
        url = url + "?UserId="+params.get("UserId")+"&Role="+params.get("Role")+"&From="+params.get("From")+"&To=";
        Log.d("StudentShowApp", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //setMessage(response.toString());
                        StoreInSharePreference preference = new StoreInSharePreference(context);
                        preference.setType(preference.LeaveApplication);
                        preference.storeData(response.toString());

                        parse(response);
                        getProgressBarInVisibility();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getProgressBarInVisibility();
                //setMessageInTextView("Connection Failed!");
                offline();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.LeaveApplication);
        String data = preference.getData();

        if(data==null){
            setMessageInTextView("There is not any Leave Application.");
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
            parse(response);
        } catch (JSONException e) {
            e.printStackTrace();
            parse(null);
        }
    }

    private void parse(JSONObject response) {
        getAllUserLeaveApplication.parseData(response);
    }

    private void setMessageInTextView(String message){
        getAllUserLeaveApplication.setMessageInTextView(message);
    }

    private void getProgressBarInVisibility(){
        getAllUserLeaveApplication.leaveApplicationPresenter.getProgressBarInVisibility();
    }
}
