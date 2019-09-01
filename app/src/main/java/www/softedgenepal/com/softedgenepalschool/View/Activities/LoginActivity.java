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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.Result;

import org.json.JSONArray;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.UserModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Login.CheckUserLogin;
import www.softedgenepal.com.softedgenepalschool.View.Login.FormValidation;
import www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation.SchoolNav;

import static android.Manifest.permission_group.CAMERA;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;
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
//        setLog("GENERATE_TOKEN", Constants.GENERATE_TOKEN);
        //login button
        buttonLogin.setOnClickListener(new FormValidation(this, editTextUserName, editTextPassword, null, Constants.GENERATE_TOKEN));
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
//        Log.d(topic, message);
    }

}
