package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import www.softedgenepal.com.softedgenepalschool.R;

public class SettingActivity extends AppCompatActivity {
    private View backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //casting
        casting();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void casting() {
        backpress = findViewById(R.id.setting_bt_close);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
