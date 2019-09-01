package www.softedgenepal.com.softedgenepalschool.View.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage.EditTextError;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Base64EncodeAndDecode;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DataSnapshotConverterToGson;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;

public class FormValidation implements View.OnClickListener, Validate {
    private String school = "school";
    private String teacher = "teacher";
    private String student = "student";

    private String userN;
    private String passW;

    private List<UserCache> userCacheList;
    private String qrScan;
    private String fid;
    Activity activity;
    EditText editTextUserName;
    EditText editTextPassword;
    Drawable drawable;

    boolean user = false;
    boolean password = false;

    private ProgressDialog progressDialog;

    public FormValidation(Activity activity, EditText editTextUserName, EditText editTextPassword, String qrScan, String fid) {
        this.activity = activity;
        this.editTextUserName = editTextUserName;
        this.editTextPassword = editTextPassword;
        this.qrScan = qrScan;
        this.fid = fid;
        if(qrScan!=null){
            request();
        }
    }

    public FormValidation(EditText editTextUserName, EditText editTextPassword, Drawable drawable) {
        this.editTextUserName = editTextUserName;
        this.editTextPassword = editTextPassword;
        this.drawable = drawable;
    }

    @Override
    public void onClick(View v) {
       request();
    }

    private void request(){
        //todo validating form
        if(qrScan != null){
            validateWithDataBase(null, null, qrScan, fid);
        }else {
            if (TextUtils.isEmpty(editTextUserName.getText())) {
                if (drawable != null) {
                    new EditTextError().setErrorForEditText(editTextUserName, activity.getResources().getString(R.string.username_error), drawable);
                } else {
                    new EditTextError().setErrorForEditText(editTextUserName, activity.getResources().getString(R.string.username_error));
                }
                user = false;
            } else {
                user = true;
            }

            if (TextUtils.isEmpty(editTextPassword.getText())) {
                if (drawable != null) {
                    new EditTextError().setErrorForEditText(editTextPassword, activity.getResources().getString(R.string.password_error), drawable);
                } else {
                    new EditTextError().setErrorForEditText(editTextPassword, activity.getResources().getString(R.string.password_error));
                }
                password = false;
            } else {
                password = true;
            }

            if (user && password) {
                //todo request to server and, validate and get/store token
                validateWithDataBase(editTextUserName.getText().toString(), editTextPassword.getText().toString(), qrScan, fid);
            }
        }
    }

    @Override                               //nanda             //admin123
    public void validateWithDataBase(String userName, String password, String QRScan, String FID) {
        progressDialog = new ProgressDialog(activity, R.style.AlertDialogTheme);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(activity.getResources().getString(R.string.loading_sign_in));
        progressDialog.show();

        // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        if (new NetworkConnection(activity).isConnectionSuccess()) {
            if(qrScan != null) {
                //todo for qr
                new CheckUserLogin(userName, password, qrScan, fid, activity).fromAPICall(progressDialog);//.queryDataBaseOnline(progressDialog);
            }else {
                userName = userName.toUpperCase();
                this.passW = password;
                new CheckUserLogin(userName, password, qrScan, fid, activity).fromAPICall(progressDialog);//.queryDataBaseOnline(progressDialog);
            }

        } else {
            progressDialog.dismiss();
            setMessage(activity.getString(R.string.Network_error));
        }
    }

    private void setMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
//    private ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            userCacheList = new ArrayList<>();
//            if (dataSnapshot.exists()) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String FullName = snapshot.child("FullName").getValue().toString();
//                    String Id = snapshot.child("Id").getValue().toString();
//                    String ImageUrl = snapshot.child("ImageUrl").getValue().toString();
//                    String Password = snapshot.child("Password").getValue().toString();
//                    String Role = snapshot.child("Role").getValue().toString();
//                    String SystemCode = snapshot.child("SystemCode").getValue().toString();
//                    String UserName = snapshot.child("UserName").getValue().toString();
//
//                    userCacheList.add(new UserCache(FullName, Id, ImageUrl, Password, Role, SystemCode, UserName));
//                }
//
//                String enPass = Base64EncodeAndDecode.encode(passW).trim();
//                //setMessage(enPass);
//                if(enPass.equals(userCacheList.get(0).getPassword())){
//                    progressDialog.dismiss();
//                    setUserType();
//                    setMessage(activity.getResources().getString(R.string.login_success));
//                }else {
//                    progressDialog.dismiss();
//                    setMessage(activity.getResources().getString(R.string.login_failed));
//                }
//            }else {
//                progressDialog.dismiss();
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            progressDialog.dismiss();
//        }
//    };
}
