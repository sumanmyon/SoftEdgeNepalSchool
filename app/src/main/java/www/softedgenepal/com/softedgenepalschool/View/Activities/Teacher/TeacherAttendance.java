package www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

public class TeacherAttendance extends CustomAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);

        casting();
        toolBarTitle.setText(R.string.TeacherAttendance);
    }

    @Override
    protected void casting() {
        super.casting();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
