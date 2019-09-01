package www.softedgenepal.com.softedgenepalschool.View.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DataSnapshotConverterToGson;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserModel;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LoginActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;

public class CheckUserLogin {
    private Activity context;
    private String userName;
    private String password;
    private String qrScan;
    private String fid;
    private List<UserCache> userCacheList;
    private UserModel user;
    private ProgressDialog progressDialog;

    public CheckUserLogin() {

    }

    public CheckUserLogin(Activity context) {
        this.context = context;
    }

    public CheckUserLogin(String userName, String password, String qrScan, String fid, Activity context) {
        this.userName = userName;
        this.password = password;
        this.qrScan = qrScan;
        this.fid = fid;
        this.context = context;
    }

    //todo for firebase login
//    public void queryDataBaseOnline() {
//        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserName").equalTo(userName);
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }
//
//    public void queryDataBaseOnline(ProgressDialog progressDialog) {
//        this.progressDialog = progressDialog;
//        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserName").equalTo(userName);
//        query.addListenerForSingleValueEvent(valueEventListener);
//    }

//    private ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                try {
//                    JSONArray jsonArray = DataSnapshotConverterToGson.getJsonArrayFromString(dataSnapshot);
//                    parseData(jsonArray, context);
//                    checkLogin(jsonArray);
//                    if (progressDialog != null) {
//                        progressDialog.dismiss();
//                    }
//                } catch (Exception e) {
//                    setMessage(context.getResources().getString(R.string.login_failed));
//                    if (progressDialog != null) {
//                        progressDialog.dismiss();
//                    }
//                }
//            } else {
//                setMessage(context.getResources().getString(R.string.login_failed));
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            setMessage(context.getResources().getString(R.string.login_failed));
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//            }
//        }
//    };

//    public void parseData(JSONArray jsonArray, Activity context) throws Exception {
//        this.context = context;
//        userCacheList = new ArrayList<>();
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject profile = jsonArray.getJSONObject(0);
//
//            String FullName = profile.getString("FullName");
//            String Id = profile.getString("Id");
//            String ImageUrl = profile.getString("ImageUrl");
//            String Password = profile.getString("Password");
//            String Role = profile.getString("Role");
//            String SystemCode = profile.getString("SystemCode");
//            String UserName = profile.getString("UserName");
//
//            userCacheList.add(new UserCache(FullName, Id, ImageUrl, Password, Role, SystemCode, UserName));
//        }
//    }

//    private void checkLogin(JSONArray jsonArray) {
//        String enPass;
//        if (context instanceof MainActivity) {
//            enPass = password;
//        } else {
//            enPass = Base64EncodeAndDecode.encode(password).trim();
//        }
//
//        if (enPass.equals(userCacheList.get(0).getPassword())) {
//            storeUserCredentials(jsonArray);
//            setUserType();
//            if (context instanceof MainActivity) {
//
//            } else {
//                setMessage(context.getResources().getString(R.string.login_success));
//            }
//
//        } else {
//            if (context instanceof MainActivity) {
//                clearCredential();
//                reDirectToMainActivityAfterLogout();
//            }else {
//                setMessage(context.getResources().getString(R.string.login_failed));
//            }
//
//        }
//    }

    private void clearCredential() {
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.LoginCredential);
        preference.clear();
    }


    public void setSchoolType() {
        MainActivity.userType = "School";
        MainActivity.user = null;
        redirect();
    }

    public void setUserType() {
        MainActivity.userType = user.Role;
        MainActivity.user = user;
        if (context instanceof MainActivity) {

        } else {
            redirect();
        }
    }

    private void reDirectToMainActivityAfterLogout() {
        setMessage(context.getResources().getString(R.string.login_failed));
        Intent intent = new Intent(context, LoginActivity.class);
        setSchoolType();
        context.startActivity(intent);
        context.finish();
    }

    private void storeUserCredentials() {
        PreferencesForObject.store(context, Constants.LoginCredential, Constants.LoginCredential, user);
    }

    private void redirect() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    private void setMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void setLog(String topic, String message) {
        Log.d(topic, message);
    }

    public void fromAPICall(ProgressDialog progressDialog) {
        try {
            this.progressDialog = progressDialog;
            String url = new URL().getLoginUrl();
            if (qrScan != null) {
                url = url + "UserName&Password&QRCode="+URLEncoder.encode( qrScan, "UTF-8");
            } else {
                url = url + "UserName=" + userName + "&Password=" + password + "&QRCode";
            }
            url = url +"&FID=" + URLEncoder.encode( fid, "UTF-8");


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("Status").equals("false")) {
                            dismissProgessBar();
                            failedMessage();
                        } else {
                            dismissProgessBar();
                            successMessage();

                            parseDataFromAPI(response.getString("Data"), context);
                            setUserType();
                        }
                    } catch (Exception e) {
                        dismissProgessBar();
                        setMessage(e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgessBar();
                    if (progressDialog != null) setMessage(error.getStackTrace().toString());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseDataFromAPI(String data, Activity context) {
        this.context = context;
        user = new Gson().fromJson(data, new TypeToken<UserModel>() {
        }.getType());
        if (qrScan != null) {
            user.QrScan = qrScan;
        } else {
            user.UserName = userName;
            user.Password = password;
        }
        user.FID = fid;

        //todo save userId, role, userName, password
        storeUserCredentials();
    }

    private void dismissProgessBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void failedMessage() {
        if (progressDialog != null) {
            setMessage(context.getResources().getString(R.string.login_failed));
        }
    }

    private void successMessage() {
        if (progressDialog != null) {
            setMessage(context.getResources().getString(R.string.login_success));
        }
    }

    public void fromOfflineCall(UserModel data) {
        user = data;
        setUserType();
    }
}
