package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;

public class BaseUrlSetting {
    public static void setUrl(Context context, String type){
        //todo store in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.BaseUrlSetting);
        preference.storeData(type);
    }

    public static String getUrl(Context context){
        //todo get in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.BaseUrlSetting);
        String type = preference.getData();
        return type;
    }
}
