package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.R;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView schoolLogoImageView;
    private TextView schoolNameTextView,addressTextView, phoneNoTextView, faxTextView, emailTextView, webTextView;

    private LanguageSettingv2 languageSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();
        setContentView(R.layout.activity_about);

        //casting
        casting();

        //fetch/get data

        //toolbar
        setToolBar();

        //set data
        setInFields();
    }

    private void setToolBar() {
        toolbar.setTitle("About Us");
    }

    private void setInFields() {
        //for compatible image for different devices
        //at first get display size
        //now set image
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(this, schoolLogoImageView);
        screenSize.setCompitableForHeight(3);
        schoolLogoImageView.setImageDrawable(getResources().getDrawable(R.drawable.logo));

        schoolNameTextView.setText("Welcome to\nSoftEdge Nepal");
        addressTextView.setText("Singhadurbar East Gate, Anamnagar, Kathmandu, Nepal");
        phoneNoTextView.setText("+977-9841729323\n+977-9801029323");
        faxTextView.setText("info@softedgenepal.com");
        emailTextView.setText("info@softedgenepal.com");
        webTextView.setText("https://softedgenepal.com");
    }

    private void casting() {
        toolbar = findViewById(R.id.about_toolbar);
        schoolLogoImageView = findViewById(R.id.schoolLogo_imageView);
        schoolNameTextView = findViewById(R.id.schoolName_TextView);
        addressTextView = findViewById(R.id.schoolAddress_TextView);
        phoneNoTextView = findViewById(R.id.schoolPhoneNo_TextView);
        faxTextView = findViewById(R.id.schoolFax_TextView);
        emailTextView = findViewById(R.id.schoolEmail_TextView);
        webTextView = findViewById(R.id.schoolWeb_TextView);
    }
}
