package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication.CreateLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication.GetAllUserLeaveApplication;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.LeaveApplicationContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication.LeaveApplicationActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LeaveApplication.ShowAllLeaveApplication;

public class LeaveApplicationPresenter implements LeaveApplicationContractor.Presenter,
        LeaveApplicationContractor.Presenter.Create,
        LeaveApplicationContractor.Presenter.Get {

    private LeaveApplicationActivity leaveApplicationActivity;
    private ShowAllLeaveApplication showAllLeaveApplication;

    public LeaveApplicationPresenter(LeaveApplicationActivity leaveApplicationActivity) {
        this.leaveApplicationActivity=leaveApplicationActivity;
    }

    public LeaveApplicationPresenter(ShowAllLeaveApplication showAllLeaveApplication) {
        this.showAllLeaveApplication=showAllLeaveApplication;
    }

    public void createLeaveApplication(List<String> data){
        if(leaveApplicationActivity!=null) {
            CreateLeaveApplication createLeaveApplication = new CreateLeaveApplication(this);
            createLeaveApplication.postUploadData(data);
        }else {
            setMessage("null");
        }
    }

    public void getAllLeaveApplication(Map<String, String> params, String StudentId){
        if(showAllLeaveApplication!=null) {
            GetAllUserLeaveApplication getAllUserLeaveApplication = new GetAllUserLeaveApplication(this);
            getAllUserLeaveApplication.getAllUserLeaveData(params, StudentId);
        }else {
            setMessage("null");
        }
    }

    @Override
    public void setMessage(String message) {
        if(leaveApplicationActivity!=null)
            leaveApplicationActivity.setMessage(message);
        else if(showAllLeaveApplication!=null)
            showAllLeaveApplication.setMessage(message);
    }

    @Override
    public Context getContext() {
        if(leaveApplicationActivity!=null)
            return leaveApplicationActivity.getActivity();
        else if(showAllLeaveApplication!=null)
            return showAllLeaveApplication.getActivity();
        return null;
    }

    @Override
    public void getAllUserData() {

    }

    @Override
    public void getProgressBarVisibility() {
        showAllLeaveApplication.setProgressBarVisibility();
    }

    @Override
    public void getProgressBarInVisibility() {
        showAllLeaveApplication.setProgressBarInVisibility();
    }

    @Override
    public void setMessageInTextView(String messageInTextView) {
        showAllLeaveApplication.setMessageInTextView(messageInTextView);
    }

    @Override
    public void setAllLeaveApplication(List<LeaveApplicationDataCache> leaveApplicationDataCacheList) {
        showAllLeaveApplication.setAllLeaveApplication(leaveApplicationDataCacheList);
    }

    @Override
    public void createProgressBarVisibility() {
        leaveApplicationActivity.setProgressBarVisibility();
    }

    @Override
    public void createProgressBarInVisibility() {
        leaveApplicationActivity.setProgressBarInVisibility();
    }

    @Override
    public void refresh() {
        leaveApplicationActivity.onBackPressed();
    }
}
