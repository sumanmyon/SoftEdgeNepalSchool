package www.softedgenepal.com.softedgenepalschool.View.HomePage.Fragments.TypeOfHomPage;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize.SetImageWithCompatibleScreenSize;
import www.softedgenepal.com.softedgenepalschool.R;

public class SchoolHomePage {
    private Activity activity;
    private View view;
    private ImageView schoolLogoImageView;
    private TextView schoolNameTextView,addressTextView, phoneNoTextView, faxTextView, emailTextView, webTextView;

    public SchoolHomePage(Activity activity, View view) {
        this.activity=activity;
        this.view=view;
    }

    public void setView() {
        //casting
        casting();

        //fetch/get data

        //set data
        setInFields();
    }

    private void setInFields() {
        //for compatible image for different devices
        //at first get display size
        //now set image
        SetImageWithCompatibleScreenSize screenSize = new SetImageWithCompatibleScreenSize(activity, schoolLogoImageView);
        screenSize.setCompitableForHeight(3);
        schoolLogoImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.logo));

        schoolNameTextView.setText("Welcome to\nSoftEdge Nepal");
        addressTextView.setText("Singhadurbar East Gate, Anamnagar, Kathmandu, Nepal");
        phoneNoTextView.setText("+977-9841729323\n+977-9801029323");
        faxTextView.setText("info@softedgenepal.com");
        emailTextView.setText("info@softedgenepal.com");
        webTextView.setText("https://softedgenepal.com");
    }

    private void casting() {
        schoolLogoImageView = view.findViewById(R.id.schoolLogo_imageView);
        schoolNameTextView = view.findViewById(R.id.schoolName_TextView);
        addressTextView = view.findViewById(R.id.schoolAddress_TextView);
        phoneNoTextView = view.findViewById(R.id.schoolPhoneNo_TextView);
        faxTextView = view.findViewById(R.id.schoolFax_TextView);
        emailTextView = view.findViewById(R.id.schoolEmail_TextView);
        webTextView = view.findViewById(R.id.schoolWeb_TextView);
    }
}
