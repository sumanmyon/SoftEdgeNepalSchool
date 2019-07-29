package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.ReportCardModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.ReportCardActivity;

public class ReportCardPresenter implements IContractor.Presenter {
    private ReportCardActivity reportCardActivity;
    private ReportCardModel reportCardModel;

    public ReportCardPresenter(ReportCardActivity reportCardActivity) {
        this.reportCardActivity = reportCardActivity;
        reportCardModel = new ReportCardModel(this);
    }

    @Override
    public void setMessage(String message) {
        reportCardActivity.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        reportCardActivity.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        return reportCardActivity.getCalContext();
    }

    @Override
    public void getJsonData() {
        reportCardModel.getJsonData();
    }

    @Override
    public Map<String, String> getParams() {
        return reportCardActivity.getParams();
    }

    @Override
    public void setJsonData(JSONObject response) {
        reportCardActivity.setJsonData(response);
    }
}
