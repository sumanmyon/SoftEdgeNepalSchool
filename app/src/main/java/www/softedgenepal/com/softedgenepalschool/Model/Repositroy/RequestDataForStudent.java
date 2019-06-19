package www.softedgenepal.com.softedgenepalschool.Model.Repositroy;

import android.content.Context;

import org.json.JSONObject;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline.FetchDataOffline;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.FetchDataOnline;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.StudentParseJson;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.Contractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;


public class RequestDataForStudent implements Contractor.Model {
    StudentHomePagePresenter presenter;
    FetchDataOnline fetchDataOnline;
    FetchDataOffline fetchDataOffline;
    StudentParseJson parseJson;
    public RequestDataForStudent(StudentHomePagePresenter studentHomePagePresenter) {
        this.presenter = studentHomePagePresenter;
    }

    public void requestData() {
        if(new NetworkConnection(presenter.getContext()).isConnectionSuccess()){
            setMessage("online");
            fetchOnline();
        }else {
            setMessage("offline");
            fetchOffline();
        }
    }

    private void fetchOnline() {
        fetchDataOnline = new FetchDataOnline(this);
        fetchDataOnline.getJson();

    }

    private void fetchOffline() {
        fetchDataOffline = new FetchDataOffline(this);
        fetchDataOffline.getFromLocalStore();
    }


    @Override
    public void setMessage(String message) {
        presenter.handleMessage(message);
    }

    public void parseJson(JSONObject request) {
        parseJson = new StudentParseJson(this,request);
        parseJson.parse(presenter);

        fetchDataOnline.saveforOffline(parseJson.getCache());
    }

    public void studentData(Cache cache){
        presenter.userDataList(cache);
    }


    public Context getContext() {
        return presenter.getContext();
    }


    public void handleMessage(String message) {
        presenter.handleMessage(message);
    }
}
