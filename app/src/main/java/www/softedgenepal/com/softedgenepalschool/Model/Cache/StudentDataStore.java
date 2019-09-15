package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.User.StudentProfileModel;

public class StudentDataStore {
    public static class Profile{
        public static void store(Context context, StudentProfileModel data){
            PreferencesForObject.store(context, Constants.ProfileStudent, Constants.ProfileStudent, data);
        }

        public static Object get(Context context, Class<?> type){
            return PreferencesForObject.get(context, Constants.ProfileStudent,Constants.ProfileStudent, "null", type);
        }
    }
}
