package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class RestRequest {
    protected Context context;
    private String errorMessage;

    //Request methods
    public int GET = Request.Method.GET;
    public int POST = Request.Method.POST;

    private RestRequest(Context context) {
        this.context = context;
    }

    public abstract static class RestJsonObjectRequest{
        Context ctx;
        public RestRequest restRequest;
        protected RestJsonObjectRequest(Context ctx) {
            restRequest = new RestRequest(ctx);
            this.ctx=ctx;
        }

        public void setJsonObjectRequest(int method, String url){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    getJsonObjectRequest(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getError(error);
                }
            });
            //calling volley interface to get data
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(jsonObjectRequest);
        }

        protected abstract void getJsonObjectRequest(JSONObject response);
        public abstract void getError(VolleyError error);
    }
}