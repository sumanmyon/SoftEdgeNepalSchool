package www.softedgenepal.com.softedgenepalschool.View.Activities.Student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.BusRouteCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.MapBoxPresenter;
import www.softedgenepal.com.softedgenepalschool.Presenter.RoutinePresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SiblingActivity;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class BusRouteActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private MapBoxPresenter presenter;
    private LanguageSettingv2 languageSetting;

    private String StudentId;
    private String Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();
        setContentView(R.layout.activity_bus_route);

        //casting
        casting();

        Bundle bundle = getIntent().getExtras();
        String registrationNo = null;
        if (bundle != null) {
            registrationNo = bundle.getString("registrationNo");
        }
        StudentDataCache studentDataList = StudentHomePage.studentProfileModellist.StudentDetail;
        if (studentDataList != null) {
            if (registrationNo.equals(user.Id)) {
                StudentId = user.Id;
            } else {
                StudentProfileModel sibModel = SiblingActivity.siblingProfileModel;
                if (registrationNo.equals(sibModel.StudentDetail.RegistrationNo)) {
                    StudentId = sibModel.StudentDetail.RegistrationNo;
                }
            }

            Role = user.Role;
        }

        presenter = new MapBoxPresenter(this);
        getJsonData();
    }


    private void setFieldVisible(){
        progressBar.setVisibility(View.VISIBLE);
        loadTextView.setVisibility(View.VISIBLE);
    }

    private void setFieldInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
        loadTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void casting() {
        progressBar = findViewById(R.id.busRoute_progressbar);
        loadTextView = findViewById(R.id.busRoute_loading);

        recyclerView = findViewById(R.id.busRoute_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLog(String topic, String body) {
        Log.e(topic, body);
    }

    @Override
    public void getJsonData() {
        presenter.getJsonData(StudentId);
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("id", StudentId);
        params.put("role", Role);
        return params;
    }


    @Override
    public Context getCalContext() {
        return this;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();

        if(response != null) {
            BusRouteCache busRouteCaches = null;
            try {
                if (response.getString("Status").equals("true")) {
                    if (response.getString("Response").equals("Success")) {
                        String data = response.getString("Data");
                        if(data == "null"){
                            toast("There is no bus route for you.");
                        }else {
                            JSONObject dataObject = response.getJSONObject("Data");
                            busRouteCaches = new Gson().fromJson(dataObject.toString(), new TypeToken<BusRouteCache>() {
                            }.getType());
                        }
                    }else{
                        toast("There is no bus route for you.");
                    }
                }

                //todo show in view
                showInView(busRouteCaches);
            } catch (Exception e) {
                setLog("BusRouteActivity", e.getMessage());
            }
        }else {
           loadTextView.setVisibility(View.VISIBLE);
           loadTextView.setText(getString(R.string.BusRoute_online));
        }
    }

    private void showInView(final BusRouteCache busRouteCachesList){
        saveBusRouteCachesList = busRouteCachesList;
        setLog("BusRouteActivity", saveBusRouteCachesList+"");
        redirect(saveBusRouteCachesList, ShowInMapActivity.class);
    }

    private void redirect(BusRouteCache busRouteCache, Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        intent.putExtra("GetBusRoutes", busRouteCache);
        startActivity(intent);
        finish();
    }

    BusRouteCache saveBusRouteCachesList;
    @Override
    protected void onRestart() {
        super.onRestart();
        showInView(saveBusRouteCachesList);
    }

    private void toast(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}
