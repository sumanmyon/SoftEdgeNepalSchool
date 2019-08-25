package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.BaseUrlSetting;
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
    private EditText urlEditText;

    private String reportType = "";
    private String notificationType = "";
    private String urlMatcheRegex = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>"; // matches <http://google.com>
    private String urlMatcheRegex2 = "<^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>"; // does not match <http://google.com>

    private LanguageSetting languageSetting;
    String lang;

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        String url = BaseUrlSetting.getUrl(this);
        if(!url.equals("No name defined")){
            urlEditText.setText(url);
        }

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

        urlEditText = findViewById(R.id.settingBaseUrlEditText);

        setting_done = findViewById(R.id.setting_done);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
        ReportCardSetting.setCardFormate(this, reportType);
        NotificationSetting.setNotification(getApplicationContext(), notificationType);
        if(!TextUtils.isEmpty(urlEditText.getText())){
//            Pattern patt = Pattern.compile(urlMatcheRegex);
//            Matcher matcher = patt.matcher(urlEditText.getText().toString());
//            boolean b = matcher.matches();
//            if(b){
                BaseUrlSetting.setUrl(getApplicationContext(), urlEditText.getText().toString());
                showMessage("Base Url successfully " +getString(R.string.Saved));
//            }else {
//                showMessage("Error");
//            }
        }
        showMessage(getString(R.string.Saved));
    }
}
