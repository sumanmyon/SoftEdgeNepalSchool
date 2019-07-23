package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import www.softedgenepal.com.softedgenepalschool.R;

public class SuggestionActivity extends AppCompatActivity {
    private View backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        
        //casting
        casting();

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void casting() {
        //toolbar = findViewById(R.id.suggestion_toolbar);
        backpress = findViewById(R.id.suggest_bt_close);
    }
}
