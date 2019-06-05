package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;
import www.softedgenepal.com.softedgenepalschool.R;

public class StudentHomePage implements Contractor.View {
    private Activity activity;
    private View view;

    private Toolbar toolbar;

    private CircleImageView userProfileImage;
    private TextView userNameTextView, classTextView, roll_SubTextView;
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
        //set data

    }

    private void setData(String response, String student, String parent) {
        ShowInGlide glide = new ShowInGlide(activity);
        glide.loadDrawable(R.drawable.userprofile_suman);
        glide.show(userProfileImage);

        userNameTextView.setText("Name: "+response);
        classTextView.setText("Class: "+student);
        roll_SubTextView.setText("Roll No.: "+parent);
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


//    private void data(){
//        StudentApi studentApi = new StudentApi();
//        String response = "", student = "", parent = "";
//        try {
//            JSONObject object = new JSONObject(studentApi.getStudentTrueForGuardianWithSibling());
//            if(object.getString("response").equals("failed")){
//                if(object.getString("data").equals("null")){
//                    response="There is not any data to show.";
//                }
//            }else if(object.getString("response").equals("success")){
//                if(object.getString("data").equals("null")){
//                    response="There is not any data to show.";
//                }else {
//                    response=object.getString("data");
//                    student=object.getString("guardianDetail");
//                    parent=object.getString("siblingDetail");
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


}
