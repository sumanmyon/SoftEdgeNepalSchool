package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication.ParseShowAllLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication.ShowLeaveApplicationOnline;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.LeaveApplicationPresenter;

public class GetAllUserLeaveApplication implements LeaveApplicationContractor.Model, LeaveApplicationContractor.Model.Get {
    public LeaveApplicationPresenter leaveApplicationPresenter;
    private Context context;
    private ShowLeaveApplicationOnline online;

    public GetAllUserLeaveApplication(LeaveApplicationPresenter leaveApplicationPresenter) {
        this.leaveApplicationPresenter=leaveApplicationPresenter;
        this.context = leaveApplicationPresenter.getContext();
    }

    @Override
    public void getAllUserLeaveData(Map<String, String> params) {
        if(new NetworkConnection(context).isConnectionSuccess()){
            online = new ShowLeaveApplicationOnline(this,context);
            online.get(params);
        }else {
            getProgressBarInVisibility();
            setMessageInTextView("It seems you are offline. Please check your network to load.");
        }
    }

    public void parseData(JSONObject response) {
        ParseShowAllLeaveApplication leaveApplication = new ParseShowAllLeaveApplication(this,context);
        leaveApplication.parse(response);
    }

    public void setData(List<LeaveApplicationDataCache> leaveApplicationDataCacheList){
        if(leaveApplicationDataCacheList.size()>=0){
            leaveApplicationPresenter.setAllLeaveApplication(leaveApplicationDataCacheList);
        }
    }

    @Override
    public void setMessageInTextView(String message) {
        leaveApplicationPresenter.setMessageInTextView(message);
    }

    @Override
    public void setMessage(String message) {
        leaveApplicationPresenter.setMessage(message);
    }

    private void getProgressBarInVisibility(){
        leaveApplicationPresenter.getProgressBarInVisibility();
    }
}
