package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;

public class ReportCardSetting {

    public static void setCardFormate(Context context, String type){
        //todo store in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.ReportCardSetting);
        preference.storeData(type);
    }

    public static String getCardFormate(Context context){
        //todo get in Sharepref
        StoreInSharePreference preference = new StoreInSharePreference(context);
        preference.setType(preference.ReportCardSetting);
        String type = preference.getData();
        return type;
    }
}
