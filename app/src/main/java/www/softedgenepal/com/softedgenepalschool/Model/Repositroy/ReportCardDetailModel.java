package www.softedgenepal.com.softedgenepalschool.Model.Repositroy;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
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
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.ReportCardPresenter;

public class ReportCardDetailModel implements IContractor.Model {
    private ReportCardPresenter reportCardPresenter;

    public ReportCardDetailModel(ReportCardPresenter reportCardPresenter) {
        this.reportCardPresenter = reportCardPresenter;
    }

    @Override
    public void setMessage(String message) {
        reportCardPresenter.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        reportCardPresenter.setLog(topic, body);
    }

    @Override
    public void getJsonData() {
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online( reportCardPresenter.getParams());
        }else {
            //todo go offline
            offline();
        }
    }

    private void online(Map<String, String> params) {
        String Url = new URL().reportCardDetailUrl()+
                "?studentId="+params.get("studentId")+
                "&examId="+params.get("examId");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.ReportDetailCard);
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

        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(getContext());
        preference.setType(preference.ReportDetailCard);
        String data = preference.getData();

        if(data==null){
            setMessage("report card is not yet published.");
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
        reportCardPresenter.setJsonData(response);
    }

    private Context getContext() {
        return reportCardPresenter.getCalContext();
    }
}
