package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

public interface IContractor {
    interface View{
        void casting();
        Context getCalContext();

        void setMessage(String message);
        void setLog(String topic, String body);

        void getJsonData();
        Map<String, String> getParams();
        void setJsonData(JSONObject response);
    }

    interface Presenter{
        void setMessage(String message);
        void setLog(String topic, String body);

        Context getCalContext();

        void getJsonData(String studentId);
        Map<String, String> getParams();
        void setJsonData(JSONObject response);
    }

    interface Model{
        void setMessage(String message);
        void setLog(String topic, String body);

        void getJsonData(String studentId);
        void setJsonData(JSONObject response);
    }
}
