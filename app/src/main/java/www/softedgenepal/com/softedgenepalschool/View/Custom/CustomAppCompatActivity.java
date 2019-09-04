package www.softedgenepal.com.softedgenepalschool.View.Custom;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

public class CustomAppCompatActivity extends AppCompatActivity {
    public int pendingRequestsCount = 0;
    public static final Gson GSON = new Gson();
    public Activity activity = this;

    protected void showErrorPopUp(String title, String message){
        CustomAlertDialogs dialog = new CustomAlertDialogs();
        dialog.showErrorPopUp(title, message, activity);
    }

    protected void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

