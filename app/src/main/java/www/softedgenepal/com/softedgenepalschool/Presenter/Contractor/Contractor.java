package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;

public interface Contractor {
    interface View{
        void setMessage(String message);
    }
    interface Presenter{
        void handleMessage(String message);
        Context getContext();
        void userDataList(Cache cache);
    }
    interface Model{
        void requestData();
        void setMessage(String message);
    }
}
