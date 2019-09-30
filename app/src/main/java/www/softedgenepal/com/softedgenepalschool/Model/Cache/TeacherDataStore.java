package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.HomeWorkModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.ProfileTeacherModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel2;

public class TeacherDataStore {
    //Type for list<?> model list
    //Class<?> for model

    public static class FacultyClassDetail {
        public static void store(Context context, List<FacultyClassDetailModel> data) {
            PreferencesForObject.store(context, Constants.FacultyClassDetail, Constants.FacultyClassDetail, data);
        }

        public static Object get(Context context, Type type) {
            return PreferencesForObject.get(context, Constants.FacultyClassDetail, Constants.FacultyClassDetail, "null", type);
        }
    }

    public static class HomeWorkDetail{
        public static void store(Context context, List<HomeWorkModel> data){
            PreferencesForObject.store(context, Constants.ShowHomeWorkTeacher, Constants.ShowHomeWorkTeacher, data);
        }

        public static Object get(Context context, Type type){
            return PreferencesForObject.get(context, Constants.ShowHomeWorkTeacher, Constants.ShowHomeWorkTeacher, "null", type);
        }
    }

    public static class GetLeaveType{
        public static void store(Context context, List<LeaveTypeModel> data){
            PreferencesForObject.store(context, Constants.GetLeaveTypeTeacher, Constants.GetLeaveTypeTeacher, data);
        }

        public static Object get(Context context,Type type){
            return PreferencesForObject.get(context, Constants.GetLeaveTypeTeacher, Constants.GetLeaveTypeTeacher, "null", type);
        }
    }

    public static class ShowLeaveApplication{
        public static void store(Context context, List<LeaveApplicationModel> data){
            PreferencesForObject.store(context, Constants.ShowStaffLeaveApplication, Constants.ShowStaffLeaveApplication, data);
        }

        public static Object get(Context context, Type type){
            return PreferencesForObject.get(context, Constants.ShowStaffLeaveApplication, Constants.ShowStaffLeaveApplication, "null", type);
        }
    }

    public static class Profile{
        public static void store(Context context, ProfileTeacherModel data){
            PreferencesForObject.store(context, Constants.ProfileTeacher, Constants.ProfileTeacher, data);
        }

        public static Object get(Context context, Class<?> type){
            return PreferencesForObject.get(context, Constants.ProfileTeacher, Constants.ProfileTeacher, "null", type);
        }
    }

    public static class ClassRoutine{
        public static void store(Context context, List<TeacherClassRoutineModel2> data){
            PreferencesForObject.store(context, Constants.ClassRoutineTeacher, Constants.ClassRoutineTeacher, data);
        }

        public static Object get(Context context, Type type){
            return PreferencesForObject.get(context, Constants.ClassRoutineTeacher, Constants.ClassRoutineTeacher, "null", type);
        }
    }
}
