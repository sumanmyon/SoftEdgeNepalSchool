package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.ReportCardModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.ReportCardDetailActivity;

public class ReportCardPresenter implements IContractor.Presenter {
    private ReportCardDetailActivity percentageFragment;
    private ReportCardModel reportCardModel;

    public ReportCardPresenter(ReportCardDetailActivity percentageFragment) {
        this.percentageFragment = percentageFragment;
        reportCardModel = new ReportCardModel(this);
    }

    @Override
    public void setMessage(String message) {
        percentageFragment.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        percentageFragment.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        return percentageFragment.getCalContext();
    }

    @Override
    public void getJsonData() {
        reportCardModel.getJsonData();
    }

    @Override
    public Map<String, String> getParams() {
        return percentageFragment.getParams();
    }

    @Override
    public void setJsonData(JSONObject response) {
        percentageFragment.setJsonData(response);
    }
}
