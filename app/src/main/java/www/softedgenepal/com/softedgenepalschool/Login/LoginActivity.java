package www.softedgenepal.com.softedgenepalschool.Login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.CustomMessage.ShowMessageInSnackBar;
import www.softedgenepal.com.softedgenepalschool.CustomMessage.ShowMessageInToast;
import www.softedgenepal.com.softedgenepalschool.QRandBarCode.QRScan;
import www.softedgenepal.com.softedgenepalschool.R;

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
        buttonLogin.setOnClickListener(new FormValidation(editTextUserName, editTextPassword));
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

    private void showMessage2(String message){
        ShowMessageInToast show = new ShowMessageInToast(this);
        show.setCustomToast(R.drawable.logo,message,show.toastLongLength,Gravity.BOTTOM,0,10);
        show.show();
    }

    private void glideDemo(){
//        String url = "https://picsum.photos/200/300?grayscale";
//        url="https://cdn-images-1.medium.com/max/1600/0*akL0KXb54mViVajR.";
//        ShowInGlide showInGlide = new ShowInGlide(this);
//        showInGlide.loadURL(url);
//        showInGlide.forGif();
//        showInGlide.show(imageView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        qrScan.onResult(requestCode, resultCode, data);
    }


}
