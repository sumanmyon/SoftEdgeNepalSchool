package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;

public interface AssignmentContractor {
    interface View{
        void setMessage(String message);
        Context getCalContext();
        //void setData(List<AssignmentCache> assignmentCacheList);
        void setJsonData(JSONObject response);
    }

    interface Presenter{
        void setMessage(String message);
        Context getCalContext();
        void setData(List<AssignmentCache> assignmentCacheList);
        void setJsonData(JSONObject response);
    }

    interface Model{
        void setMessage(String message);
    }
}
