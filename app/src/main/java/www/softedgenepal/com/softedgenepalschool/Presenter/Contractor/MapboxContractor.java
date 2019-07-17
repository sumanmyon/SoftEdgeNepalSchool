package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;

public interface MapboxContractor {
    interface View{
        void casting();
        Context getCalContext();

        void setMessage(String message);
        void setLog(String topic, String body);

        void getJsonData();
        void setJsonData(JSONObject response);
    }

    interface Presenter{
        void setMessage(String message);
        void setLog(String topic, String body);

        Context getCalContext();

        void getJsonData();
        void setJsonData(JSONObject response);
    }

    interface Model{
        void setMessage(String message);
        void setLog(String topic, String body);

        void getJsonData();
        void setJsonData(JSONObject response);
    }
}
