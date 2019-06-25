package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.View.CustomAlertDialog.AlertDialog;

public class CancelLeaveApplication{
    private ShowAllLeaveApplication context;
    private AlertDialog alertDialog;

    public CancelLeaveApplication(ShowAllLeaveApplication context, AlertDialog alertDialog) {
        this.context=context;
        this.alertDialog=alertDialog;
    }

    public void cancelRequest(Map<String, String> params) {
        callApi(params);
        //setMessage("Coming Soon " + params.get("SystemCode"));
    }

    private void callApi(Map<String, String> params) {
        //todo set url missing
        String Url = new URL(context).cancelLeaveApplicationUrl() + "?SystemCode="+params.get("SystemCode");
        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("Status").equals("true")){
                                setMessage(response.getString("Response"));
                                destroy();
                                refreshShowAllLeaveApplication();
                            }else {
                                setMessage(response.getString("Response"));
                                destroy();
                                refreshShowAllLeaveApplication();
                            }
                        }catch (Exception e){
                            setMessage(e.getMessage());
                            destroy();
                            refreshShowAllLeaveApplication();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setMessage(error.getMessage());
                destroy();
                refreshShowAllLeaveApplication();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonOblect);
    }

    public void setMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void destroy(){
        alertDialog.cancel();
    }

    private void refreshShowAllLeaveApplication(){
        context.refreshRecyclerView();
    }
}
