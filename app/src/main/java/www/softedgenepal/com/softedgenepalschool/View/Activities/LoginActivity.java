package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Login.CheckUserLogin;
import www.softedgenepal.com.softedgenepalschool.View.Login.FormValidation;

import static android.Manifest.permission_group.CAMERA;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

public class LoginActivity extends AppCompatActivity {
    //casting
    static public EditText editTextUserName, editTextPassword;
    public Button buttonQR, buttonLogin;
    ImageView imageView;

    private LanguageSetting languageSetting;
    private String lang;
    private CheckUserLogin checkUserLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        languageSetting = new LanguageSetting(this);
        languageSetting.loadLanguage();

        super.onCreate(savedInstanceState);
        loadUI();

        //QR Scan
        qRScan();

        //login button
        buttonLogin.setOnClickListener(new FormValidation(this, editTextUserName, editTextPassword));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!userType.equals("School")) {
            StoreInSharePreference preference = new StoreInSharePreference(this);
            preference.setType(preference.LoginCredential);
            String data = preference.getData();
            try {
                JSONArray array = new JSONArray(data);
                checkUserLogin = new CheckUserLogin();
                checkUserLogin.parseData(array, this);
                checkUserLogin.setUserType();
            } catch (Exception e) {
                setLog("LoginForm", e.getMessage());
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
        Log.d(topic, message);
    }

}
