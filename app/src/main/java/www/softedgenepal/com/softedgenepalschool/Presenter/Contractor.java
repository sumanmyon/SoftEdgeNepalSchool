package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;

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
