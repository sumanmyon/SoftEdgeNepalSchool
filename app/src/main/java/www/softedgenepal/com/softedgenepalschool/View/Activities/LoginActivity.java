package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.PrintWriter;
import java.io.StringWriter;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Login.CheckUserLogin;
import www.softedgenepal.com.softedgenepalschool.View.Login.FormValidation;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class LoginActivity extends AppCompatActivity {
    //casting
    static public EditText editTextUserName, editTextPassword;
    static public Button buttonLogin;
    public Button buttonQR;
    ImageView imageView;

    private LanguageSettingv2 languageSetting;
    private CheckUserLogin checkUserLogin;

    public static LoginActivity loginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();

        super.onCreate(savedInstanceState);

        loginActivity = this;

        loadUI();

        //QR Scan
        qRScan();

        Constants.GENERATE_TOKEN = FirebaseInstanceId.getInstance().getToken();

        //todo for login history which devices are connected to app server
        Constants.DEVICE_MODEL_NAME_AND_IMEI = Build.BRAND + " " + android.os.Build.MODEL + " " + Build.PRODUCT + " imei: "+ imei();

        Log.d("DeviceConfig2", Constants.DEVICE_MODEL_NAME_AND_IMEI);

        //login button
        buttonLogin.setOnClickListener(new FormValidation(this, editTextUserName, editTextPassword, null, Constants.GENERATE_TOKEN));
    }

    public String imei(){
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            String imei = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imei = telephonyManager.getDeviceId(0);
                imei = imei + " " + telephonyManager.getDeviceId(1);
            }else {
                imei = telephonyManager.getDeviceId();
            }

            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!userType.equals("School")) {
            UserModel data = (UserModel) PreferencesForObject.get(this, Constants.LoginCredential, Constants.LoginCredential, null, UserModel.class);
            try {
                checkUserLogin = new CheckUserLogin(this);
                checkUserLogin.fromOfflineCall(data);
            } catch (Exception e) {
                setLog("LoginForm", "1. "+e.getMessage());
                checkUserLogin = new CheckUserLogin(this);
                checkUserLogin.setSchoolType();
            }
        }
    }

    private void loadUI() {
        setContentView(R.layout.activity_login);

        //casting
        casting();
    }

    private void casting() {
        editTextUserName = findViewById(R.id.login_edit_text_username);
        editTextPassword = findViewById(R.id.login_edit_text_password);
        buttonLogin = findViewById(R.id.login_button_login);
        buttonQR = findViewById(R.id.login_button_QR);
        imageView = findViewById(R.id.login_image_view);
    }

    private void qRScan() {
        buttonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setLog(String topic, String message) {
//        Log.d(topic, message);
    }

}