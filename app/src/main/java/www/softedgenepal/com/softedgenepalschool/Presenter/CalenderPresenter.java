package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Calender.CalenderModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.CalenderContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.EventActivity;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.Calendar;

public class CalenderPresenter implements CalenderContractor.Presenter {
    private Calendar calendar = null;
    private EventActivity eventActivity = null;
    private CalenderModel model;

    public CalenderPresenter(Calendar calendar) {
        this.calendar=calendar;
    }

    public CalenderPresenter(EventActivity eventActivity) {
        this.eventActivity=eventActivity;
    }

    public void mapModel(){
        model = new CalenderModel(this);
    }

    public void fetchData(Map<String, String> params) {
        model.fetchDataFromServer(params);
    }

    @Override
    public void setMessage(String message) {
        if(calendar!=null)
            calendar.setMessage(message);
        else if (eventActivity!=null)
            eventActivity.setMessage(message);
    }

    @Override
    public Context getCalContext() {
        if(calendar!=null)
            return calendar.getCalContext();
        else if (eventActivity!=null)
            return eventActivity.getCalContext();

        return null;
    }

    @Override
    public void setData(List<CalenderCache> calenderCacheList) {
        if(calendar!=null)
            calendar.setData(calenderCacheList);
        else if(eventActivity!=null)
            eventActivity.setData(calenderCacheList);
    }
}
