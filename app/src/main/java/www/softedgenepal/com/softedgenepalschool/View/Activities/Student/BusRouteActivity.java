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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.BusRouteCache;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.MapBoxPresenter;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.ShowInMapActivity;

public class BusRouteActivity extends AppCompatActivity implements IContractor.View {
    private TextView loadTextView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private MapBoxPresenter presenter;
    private LanguageSettingv2 languageSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageSetting = new LanguageSettingv2(this);
        languageSetting.loadLanguage();
        setContentView(R.layout.activity_bus_route);

        //casting
        casting();

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
        Log.d(topic, body);
    }

    @Override
    public void getJsonData() {
        presenter.getJsonData();
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }


    @Override
    public Context getCalContext() {
        return this;
    }

    @Override
    public void setJsonData(JSONObject response) {
        setFieldInvisible();

        if(response != null) {
            //setLog("BusRouteActivity", response.toString());
            List<BusRouteCache> busRouteCaches = new ArrayList<>();
            try {
                if (response.getString("Status").equals("true")) {
                    if (response.getString("Response").equals("Success")) {
                        JSONArray dataArray = response.getJSONArray("Data");
                        if (!dataArray.toString().equals("[]")) {
                            if (dataArray.length() >= 0) {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataObject = dataArray.getJSONObject(i);
                                    String SystemCode = dataObject.getString("SystemCode");
                                    String StaffCode = dataObject.getString("StaffCode");
                                    String FullName = dataObject.getString("FullName");
                                    String RouteName = dataObject.getString("RouteName");

                                    JSONArray routeDetailsArray = dataObject.getJSONArray("RouteDetails");
                                    List<BusRouteCache.RouteDetails> routeDetailsList = new ArrayList<>();
                                    for (int j = 0; j < routeDetailsArray.length(); j++) {
                                        if (!routeDetailsArray.toString().equals("[]")) {
                                            JSONObject routeDetailsObject = routeDetailsArray.getJSONObject(j);
                                            String StationName = routeDetailsObject.getString("StationName");
                                            String Latitude = routeDetailsObject.getString("Latitude");
                                            String Longitude = routeDetailsObject.getString("Longitude");
                                            String Order = routeDetailsObject.getString("Order");
                                            routeDetailsList.add(new BusRouteCache.RouteDetails(StationName, Latitude, Longitude, Order));
                                        }
                                    }

                                    busRouteCaches.add(new BusRouteCache(SystemCode, StaffCode, FullName, RouteName, routeDetailsList));

                                }
                            }
                        }
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

    private void showInView(final List<BusRouteCache> busRouteCachesList){
        saveBusRouteCachesList = busRouteCachesList;
        setLog("BusRouteActivity", saveBusRouteCachesList.size()+"");
        redirect(saveBusRouteCachesList.get(0), ShowInMapActivity.class);
//        final RecyclerAdapter adapter = new RecyclerAdapter(this, saveBusRouteCachesList.size()) {
//            TextView routeNameTextView;
//            @Override
//            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_route_recyclerview_list, viewGroup,false);
//                return new ViewHolder(view);
//            }
//
//            @Override
//            public void inflateUIFields(View itemView) {
//                routeNameTextView = itemView.findViewById(R.id.busRoute_RouteName);
//            }
//
//            @Override
//            public void onBind(ViewHolder viewHolder, int position) {
//                final BusRouteCache busRouteCache = saveBusRouteCachesList.get(position);
//                routeNameTextView.setText(busRouteCache.RouteName);
//
//                viewHolder.itemView.findViewById(R.id.bck).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setLog("BusRouteActivity", busRouteCache.RouteDetailsList.size()+"");
//                        setMessage("Total Route points "+busRouteCache.RouteDetailsList.size());
//                        redirect(busRouteCache, ShowInMapActivity.class);
//                    }
//                });
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
    }

    private void redirect(BusRouteCache busRouteCache, Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        intent.putExtra("GetBusRoutes", busRouteCache);
        startActivity(intent);
        finish();
    }

    List<BusRouteCache> saveBusRouteCachesList;
    @Override
    protected void onRestart() {
        super.onRestart();
        showInView(saveBusRouteCachesList);
    }
}
