package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.BusRouteAndLiveBusTracking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.MapBoxPresenter;

public class MapBoxModel implements IContractor.Model {
    MapBoxPresenter mapBoxPresenter;
    private String studentId;

    public MapBoxModel(MapBoxPresenter mapBoxPresenter) {
        this.mapBoxPresenter=mapBoxPresenter;
    }

    @Override
    public void getJsonData(String studentId) {
        this.studentId = studentId;
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online();
        }else {
            //todo go offline
            offline();
        }
    }

    private void online() {
        Map<String, String> params = mapBoxPresenter.getParams();
        String Url = new URL().busRouteUrl();
        Url = Url + "?id=" + params.get("id");
        Url = Url + "&role=" + params.get("role");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.BusRoute);
                preference.storeData(response.toString(), studentId);
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
        preference.setType(preference.BusRoute);
        String data = preference.getData(studentId);

        if(data==null){
            setMessage("There is not any route.");
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
            setJsonData(response);
        } catch (Exception e) {
            e.printStackTrace();
            setJsonData(null);
        }
    }

    @Override
    public void setJsonData(JSONObject response) {
        mapBoxPresenter.setJsonData(response);
    }

    private Context getContext() {
        return mapBoxPresenter.getCalContext();
    }

    @Override
    public void setMessage(String message) {
        mapBoxPresenter.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        mapBoxPresenter.setLog(topic, body);
    }
}
