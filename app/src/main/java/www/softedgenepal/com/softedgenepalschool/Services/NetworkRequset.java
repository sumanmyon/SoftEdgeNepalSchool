package www.softedgenepal.com.softedgenepalschool.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;

public class NetworkRequset extends Request<JSONObject> {
    private final String TAG = "NetworkRequest";

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private Context context;
    private String url = "";

    public NetworkRequset(Context context, String url, int method, Map<String, String> params,
                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method,new URL().url + url, errorListener);

        try{
            this.context = context;
            this.url = url;
            this.params = params;
            this.listener = listener;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() {
        if(params == null)
            return new HashMap<>();
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try{
            String jsonString = new String(response.data);
            Log.e(TAG, "jsonString==" + jsonString);
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));

        }catch (Exception e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        try {
            Log.e(TAG, "jsonObject==" + response.toString(4));
            listener.onResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
