package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Assignment.AssignmentModel;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Calender.CalenderModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.AssignmentActivity;

public class AssignmentPresenter implements AssignmentContractor.Presenter {
    private AssignmentActivity assignmentActivity;
    private AssignmentModel model;

    public AssignmentPresenter(AssignmentActivity assignmentActivity) {
        this.assignmentActivity = assignmentActivity;
    }

    public void mapToModel(){
        model = new AssignmentModel(this);
    }

    public void fetchData(Map<String, String> params) {
        model.fetchDataFromServer(params);
    }

    @Override
    public void setMessage(String message) {
        assignmentActivity.setMessage(message);
    }

    @Override
    public Context getCalContext() {
        return assignmentActivity.getCalContext();
    }

    @Override
    public void setData(List<AssignmentCache> assignmentCacheList) {
        assignmentActivity.setData(assignmentCacheList);
    }

    @Override
    public void setJsonData(JSONObject response) {
        assignmentActivity.setJsonData(response);
    }
}
