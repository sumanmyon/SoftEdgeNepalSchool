package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Login.FormValidation;

import static android.Manifest.permission_group.CAMERA;

public class LoginActivity extends AppCompatActivity {
    //casting
    static public EditText editTextUserName,  editTextPassword;
    public Button buttonQR, buttonLogin;
    ImageView imageView;

    private LanguageSetting languageSetting;
    private String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        languageSetting = new LanguageSetting(this);
        languageSetting.loadLanguage();

        super.onCreate(savedInstanceState);
        loadUI();

        //QR Scan
        qRScan();

        //login button
        buttonLogin.setOnClickListener(new FormValidation(this,editTextUserName, editTextPassword));
    }

    private void loadUI(){
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

    private void qRScan(){
        buttonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
    }

}
