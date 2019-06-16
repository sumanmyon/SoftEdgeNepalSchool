package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import www.softedgenepal.com.softedgenepalschool.R;

public class SuggestionActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        
        //casting
        casting();
        
        //toolbar
        setToolbar();

        //
    }

    private void setToolbar() {
        toolbar.setTitle("Suggestion");
    }

    private void casting() {
        toolbar = findViewById(R.id.suggestion_toolbar);
    }
}
