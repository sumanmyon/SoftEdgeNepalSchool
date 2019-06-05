package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

public interface Contractor {
    interface View{
        void setMessage(String message);
    }
    interface Presenter{
        void handleMessage(String message);
        Context getContext();
    }
    interface Model{
        void requestData();
        void setMessage(String message);
    }
}
