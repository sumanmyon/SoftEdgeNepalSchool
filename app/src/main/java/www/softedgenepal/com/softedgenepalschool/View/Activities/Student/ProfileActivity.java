package www.softedgenepal.com.softedgenepalschool.View.Activities.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomViews.SetProfile;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;

public class ProfileActivity extends AppCompatActivity {
    private StudentProfileModel cache;
    private TextView userNameTextView;
    private CircleImageView userImageView;
    private View backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //casting
        casting();

        //getting cached data
        this.cache = StudentHomePage.studentProfileModellist;
        //cache = (Cache) getIntent().getSerializableExtra("cache");
        if (cache != null) {
            userNameTextView.setText(cache.StudentDetail.StudentName);

            //todo show image and store image for offline
            //Glide.with(userImageView).load(cache.studentDataCaches.get(0).imageUrl).into(userImageView);
            ShowInGlide glide = new ShowInGlide(this);
            glide.loadURL(cache.StudentDetail.ImageUrl);
            glide.loadFailed(R.drawable.userprofile4);
            glide.show(userImageView);

            //todo inflating ui for Student profile
            studentProfile(getString(R.string.personal_detail));
            studentParent();
            studentGuardian();
        }

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void studentProfile(String text) {
        String title = text;

        final String[] keys = new String[]{"RegistrationNo", "Class", "section", "Roll no", "Gender", "Birth Date", "Contact", "Email", "House", "Religion", "Caste", "Address", "Blood Group", "Bus Stop", "Bus Route"};

        final StudentDataCache studentDataCache = cache.StudentDetail;

        final List<String> value = new ArrayList<>();
        value.add(studentDataCache.RegistrationNo);
        value.add(studentDataCache.ClassName);
        value.add(studentDataCache.SectionName);
        value.add(studentDataCache.RollNo);
        value.add(studentDataCache.Gender);

        value.add(studentDataCache.DateofBirth);

        value.add(studentDataCache.Contact);
        value.add(studentDataCache.Email);
        value.add(studentDataCache.House);

        value.add(studentDataCache.Religion);
        value.add(studentDataCache.Caste);
        value.add(studentDataCache.Address);

        value.add(studentDataCache.Blood);
        value.add(studentDataCache.BusStop);
        value.add(studentDataCache.BusRoute);

        SetProfile setProfile = new SetProfile(this, title, keys, value);
        setProfile.start();
    }

    private void studentParent() {
        String title = getString(R.string.parent_detail);

        String[] keys = new String[]{"Father Name", "Father Occupation", "Father Contact", "Mother Name", "Mother Occupation", "Mother Contact"};

        ParentDataCache parentDataCache = cache.ParentDetail;
        List<String> values = new ArrayList<>();
        values.add(parentDataCache.FatherName);
        values.add(parentDataCache.FatherOccupation);
        values.add(parentDataCache.FatherContact);

        values.add(parentDataCache.MotherName);
        values.add(parentDataCache.MotherOccupation);
        values.add(parentDataCache.MotherContact);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    private void studentGuardian() {
        String title = getString(R.string.guardian_deatil);

        String[] keys = new String[]{"Guardian Name", "Guardian Occupation", "Guardian Contact"};

        GuardianDataCache guardianDataCache = cache.GuardianDetail;
        List<String> values = new ArrayList<>();
        values.add(guardianDataCache.GuardianName);
        values.add(guardianDataCache.GuardianOccupation);
        values.add(guardianDataCache.GuardianContact);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    private void casting() {
        backpress = findViewById(R.id.profile_bt_close);
        userNameTextView = findViewById(R.id.profile_detail_username);
        userImageView = findViewById(R.id.profile_detail_ImageView);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
