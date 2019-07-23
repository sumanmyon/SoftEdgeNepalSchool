package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.Contractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.ProfileActivity;
import www.softedgenepal.com.softedgenepalschool.View.Sibling.SiblingPopUpMenu;

public class StudentHomePage implements Contractor.View {
    private Activity activity;
    private View view;
    ProgressBar progressBar;

    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;
    public static Cache cache;

    public static List<StudentDataCache> studentDataCacheList = null;
    public StudentHomePage(Activity activity, View view) {
        this.activity=activity;
        this.view=view;
    }

    public void setView() {
        //casting
        casting();

        //progress bar
        progressBar.setVisibility(View.VISIBLE);

        //fetch/get data
        StudentHomePagePresenter presenter = new StudentHomePagePresenter(StudentHomePage.this);
        presenter.getData();
    }

    public void userDataList(Cache cache) {
        this.cache = cache;
        //set data
        this.studentDataCacheList = cache.studentDataCaches;
        setData(this.studentDataCacheList.get(0).username,
                this.studentDataCacheList.get(0).userclass+"("+ this.studentDataCacheList.get(0).section+")",
                this.studentDataCacheList.get(0).rollno, this.studentDataCacheList.get(0).imageUrl);
    }

    private void setData(String username, String classSection, String rollno, String imageUrl) {
        progressBar.setVisibility(View.INVISIBLE);

        ShowInGlide glide = new ShowInGlide(activity);
        glide.loadURL(imageUrl);
        glide.loadFailed(R.drawable.userprofile);
        glide.show(userProfileImage);


        userNameTextView.setText("Name: "+username);
        classTextView.setText("Class: "+classSection);
        roll_SubTextView.setText("Roll No.: "+rollno);

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo student user detail page
                Intent profileIntent = new Intent(activity, ProfileActivity.class);
                //profileIntent.putExtra("cache",cache);
                activity.startActivity(profileIntent);
            }
        });
    }

    private void casting() {
        userProfileImage = view.findViewById(R.id.userProfile_ImageView);
        userNameTextView = view.findViewById(R.id.userProfile_nameTextView);
        classTextView = view.findViewById(R.id.userProfile_classTextView);
        roll_SubTextView = view.findViewById(R.id.userProfile_Roll_SubjectTextView);
        progressBar = view.findViewById(R.id.profile_progress_bar);
    }

    public Context getContext(){
        return activity.getApplicationContext();
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    public void siblingMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sibling:
                if(cache != null) {
                    contextMenu();
                }else {
                    showMessage("You have no sibling.");
                }
                break;
        }
    }

    //todo create different class for this
    private void contextMenu(){
        SiblingPopUpMenu popUpMenu = new SiblingPopUpMenu(activity, cache.siblingDataCaches);
    }

    private void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
