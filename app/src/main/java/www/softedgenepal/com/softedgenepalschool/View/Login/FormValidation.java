package www.softedgenepal.com.softedgenepalschool.View.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage.EditTextError;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;
import static www.softedgenepal.com.softedgenepalschool.Model.Cache.Token.setUserTokenKey;

public class FormValidation implements View.OnClickListener, Validate{
    private String school = "school";
    private String teacher = "teacher";
    private String student = "student";

    Activity activity;
    EditText editTextUserName;
    EditText editTextPassword;
    Drawable drawable;

    boolean user=false;
    boolean password=false;

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

    @Override
    public void validateWithDataBase(String userName, String password) {
        if(userName.equals(school)&&password.equals(school)){
            MainActivity.userType = school;
            setMessage(school);
            redirect();
        }else if(userName.equals(teacher)&&password.equals(teacher)){
            MainActivity.userType = teacher;
            //setUserTokenKey("t35CtJeBT3Npx4sdD7PBzjLiaUZI1mGn");
            setMessage(teacher);
            redirect();
        }else if(userName.equals(student)&&password.equals(student)){
            MainActivity.userType = student;
            //setUserTokenKey("ZIh3NbunvHsZknDpREWA6CmKZ97NNMdE");
            setMessage(student);
            redirect();
        }else {
            MainActivity.userType = student;
            //setUserTokenKey("ZIh3NbunvHsZknDpREWA6CmKZ97NNMdE");
            setMessage(student);
            redirect();
        }
    }

    private void redirect(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private void setMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
