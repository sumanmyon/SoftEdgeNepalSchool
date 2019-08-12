package www.softedgenepal.com.softedgenepalschool.Model.Repositroy;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.AttendancePresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;

public class AttendanceModel implements IContractor.Model {
    private AttendancePresenter attendancePresenter;

    public AttendanceModel(AttendancePresenter attendancePresenter) {
        this.attendancePresenter = attendancePresenter;
    }

    @Override
    public void setMessage(String message) {
        attendancePresenter.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        attendancePresenter.setLog(topic, body);
    }

    @Override
    public void getJsonData() {
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online( attendancePresenter.getParams());
        }else {
            //todo go offline
            offline();
        }
    }

    private void online(Map<String, String> params) {
        String Url = new URL().attendanceUrl()+
                "?studentID="+params.get("studentID");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.Attendance);
                preference.storeData(response.toString());
                offline();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setMessage(error.toString());
                offline();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(getContext());
        preference.setType(preference.Attendance);
        String data = preference.getData();

        if(data==null){
            setMessage("There is not any attendance.");
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
            // parseJson(response);
            setJsonData(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setJsonData(JSONObject response) {
        attendancePresenter.setJsonData(response);
    }

    private Context getContext() {
        return attendancePresenter.getCalContext();
    }
}
