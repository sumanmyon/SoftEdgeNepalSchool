package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;

public interface CalenderContractor {
    interface View{
        void setMessage(String message);
        Context getCalContext();
        void setData(List<CalenderCache> calenderCacheList);
    }

    interface Presenter{
        void setMessage(String message);
        Context getCalContext();
        void setData(List<CalenderCache> calenderCacheList);
    }

    interface Model{
        void setMessage(String message);
    }
}
