package www.softedgenepal.com.softedgenepalschool.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.R;

public class ApiCall {
    private final String TAG = "ApiService";

    private boolean showDialog = true;
    private ProgressDialog progressDialog;

    private static ApiCall call = new ApiCall();
    public static ApiCall getInstance(){
        return call;
    }

    public void connect(Context context, final String url, int method,
                        HashMap<String, String> params, ResultListener listener, String loadMessage){
        try {
            if (new NetworkConnection(context).isConnectionSuccess()) {
                if(showDialog)
                    loadMessage = (loadMessage != null) ? loadMessage : context.getString(R.string.Loading);
                showDialog(context, loadMessage);

                NetworkRequset requset = new NetworkRequset(context, url, method, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse() called");
                        listener.onResult(url, true, response, null, progressDialog);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse() called");
                        listener.onResult(url, false, null, error, progressDialog);
                    }
                });
                requset.setRetryPolicy(new DefaultRetryPolicy(50000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(context).add(requset);
            }else {
                Toast.makeText(context, context.getString(R.string.Network_error), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showDialog(Context context, String loadMessage) {
        try {
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(loadMessage);
            progressDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface ResultListener {
        void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog);
    }
}
