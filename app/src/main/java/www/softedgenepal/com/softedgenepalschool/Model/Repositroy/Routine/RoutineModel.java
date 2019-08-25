package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Routine;

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
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.RoutinePresenter;

public class RoutineModel implements IContractor.Model {
    private RoutinePresenter presenter;

    public RoutineModel(RoutinePresenter routinePresenter) {
        this.presenter = routinePresenter;
    }

    @Override
    public void setMessage(String message) {
        presenter.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        presenter.setLog(topic, body);
    }

    @Override
    public void getJsonData() {
        if(new NetworkConnection(getContext()).isConnectionSuccess()) {
            //todo go online
            online();
        }else {
            //todo go offline
            offline();
        }
    }

    private void online() {
        String Url = new URL().routineUrl();
        Map<String, String> params = presenter.getParams();

        Url = Url + "?studentID="+ params.get("studentID");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.Routine);
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
        preference.setType(preference.Routine);
        String data = preference.getData();

        if(data==null){
            setMessage("There is not any assignment.");
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
            setJsonData(response);
        } catch (JSONException e) {
            e.printStackTrace();
            setJsonData(null);
        }
    }

    @Override
    public void setJsonData(JSONObject response) {
        presenter.setJsonData(response);
    }

    private Context getContext() {
        return presenter.getCalContext();
    }
}
