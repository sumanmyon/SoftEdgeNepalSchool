package www.softedgenepal.com.softedgenepalschool.Model.Repositroy;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;

public class RequestDataForStudent implements Contractor.Model {
    StudentHomePagePresenter presenter;
    public RequestDataForStudent(StudentHomePagePresenter studentHomePagePresenter) {
        this.presenter = studentHomePagePresenter;
    }

    public void requestData() {
        if(new NetworkConnection(presenter.getContext()).isConnectionSuccess()){
            setMessage("online");
            fetchOnline();
        }else {
            setMessage("offline");
        }
    }

    private void fetchOnline() {

    }

    @Override
    public void setMessage(String message) {
        presenter.handleMessage(message);
    }
}
