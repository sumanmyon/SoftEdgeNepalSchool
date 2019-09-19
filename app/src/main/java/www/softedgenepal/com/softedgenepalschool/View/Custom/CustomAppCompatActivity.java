package www.softedgenepal.com.softedgenepalschool.View.Custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import www.softedgenepal.com.softedgenepalschool.R;

public class CustomAppCompatActivity extends AppCompatActivity implements View.OnClickListener {
    public int pendingRequestsCount = 0;
    public static final Gson GSON = new Gson();
    public Activity activity = this;

    protected View backpress;
    protected TextView toolBarTitle;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == backpress.getId()){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void showErrorPopUp(String title, String message){
        CustomAlertDialogs dialog = new CustomAlertDialogs();
        CustomAlertDialogs.showErrorPopUp(title, message, activity);
    }

    public void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showErrorInTextView(TextView textView, String message){
        textView.setError(message);
    }

    protected void redirect(Class<?> aClass){
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    protected void casting() {
        toolBarTitle = findViewById(R.id.toolBarTitleText);
        backpress = findViewById(R.id.close);
        backpress.setOnClickListener(this);
    }
}

