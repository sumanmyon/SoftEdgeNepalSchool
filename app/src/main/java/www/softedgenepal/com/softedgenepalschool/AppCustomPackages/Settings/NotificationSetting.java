package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;

public class NotificationSetting {
    public static void setNotification(Context context, String type){
        //todo store in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.NotificationSetting);
        preference.storeData(type);
    }

    public static String getNotification(Context context){
        //todo get in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.NotificationSetting);
        String type = preference.getData();
        return type;
    }
}
