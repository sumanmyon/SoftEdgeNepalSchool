package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.BusRouteAndLiveBusTracking.MapBoxModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.MapboxContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.BusRouteActivity;

public class MapBoxPresenter implements MapboxContractor.Presenter {
    private BusRouteActivity busRouteActivity;
    private MapBoxModel mapBoxModel;

    public MapBoxPresenter(BusRouteActivity busRouteActivity) {
        this.busRouteActivity=busRouteActivity;
        mapBoxModel = new MapBoxModel(this);
    }

    @Override
    public void setMessage(String message) {
        busRouteActivity.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        busRouteActivity.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        return busRouteActivity.getCalContext();
    }

    @Override
    public void getJsonData() {
        mapBoxModel.getJsonData();
    }

    @Override
    public void setJsonData(JSONObject response) {
        busRouteActivity.setJsonData(response);
    }
}
