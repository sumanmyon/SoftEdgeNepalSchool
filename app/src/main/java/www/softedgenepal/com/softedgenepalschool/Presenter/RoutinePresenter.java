package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Routine.RoutineModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.RoutineActivity;

public class RoutinePresenter implements IContractor.Presenter {
    private RoutineActivity routineActivity;
    private RoutineModel routineModel;

    public RoutinePresenter(RoutineActivity routineActivity) {
        this.routineActivity = routineActivity;
        routineModel = new RoutineModel(this);
    }

    @Override
    public void setMessage(String message) {
        routineActivity.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        routineActivity.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        return routineActivity.getCalContext();
    }

    @Override
    public void getJsonData() {
        routineModel.getJsonData();
    }

    @Override
    public Map<String, String> getParams() {
        return routineActivity.getParams();
    }

    @Override
    public void setJsonData(JSONObject response) {
        routineActivity.setJsonData(response);
    }
}
