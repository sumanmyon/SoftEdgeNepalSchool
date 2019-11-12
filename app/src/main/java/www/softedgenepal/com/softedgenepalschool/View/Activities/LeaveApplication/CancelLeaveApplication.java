package www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication;

import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;

public class CancelLeaveApplication{
    private ShowAllLeaveApplication context;
    private View.OnClickListener view;

    public CancelLeaveApplication(ShowAllLeaveApplication context, View.OnClickListener view) {
        this.context=context;
        this.view=view;
    }

    public void cancelRequest(Map<String, String> params) {
        callApi(params);
        //setMessage("Coming Soon " + params.get("SystemCode"));
    }

    private void callApi(Map<String, String> params) {
        //todo set url missing
        String Url = new URL(context).cancelLeaveApplicationUrl() + "?SystemCode="+params.get("SystemCode")+"&Role="+params.get("Role");
        if(new NetworkConnection(context).isConnectionSuccess()) {
            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("Status").equals("true")) {
                            setMessage(response.getString("Response"));
                            //destroy();
                            refreshShowAllLeaveApplication();
                        } else {
                            setMessage(response.getString("Response"));
                            //destroy();
                            refreshShowAllLeaveApplication();
                        }
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        //destroy();
                        refreshShowAllLeaveApplication();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setMessage(error.getMessage());
                    //destroy();
                    refreshShowAllLeaveApplication();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonOblect);
        }else {
            setMessage(context.getResources().getString(R.string.Network_error));
        }
    }

    public void setMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    private void refreshShowAllLeaveApplication(){
        context.refreshRecyclerView();
    }
}
