package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;

public class StudentDataStore {
    public static class Profile{
        public static void store(Context context,String id, StudentProfileModel data){
            PreferencesForObject.store(context, Constants.ProfileStudent + id, Constants.ProfileStudent, data);
        }

        public static Object get(Context context, String id, Class<?> type){
            return PreferencesForObject.get(context, Constants.ProfileStudent + id, Constants.ProfileStudent, "null", type);
        }
    }
}
