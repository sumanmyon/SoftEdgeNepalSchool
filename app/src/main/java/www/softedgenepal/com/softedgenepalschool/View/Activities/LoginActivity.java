package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.QRandBarCode.QRScan;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Login.FormValidation;

public class LoginActivity extends AppCompatActivity {
    //casting
    public EditText editTextUserName,  editTextPassword;
    public Button buttonQR, buttonLogin;
    ImageView imageView;
    QRScan qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUI();

        //QR Scan
        //qRScan();

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
        qrScan = new QRScan(this);
        buttonQR.setOnClickListener(qrScan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        qrScan.onResult(requestCode, resultCode, data);
    }
}
