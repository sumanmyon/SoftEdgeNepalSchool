package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.NotificationSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.ReportCardSetting;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Notification;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private View backpress;
    private MaterialRippleLayout setting_done;
    public Switch aSwitch, notificationSwitch;
    public TextView settingLanguageText, settingLanguageTitle;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPercentage, radioButtonGPA, radioButtonBoth;
    private RadioButton radioButton;

    private String reportType = "";
    private String notificationType = "";

    private LanguageSetting languageSetting;
    String lang;

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

        notificationType = NotificationSetting.getNotification(this);
        if(notificationType.equals("No name defined")){
            notificationType = "TurnOn";
        }

        setContentView(R.layout.activity_setting);

        //casting
        casting();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        switchUpdate(lang);
        reAnimateView();
        updateNotificationSwitch(notificationType);

        forLanguageSetting();
        forReportCardRadioGoupAndButton();
        forNotificationSetting();
        forSaveSetting();
    }

    private void forLanguageSetting() {
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

    private void forReportCardRadioGoupAndButton() {
        radioButtonPercentage.setOnClickListener(this);
        radioButtonGPA.setOnClickListener(this);
        radioButtonBoth.setOnClickListener(this);

        loadReportCardSetting();
    }

    private void forNotificationSetting() {
        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notificationSwitch.isChecked()){
                    notificationType = "TurnOff";
                    updateNotificationSwitch("TurnOff");
                }else {
                    notificationType = "TurnOn";
                    updateNotificationSwitch("TurnOn");
                }
            }
        });
    }

    private void forSaveSetting() {
        setting_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReportCardSetting();
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void casting() {
        backpress = findViewById(R.id.setting_bt_close);

        aSwitch = findViewById(R.id.settingLanguageSwitch);
        notificationSwitch = findViewById(R.id.settingNotificationSwitch);

        settingLanguageText = findViewById(R.id.settingLanguageText);
        settingLanguageTitle = findViewById(R.id.settingLanguageTitle);

        radioGroup = findViewById(R.id.reportCard_radioGroup);
        radioButtonPercentage = findViewById(R.id.radioButton_percentage);
        radioButtonGPA = findViewById(R.id.radioButton_gpa);
        radioButtonBoth = findViewById(R.id.radioButton_both);

        setting_done = findViewById(R.id.setting_done);
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

    private void updateNotificationSwitch(String turn) {
        if(turn.equals("TurnOff")){
            notificationSwitch.setChecked(false);
        }else if(turn.equals("TurnOn")) {
            notificationSwitch.setChecked(true);
        }
    }

    public void reAnimateView() {
        settingLanguageText.setText(getResources().getString(R.string.language_type));
        settingLanguageTitle.setText(getResources().getString(R.string.language_title));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int checkId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(checkId);
        if(id == R.id.radioButton_percentage){
            reportType = "Percentage";
        }else if(id == R.id.radioButton_gpa){
            reportType = "GPA";
        }else if(id == R.id.radioButton_both){
            reportType = "Both";
        }
    }

    private void loadReportCardSetting() {
        String type = ReportCardSetting.getCardFormate(this);
        if(type.equals("No name defined")|| type.equals("Both")){
            radioGroup.check(R.id.radioButton_both);
            reportType = "Both";
        }else if(type.equals("Percentage")){
            radioGroup.check(R.id.radioButton_percentage);
            reportType = "Percentage";
        }else if(type.equals("GPA")){
            radioGroup.check(R.id.radioButton_gpa);
            reportType = "GPA";
        }
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private void saveReportCardSetting() {
        //radioButton.getText().toString().trim();
        ReportCardSetting.setCardFormate(this, reportType);
        NotificationSetting.setNotification(getApplicationContext(), "TurnOff");
        showMessage(getString(R.string.Saved));
    }
}
