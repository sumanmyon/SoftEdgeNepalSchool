package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Calender.CalenderModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.CalenderContractor;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Calendar;

public class CalenderPresenter implements CalenderContractor.Presenter {
    private Calendar calendar;
    private CalenderModel model;
    public CalenderPresenter(Calendar calendar) {
        this.calendar=calendar;
        model = new CalenderModel(this);
    }

    public void fetchData(Map<String, String> params) {
        model.fetchDataFromServer(params);
    }

    @Override
    public void setMessage(String message) {
        calendar.setMessage(message);
    }

    @Override
    public Context getCalContext() {
        return calendar.getCalContext();
    }

    @Override
    public void setData(List<CalenderCache> calenderCacheList) {
        calendar.setData(calenderCacheList);
    }
}
