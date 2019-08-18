package www.softedgenepal.com.softedgenepalschool.View.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Base64EncodeAndDecode;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DataSnapshotConverterToGson;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;

public class CheckUserLogin {
    private Activity context;
    private String userName;
    private String password;
    private List<UserCache> userCacheList;
    private ProgressDialog progressDialog;

    public  CheckUserLogin(){

    }

    public CheckUserLogin(String userName, String password, Activity context){
        this.userName = userName;
        this.password = password;
        this.context = context;
    }

    public void queryDataBaseOnline( ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserName").equalTo(userName);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                try {
                    JSONArray jsonArray = DataSnapshotConverterToGson.getJsonArrayFromString(dataSnapshot);
                    parseData(jsonArray, context);
                    checkLogin(jsonArray);
                    progressDialog.dismiss();
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            } else {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    public void parseData(JSONArray jsonArray, Activity context) throws Exception{
        this.context = context;
        userCacheList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject profile = jsonArray.getJSONObject(0);

            String FullName = profile.getString("FullName");
            String Id = profile.getString("Id");
            String ImageUrl = profile.getString("ImageUrl");
            String Password = profile.getString("Password");
            String Role = profile.getString("Role");
            String SystemCode = profile.getString("SystemCode");
            String UserName = profile.getString("UserName");

            userCacheList.add(new UserCache(FullName, Id, ImageUrl, Password, Role, SystemCode, UserName));
        }
    }

    private void checkLogin(JSONArray jsonArray){
        String enPass = Base64EncodeAndDecode.encode(password).trim();
        if (enPass.equals(userCacheList.get(0).getPassword())) {
            storeUserCredentials(jsonArray);
            setUserType();
            setMessage(context.getResources().getString(R.string.login_success));
        } else {
            setMessage(context.getResources().getString(R.string.login_failed));
        }
    }


    public void setSchoolType() {
        MainActivity.userType = "School";
        MainActivity.userCache = null;
        redirect();
    }

    public void setUserType() {
        MainActivity.userType = userCacheList.get(0).getRole();
        MainActivity.userCache = userCacheList.get(0);
        redirect();
    }

    private void storeUserCredentials(JSONArray jsonArray) {
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.LoginCredential);
        preference.storeData(jsonArray.toString());
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
}
