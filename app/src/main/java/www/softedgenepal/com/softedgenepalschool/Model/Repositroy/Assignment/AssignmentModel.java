package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Assignment;

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

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.Presenter.AssignmentPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.R;

public class AssignmentModel implements AssignmentContractor.Model {
    private AssignmentPresenter assignmentPresenter;
    private List<AssignmentCache> assignmentCacheList;

    public AssignmentModel(AssignmentPresenter assignmentPresenter) {
        this.assignmentPresenter = assignmentPresenter;
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
        String Url = new URL().assignmentUrl()+
                "?dateFrom="+params.get("From")+
                "&dateTo="+params.get("To")+
                "&studentId="+params.get("studentId");
       // setMessage(Url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Url", response.toString());
                //Todo store for offline
                StoreInSharePreference preference = new StoreInSharePreference(getContext());
                preference.setType(preference.Assignment);
                preference.storeData(response.toString());
                offline();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Url", error.getMessage());
                offline();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void offline() {
        StoreInSharePreference preference = new StoreInSharePreference(getContext());
        preference.setType(preference.Assignment);
        String data = preference.getData();

        if(data==null){
            setMessage(getContext().getResources().getString(R.string.not_any_assignment));
            return;
        }

        try {
            JSONObject response = new JSONObject(data);
           // parseJson(response);
            setJsonDataToView(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setJsonDataToView(JSONObject response) {
        assignmentPresenter.setJsonData(response);

    }

    private Context getContext() {
        return assignmentPresenter.getCalContext();
    }

    @Override
    public void setMessage(String message) {
        assignmentPresenter.setMessage(message);
    }
}
