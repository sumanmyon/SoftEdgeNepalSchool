package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

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
        url = url + "?UserId="+params.get("UserId")+"&datebefore="+params.get("datebefore");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //setMessage(response.toString());
                        getAllUserLeaveApplication.parseData(response);
                        getProgressBarInVisibility();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getProgressBarInVisibility();
                setMessageInTextView("Connection Failed!");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void setMessageInTextView(String message){
        getAllUserLeaveApplication.setMessageInTextView(message);
    }

    private void getProgressBarInVisibility(){
        getAllUserLeaveApplication.leaveApplicationPresenter.getProgressBarInVisibility();
    }
}
