package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ImageUrlFormater;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.ProfileTeacherModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomViews.SetProfile;

public class TeacherProfileActivity extends CustomAppCompatActivity {
    private TextView userNameTextView;
    private CircleImageView userImageView;
    private ImageView citizenImageView;
    private View cardCitizenView;

    private Bundle bundle;
    private ProfileTeacherModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        casting();
        toolBarTitle.setText(getString(R.string.TeacherProfile));

        //get data from intent
        bundle = getIntent().getExtras();
        model = (ProfileTeacherModel) bundle.getSerializable("profileModel");
    }

    @Override
    protected void onStart() {
        super.onStart();

        populateInView();
    }

    private void populateInView() {
        if (model != null) {
            userNameTextView.setText(model.FullName);

            //todo show image and store image for offline
            ShowInGlide glide = new ShowInGlide(this);
            glide.loadURL(new ImageUrlFormater().conver(model.ImageUrl));
            glide.loadFailed(R.drawable.userprofile4);
            glide.show(userImageView);

            //todo inflating ui for Student profile
            teacherProfile(getString(R.string.personal_detail));
            teacherParent();
            more();

            //todo for citizen image
            Log.d("citizenimage", model.CitizenShipUrl);
            if(!model.CitizenShipUrl.equals("")) {
                cardCitizenView.setVisibility(View.VISIBLE);
                ShowInGlide glide1 = new ShowInGlide(this);
                glide1.loadURL(new ImageUrlFormater().conver(model.CitizenShipUrl));
                glide1.loadFailed(R.drawable.userprofile4);
                glide1.show(citizenImageView);
            }
        }
    }

    private void teacherProfile(String title) {
        final String[] keys = {
                "Position",
                "Department",
                "Staff Category",
                "Teaching Type",
                "Date of Birth",
                "Gender",
                "Pernament Address",
                "Temporary Address",
                "Mobile no.",
                "Email",
                "Caste",
                "Religion",
                "Reservation Type",
                "Blood Group",
                "Marital Status"
        };

        final List<String> value = new ArrayList<>();
        value.add(model.Position);
        value.add(model.Department);
        value.add(model.StaffCategory);
        value.add(model.TeachingType);
        value.add(DateTime.DateConvertToNepali(model.DOBAD , "date"));
        value.add(model.Gender);
        value.add(model.PernamentAddress);
        value.add(model.TemporaryAddress);
        value.add(model.Mobile);
        value.add(model.EmailAddress);
        value.add(model.Caste);
        value.add(model.Religion);
        value.add(model.ReservationType);
        value.add(model.BloodGroupId);
        value.add(model.MaritalStatus);

        SetProfile setProfile = new SetProfile(this, title, keys, value);
        setProfile.start();
    }

    private void teacherParent() {
        String title = getString(R.string.parent_detail);

        final String[] keys = {
                "Father Name",
                "Mother Name",
                "Grand Father Name",
                "Telephone no."
        };

        final List<String> values = new ArrayList<>();
        values.add(model.FatherName);
        values.add(model.MotherName);
        values.add(model.GrandFatherName);
        values.add(model.TelephoneNo);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    private void more() {
        String title = "More";

        final String[] keys = {
                "Join Date",
                "Citizenship No",
                "PAN Number",
                "CIT Number",
                "PF Number"
        };

        final List<String> values = new ArrayList<>();
        values.add(DateTime.DateConvertToNepali(model.JoinDate , "date"));
        values.add(model.CitizenshipNo);
        values.add(model.PANNumber);
        values.add(model.CITNumber);
        values.add(model.PFNumber);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    @Override
    protected void casting() {
        super.casting();

        userNameTextView = findViewById(R.id.profile_detail_username);
        userImageView = findViewById(R.id.profile_detail_ImageView);
        citizenImageView = findViewById(R.id.citizenImageView);
        cardCitizenView = findViewById(R.id.cardCitizenView);
    }
}
