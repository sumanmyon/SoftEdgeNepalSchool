package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import www.softedgenepal.com.softedgenepalschool.R;

public class LeaveApplicationActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        //casting
        casting();

        //toolbar
        setToolBar();
    }

    private void setToolBar() {
        toolbar.setTitle("Leave Application");
    }

    private void casting() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
}
