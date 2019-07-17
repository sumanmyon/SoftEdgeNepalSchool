package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Assignment.AssignmentModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.HomeWorkActivity;

public class AssignmentPresenter implements AssignmentContractor.Presenter {
    private HomeWorkActivity homeWorkActivity;
    private AssignmentModel model;

    public AssignmentPresenter(HomeWorkActivity homeWorkActivity) {
        this.homeWorkActivity = homeWorkActivity;
    }

    public void mapToModel(){
        model = new AssignmentModel(this);
    }

    public void fetchData(Map<String, String> params) {
        model.fetchDataFromServer(params);
    }

    @Override
    public void setMessage(String message) {
        homeWorkActivity.setMessage(message);
    }

    @Override
    public Context getCalContext() {
        return homeWorkActivity.getCalContext();
    }

    @Override
    public void setData(List<AssignmentCache> assignmentCacheList) {
        homeWorkActivity.setData(assignmentCacheList);
    }

    @Override
    public void setJsonData(JSONObject response) {
        homeWorkActivity.setJsonData(response);
    }
}
