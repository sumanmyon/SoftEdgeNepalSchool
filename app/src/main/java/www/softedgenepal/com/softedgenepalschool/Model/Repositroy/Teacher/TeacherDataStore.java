package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Teacher;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;

public class TeacherDataStore {
    public static class FacultyClassDetail {
        public static void store(Context context, List<FacultyClassDetailModel> data) {
            PreferencesForObject.store(context, Constants.FacultyClassDetail, Constants.FacultyClassDetail, data);
        }

        public static Object get(Context context, Type type) {
            return PreferencesForObject.get(context, Constants.FacultyClassDetail, Constants.FacultyClassDetail, "null", type);
        }
    }
}
