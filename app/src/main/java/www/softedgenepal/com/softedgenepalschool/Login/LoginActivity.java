package www.softedgenepal.com.softedgenepalschool.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import www.softedgenepal.com.softedgenepalschool.CustomMessage.ShowMessageInSnackBar;
import www.softedgenepal.com.softedgenepalschool.CustomMessage.ShowMessageInToast;
import www.softedgenepal.com.softedgenepalschool.R;

public class LoginActivity extends AppCompatActivity {
    //casting
    EditText editTextUserName,  editTextPassword;
    Button buttonQR, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //casting
        casting();

    }

    @Override
    protected void onStart() {
        super.onStart();
        showMessage("My name is Suman Neupane !!! \n What's up");
    }

    private void casting() {
        editTextUserName = findViewById(R.id.login_edit_text_username);
        editTextPassword = findViewById(R.id.login_edit_text_password);
        buttonLogin = findViewById(R.id.login_button_login);
        buttonQR = findViewById(R.id.login_button_QR);
    }


    private void showMessage(final String message){
        ShowMessageInSnackBar showMessageInSnackBar = new ShowMessageInSnackBar(editTextUserName, this);
        showMessageInSnackBar.showSnackBar(message, showMessageInSnackBar.snackIndefinedLength);
        showMessageInSnackBar.setCustomView(R.drawable.logo);
        showMessageInSnackBar.show();

        showMessage2(message);
    }

    private void showMessage2(String message){
        ShowMessageInToast show = new ShowMessageInToast(this);
        show.setCustomToast(R.drawable.logo,message,show.toastLongLength,Gravity.BOTTOM,0,10);
        show.show();
    }

}
