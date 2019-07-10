package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomViews.SetProfile;

public class ProfileActivity extends AppCompatActivity {
    private Cache cache;
    private TextView userNameTextView;
    private CircleImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //casting
        casting();

        //getting cached data
        cache = (Cache) getIntent().getSerializableExtra("cache");
        if(cache != null) {
            userNameTextView.setText(cache.studentDataCaches.get(0).username);

            //todo show image and store image for offline
            //Glide.with(userImageView).load(cache.studentDataCaches.get(0).imageUrl).into(userImageView);
            ShowInGlide glide = new ShowInGlide(this);
            glide.loadURL(cache.studentDataCaches.get(0).imageUrl);
            glide.loadFailed(R.drawable.userprofile);
            glide.show(userImageView);

            //todo inflating ui for Student profile
            studentProfile("Personal Detail");
            if(cache.isParent.equals("true"))
                studentParent();
            if(cache.isGuardian.equals("true"))
                studentGuardian();
        }
    }

    private void studentProfile(String text) {
        String title = text;

        final String[] keys = new String[]{
                "Class","section","Roll no","Gender",
                "CalenderDate Of Birth (BS)", "CalenderDate Of Birth (AD)",
                "Contact", "Email", "House",
                "Religion", "Caste", "Address",
                "Blood Group", "Bus Stop", "Bus Route"
        };

        final StudentDataCache studentDataCache = cache.studentDataCaches.get(0);

        final List<String> value = new ArrayList<>();
        value.add(studentDataCache.userclass);
        value.add(studentDataCache.section);
        value.add(studentDataCache.rollno);
        value.add(studentDataCache.gender);

        value.add(studentDataCache.dateOfBirthBS);
        value.add(studentDataCache.dateOfBirthAD);

        value.add(studentDataCache.contact);
        value.add(studentDataCache.email);
        value.add(studentDataCache.house);

        value.add(studentDataCache.religion);
        value.add(studentDataCache.caste);
        value.add(studentDataCache.address);

        value.add(studentDataCache.bloodGroup);
        value.add(studentDataCache.busStop);
        value.add(studentDataCache.busRoute);

        SetProfile setProfile = new SetProfile(this,title, keys, value);
        setProfile.start();
    }

    private void studentParent() {
        String title = "Parent Detail";

        String[] keys = new String[]{
                "Father Name", "Father Occupation", "Father Contact",
                "Mother Name", "Mother Occupation", "Mother Contact"
        };

        ParentDataCache parentDataCache = cache.parentDataCaches.get(0);
        List<String> values = new ArrayList<>();
        values.add(parentDataCache.fatherName);
        values.add(parentDataCache.fatherOccupation);
        values.add(parentDataCache.fatherContact);

        values.add(parentDataCache.motherName);
        values.add(parentDataCache.motherOccupation);
        values.add(parentDataCache.motherContact);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    private void studentGuardian() {
        String title = "Guardian Detail";

        String[] keys = new String[]{
                "Guardian Name", "Guardian Occupation", "Guardian Contact"
        };

        GuardianDataCache guardianDataCache = cache.guardianDataCaches.get(0);
        List<String> values = new ArrayList<>();
        values.add(guardianDataCache.guardianName);
        values.add(guardianDataCache.guardianOccupation);
        values.add(guardianDataCache.guardianContact);

        SetProfile setProfile = new SetProfile(this, title, keys, values);
        setProfile.start();
    }

    private void studentSibling(String text) {
        List<StudentDataCache> siblingDataCache = cache.siblingDataCaches;
        for(int i=0; i<siblingDataCache.size(); i++){

        }
    }

    private void casting() {
        userNameTextView = findViewById(R.id.profile_detail_username);
        userImageView = findViewById(R.id.profile_detail_ImageView);
    }

    private void showMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

}
