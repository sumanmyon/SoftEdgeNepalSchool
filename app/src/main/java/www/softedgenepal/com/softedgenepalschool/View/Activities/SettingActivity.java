package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.R;

public class SettingActivity extends AppCompatActivity {
    private View backpress;
    public Switch aSwitch;
    public TextView settingLanguageText, settingLanguageTitle;

    private LanguageSetting languageSetting;
    String lang;
    int yourCurrentState = 0;

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSetting(this);
        lang = languageSetting.loadLanguage();

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            yourCurrentState = savedInstanceState.getInt(ACTIVITY_STATE);

        }

        setContentView(R.layout.activity_setting);

        //casting
        casting();
        switchUpdate(lang);
        reAnimateView();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo work here
                if (!aSwitch.isChecked()) {
                    languageSetting.changeLanguage("en");
                    switchUpdate("en");
                } else {
                    languageSetting.changeLanguage("ne");
                    switchUpdate("ne");
                }
                reAnimateView();
                onRestart();
            }
        });
    }

    static final String ACTIVITY_STATE = "current_state";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the app current state
        savedInstanceState.putInt(ACTIVITY_STATE, yourCurrentState);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void casting() {
        backpress = findViewById(R.id.setting_bt_close);
        aSwitch = findViewById(R.id.settingLanguageSwitch);
        settingLanguageText = findViewById(R.id.settingLanguageText);
        settingLanguageTitle = findViewById(R.id.settingLanguageTitle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void switchUpdate(String b) {
        if (b.equals("en")) {
            aSwitch.setChecked(false);
        } else if (b.equals("ne")) {
            aSwitch.setChecked(true);
        }
    }

    public void reAnimateView() {
        settingLanguageText.setText(getResources().getString(R.string.language_type));
        settingLanguageTitle.setText(getResources().getString(R.string.language_title));
    }
}
