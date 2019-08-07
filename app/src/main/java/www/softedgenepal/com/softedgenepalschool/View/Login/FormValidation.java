package www.softedgenepal.com.softedgenepalschool.View.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage.EditTextError;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Base64EncodeAndDecode;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;

public class FormValidation implements View.OnClickListener, Validate{
    private String school = "school";
    private String teacher = "teacher";
    private String student = "student";

    private String userN;
    private String passW;

    private List<UserCache> userCacheList;

    Activity activity;
    EditText editTextUserName;
    EditText editTextPassword;
    Drawable drawable;

    boolean user=false;
    boolean password=false;

    private ProgressDialog progressDialog;

    public FormValidation(Activity activity, EditText editTextUserName, EditText editTextPassword) {
        this.activity=activity;
        this.editTextUserName=editTextUserName;
        this.editTextPassword=editTextPassword;
    }

    public FormValidation(EditText editTextUserName, EditText editTextPassword, Drawable drawable) {
        this.editTextUserName=editTextUserName;
        this.editTextPassword=editTextPassword;
        this.drawable=drawable;
    }

    @Override
    public void onClick(View v) {
        //todo validating form
        if(TextUtils.isEmpty(editTextUserName.getText())){
            if(drawable!=null){
                new EditTextError().setErrorForEditText(editTextUserName,"Please type username", drawable);
            }else {
                new EditTextError().setErrorForEditText(editTextUserName,"Please type username");
            }
            user = false;
        }else {
            user = true;
        }

        if(TextUtils.isEmpty(editTextPassword.getText())){
            if(drawable!=null){
                new EditTextError().setErrorForEditText(editTextPassword,"Please type password",drawable);
            }else {
                new EditTextError().setErrorForEditText(editTextPassword,"Please type password");
            }
            password = false;
        }else {
            password = true;
        }

        if (user && password){
            //todo request to server and, validate and get/store token
            validateWithDataBase(editTextUserName.getText().toString(), editTextPassword.getText().toString());
        }
    }


    private void storeUserCredentials(){

    }

    @Override                                           //admin123
    public void validateWithDataBase(String userName, String password) {
        progressDialog = new ProgressDialog(activity, R.style.AlertDialogTheme);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sign in....");
        progressDialog.show();

       // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        userName = userName.toUpperCase();
        this.passW = password;

        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("UserName")
                .equalTo(userName);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            userCacheList = new ArrayList<>();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String FullName = snapshot.child("FullName").getValue().toString();
                    String Id = snapshot.child("Id").getValue().toString();
                    String ImageUrl = snapshot.child("ImageUrl").getValue().toString();
                    String Password = snapshot.child("Password").getValue().toString();
                    String Role = snapshot.child("Role").getValue().toString();
                    String SystemCode = snapshot.child("SystemCode").getValue().toString();
                    String UserName = snapshot.child("UserName").getValue().toString();

                    userCacheList.add(new UserCache(FullName, Id, ImageUrl, Password, Role, SystemCode, UserName));
                }

                String enPass = Base64EncodeAndDecode.encode(passW).trim();
                setMessage(enPass);
                if(enPass.equals(userCacheList.get(0).getPassword())){
                    progressDialog.dismiss();
                    setUserType();
                    setMessage("Successfully Login.");
                }else {
                    progressDialog.dismiss();
                    setMessage("Login Failed.");
                }
            }else {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    private void setUserType(){
        MainActivity.userType = userCacheList.get(0).getRole();
        MainActivity.userCache = userCacheList.get(0);
        redirect();
    }

    private void redirect(){
        UserCache cache = userCacheList.get(0);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("userCache", cache);
        activity.startActivity(intent);
        activity.finish();
    }

    private void setMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
