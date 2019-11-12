package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.LeaveApplicationPresenter;
import www.softedgenepal.com.softedgenepalschool.R;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;

public class CreateLeaveApplication implements LeaveApplicationContractor.Model, LeaveApplicationContractor.Model.Create {
    LeaveApplicationPresenter leaveApplicationPresenter;
    private Context context;

    CreateLeaveApplication(){
    }

    public CreateLeaveApplication(LeaveApplicationPresenter leaveApplicationPresenter) {
        this.leaveApplicationPresenter=leaveApplicationPresenter;
        this.context = leaveApplicationPresenter.getContext();
    }

    @Override
    public void setMessage(String message) {
        leaveApplicationPresenter.setMessage(message);
    }

    //todo recall api for
    @Override
    public void postUploadData(List<String> data) {
        if(new NetworkConnection(context).isConnectionSuccess()) {
            String Url = new URL(context).getCreateLeaveApplicationUrl();
            Url = Url + "?UserId=" + data.get(0) + "&Subject=" + URLEncoder.encode(data.get(1)) + "&Message=" + URLEncoder.encode(data.get(2)) + "&From=" + data.get(3) + "&To=" + data.get(4);
            Log.d("CreateLeaveApp", Url);

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("Status").equals("true")) {
                            setMessage(response.getString("Response"));
                            leaveApplicationPresenter.createProgressBarInVisibility();
                            refresh();
                        }
                    } catch (Exception e) {
                        setMessage(e.getMessage());
                        leaveApplicationPresenter.createProgressBarInVisibility();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setMessage(context.getResources().getString(R.string.Network_error));
                    leaveApplicationPresenter.createProgressBarInVisibility();
                }
            });

//        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(
//                DEFAULT_TIMEOUT_MS,
//                5,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            //calling volley interface to get data
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonOblect);
        }else {
            setMessage("It seems you are offline. Please check your network to send your Leave application.");
            leaveApplicationPresenter.createProgressBarInVisibility();
        }
    }

    @Override
    public void refresh() {
        leaveApplicationPresenter.refresh();
    }
}
