package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.R;

public class ReportCardActivity extends AppCompatActivity implements IContractor.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card2);

    }

    @Override
    public void casting() {

    }

    @Override
    public Context getCalContext() {
        return null;
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void setLog(String topic, String body) {

    }

    @Override
    public void getJsonData() {

    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public void setJsonData(JSONObject response) {

    }
}
