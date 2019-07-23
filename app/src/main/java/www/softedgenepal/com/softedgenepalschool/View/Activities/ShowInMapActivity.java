package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.ColorUtils;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.BusRouteCache;
import www.softedgenepal.com.softedgenepalschool.R;

import static com.mapbox.core.constants.Constants.PRECISION_5;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineTranslate;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class ShowInMapActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {
    private Toolbar toolbar;
    private MapView mapView;
    private MapboxMap mapBoxMap;
    private String setStyle = Style.MAPBOX_STREETS;

    private BusRouteCache busRouteCache;
    private String token = "pk.eyJ1Ijoic3VtYW5teW9uIiwiYSI6ImNqdjYyMnN6dzAwbnU0ZG1qZjl2NGMzb2cifQ.16smYhpSH7Tq47o78vP5RQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this,getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_show_in_map);

        //bundle
        busRouteCache = (BusRouteCache) getIntent().getSerializableExtra("GetBusRoutes");

        //casting
        casting();

        //For mapBox
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }



    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapBoxMap = mapboxMap;

        //todo remove mapbox label (i) icon from map
        mapboxMap.getUiSettings().setAttributionEnabled(false);
        mapboxMap.getUiSettings().setLogoEnabled(false);

        enableLocation();
    }

    //todo for location permission listener
    private void enableLocation(){
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            //yes
            type1(mapBoxMap);
        }else {
            //no
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //present toast or dialogue
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void type1(MapboxMap mapboxMap){

        List<BusRouteCache.RouteDetails> routeDetails = busRouteCache.RouteDetailsList;
        List<LatLng> latLngList = new ArrayList<>();
        List<Point> pointList = new ArrayList<>();

        // Create an Icon object for the marker to use
        IconFactory iconFactory = IconFactory.getInstance(this);
        Icon icon = iconFactory.fromResource(R.drawable.ic_location);

        int i=0;
        for (BusRouteCache.RouteDetails details : routeDetails){
            LatLng latLng = new LatLng(Double.parseDouble(details.Latitude), Double.parseDouble(details.Longitude));
            latLngList.add(latLng);
            pointList.add(Point.fromLngLat(Double.parseDouble(details.Longitude), Double.parseDouble(details.Latitude)));

            mapboxMap.addMarker(new MarkerOptions().position(latLng).setTitle(details.StationName).icon(icon));

            setLog("ShowInMapActivity", details.StationName + "\t" + latLng.toString());
        }

        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        mapboxMap.setStyle(setStyle, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapBoxMap.getStyle(styles -> {
                    setLoop(styles, pointList);
                });
            }
        });
    }

    private void setLoop(Style styles, List<Point> pointList){
        if(!new NetworkConnection(this).isConnectionSuccess()){
            setMessage("Come online to show route.");
            return;
        }
        for(int i=0; i<pointList.size(); i++){
            if(i==pointList.size()-1){
                //do nothing
                Log.e("MapboxDirections","last Point "+i);
            }else {
                mapboxDirections(styles, pointList.get(i), pointList.get(i+1), i);
                Log.e("MapboxDirections",i+" Point "+ pointList.get(i)+" "+ pointList.get(i+1));
            }
        }
    }

    private void mapboxDirections(Style styles, Point originPoint, Point destinationPoint, int position){
        MapboxDirections client = MapboxDirections.builder()
                .origin(originPoint)
                .destination(destinationPoint)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                if (response.body() == null) {
                    Log.e("MapboxDirections","No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.e("MapboxDirections","No routes found");
                    return;
                }

                // Retrieve the directions route from the API response
                DirectionsRoute currentRoute = response.body().routes().get(0);
                style6(styles, currentRoute, position);
            }

            @Override public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
            }
        });
    }
    private void style6(Style style, DirectionsRoute pointList, int position) {
        style.addSource(new GeoJsonSource("line-source"+position,  Feature.fromGeometry(LineString.fromPolyline(pointList.geometry(), PRECISION_6))));
        style.addLayer(new LineLayer("linelayer"+position, "line-source"+position).withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineColor(ColorUtils.colorToRgbaString(Color.parseColor("#8a8acb"))),
                lineWidth(2.5f)));
    }

    private void style5(Style style, DirectionsRoute pointList, int position) {
        style.addSource(new GeoJsonSource("rawLine"+position,  Feature.fromGeometry(LineString.fromPolyline(pointList.geometry(), PRECISION_6))));
        style.addLayer(new LineLayer("rawLine"+position, "rawLine"+position).withProperties(
                lineColor(ColorUtils.colorToRgbaString(Color.parseColor("#8a8acb"))),
                lineWidth(2.5f)));
    }

    private PermissionsManager permissionsManager;
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void casting() {
        toolbar = findViewById(R.id.ShowInMap_toolbar);
        toolbar.setTitle(getString(R.string.RouteName)+busRouteCache.RouteName);

        mapView = findViewById(R.id.mapView);
    }

    public void setLog(String topic, String body) {
        Log.d(topic, body);
    }

    public void setMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
