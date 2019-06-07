package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentSiblingHelper;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;
import www.softedgenepal.com.softedgenepalschool.R;



public class StudentHomePage implements Contractor.View {
    private Activity activity;
    private View view;

    private Toolbar toolbar;
    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;

    private List<StudentDataCache> studentDataCacheList;
    public StudentHomePage(Activity activity, View view) {
        this.activity=activity;
        this.view=view;
    }

    public void setView() {
        //casting
        casting();

        //fetch/get data
        StudentHomePagePresenter presenter = new StudentHomePagePresenter(this);
        presenter.getData();
    }

    public void userDataList(Cache cache) {
        //set data
        this.studentDataCacheList = cache.studentDataCaches;
        setData(this.studentDataCacheList.get(0).username,
                this.studentDataCacheList.get(0).userclass+"("+ this.studentDataCacheList.get(0).section+")",
                this.studentDataCacheList.get(0).rollno, this.studentDataCacheList.get(0).imageUrl);
    }

    private void setData(String username, String classSection, String rollno, String imageUrl) {
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
                //setMessage("User Profile Coming Soon");
                //checkingSibling();

                //todo student user detail page

            }
        });
    }

    private void casting() {
        userProfileImage = view.findViewById(R.id.userProfile_ImageView);
        userNameTextView = view.findViewById(R.id.userProfile_nameTextView);
        classTextView = view.findViewById(R.id.userProfile_classTextView);
        roll_SubTextView = view.findViewById(R.id.userProfile_Roll_SubjectTextView);
    }

    public Context getContext(){
        return activity.getApplicationContext();
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

    public void checkingSibling(){
        StudentSiblingHelper siblingHelper = new StudentSiblingHelper(activity);
        Cursor cursor = siblingHelper.showSibling("1");
        //todo if there is no data
        if(cursor.getCount() == 0){
            //requestDataForStudent.setMessage("There is no data in database.");
            return;
        }
        //requestDataForStudent.setMessage("There is data in database.");

        //todo if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

        //todo showing latest updated student data
        //cursor.moveToLast();
        studentDataCacheList = new ArrayList<>();
        int i=0;
        do{
            studentDataCacheList.add(new StudentDataCache(
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13),
                    cursor.getString(14), cursor.getString(15), cursor.getString(16)
            ));
            setMessage(studentDataCacheList.get(i).username);
            i++;
        }while (cursor.moveToNext());
        //requestDataForStudent.studentData(studentDataCacheList);
    }
}
