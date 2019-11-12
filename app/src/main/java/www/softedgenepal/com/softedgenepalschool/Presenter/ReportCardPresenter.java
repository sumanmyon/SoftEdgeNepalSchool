package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.ReportCardDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.ReportCardModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ReportCardActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.ReportCardDetailActivity;

public class ReportCardPresenter implements IContractor.Presenter {
    private ReportCardDetailActivity percentageFragment;
    private ReportCardActivity reportCardActivity;

    private ReportCardDetailModel reportCardDetailModel;
    private ReportCardModel reportCardModel;

    public ReportCardPresenter(ReportCardDetailActivity percentageFragment) {
        this.percentageFragment = percentageFragment;
        reportCardDetailModel = new ReportCardDetailModel(this);
    }

    public ReportCardPresenter(ReportCardActivity reportCardActivity) {
        this.reportCardActivity = reportCardActivity;
        reportCardModel = new ReportCardModel(this);
    }

    @Override
    public void setMessage(String message) {
        percentageFragment.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        if(percentageFragment!=null)
            percentageFragment.setLog(topic, body);
        else if(reportCardActivity != null)
            reportCardActivity.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        if(percentageFragment!=null)
            return percentageFragment.getCalContext();
        else if(reportCardActivity != null)
            return reportCardActivity.getCalContext();
        return null;
    }

    @Override
    public void getJsonData(String studentId) {
        if(percentageFragment!=null)
            reportCardDetailModel.getJsonData(studentId);
        else if(reportCardActivity != null){
            reportCardModel.getJsonData(studentId);
        }
    }

    @Override
    public Map<String, String> getParams() {
        if(percentageFragment!=null)
            return percentageFragment.getParams();
        else if(reportCardActivity != null){
            return reportCardActivity.getParams();
        }
       return null;
    }

    @Override
    public void setJsonData(JSONObject response) {
        if(percentageFragment!=null)
            percentageFragment.setJsonData(response);
        else if(reportCardActivity != null){
            reportCardActivity.setJsonData(response);
        }
    }
}
