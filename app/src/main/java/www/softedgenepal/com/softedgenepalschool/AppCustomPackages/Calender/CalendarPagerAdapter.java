package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;

/**
 * Pager adapter for displaying all supported ic_calendar_white months.
 */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    int type;
    List<CalenderCache> calenderCacheList;
    Activity activity;

    public static boolean isActiveCalenderData = true, hasData = false;


    public CalendarPagerAdapter(FragmentManager manager, int type, List<CalenderCache> calenderCacheList, Activity activity) {
        super(manager);
        this.type = type;
        this.calenderCacheList = calenderCacheList;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        int year = position/12 + DateUtils.startNepaliYear;
        int month = position%12 + 1;

        if (type == 0) {
            CalendarFragment cf = new CalendarFragment(calenderCacheList, activity);
            cf.set(year, month);
            return cf;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 91*12;
    }
}
