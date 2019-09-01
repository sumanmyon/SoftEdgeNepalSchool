package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentGuardianHelper;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentParentHelper;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentProfileHelper;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentSiblingHelper;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.RequestDataForStudent;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class FetchDataOffline {
    RequestDataForStudent requestDataForStudent;

    private List<StudentDataCache> studentDataCacheList = null;
    private List<ParentDataCache> parentDataCacheList = null;
    private List<GuardianDataCache> guardianDataCacheList = null;
    private List<StudentDataCache> siblingDataCacheList = null;

    private String uid = user.Id;

    public Cache offlineCache;

    public FetchDataOffline(RequestDataForStudent requestDataForStudent) {
        this.requestDataForStudent=requestDataForStudent;
    }

    public void getFromLocalStore() {
        try {
            //todo for student profile
            StudentProfileHelper dataBase = new StudentProfileHelper(requestDataForStudent.getContext());
            Cursor cursor = dataBase.showProfile(uid);
            if (cursor != null) {
                studentProfile(cursor);
                studentParent();
                studentGuardian();

                //todo for student sibling
                if (cursor.getString(17).equals("true")) studentSibling();

                //todo storing all student related data to offline cache
                cacheOffline(cursor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void studentProfile(Cursor cursor) {
        checkDataBase(cursor);
        //todo showing latest updated student data
        //cursor.moveToLast();
        studentDataCacheList = new ArrayList<>();
        studentDataCacheList.add(new StudentDataCache(cursor.getString(19),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10), cursor.getString(11),
                cursor.getString(12), cursor.getString(13), cursor.getString(14),
                cursor.getString(15), cursor.getString(16), cursor.getString(17)
        ));
    }

    private void studentParent() {
        StudentParentHelper dataBase = new StudentParentHelper(requestDataForStudent.getContext());
        Cursor cursor = dataBase.showParent(uid);
        checkDataBase(cursor);
        parentDataCacheList = new ArrayList<>();
        do{
            parentDataCacheList.add(new ParentDataCache(
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6)
            ));
        }while (cursor.moveToNext());
    }

    private void studentGuardian(){
        StudentGuardianHelper dataBase = new StudentGuardianHelper(requestDataForStudent.getContext());
        Cursor cursor = dataBase.showGurdain(uid);
        checkDataBase(cursor);
        guardianDataCacheList = new ArrayList<>();
        do{
            guardianDataCacheList.add(new GuardianDataCache(
                    cursor.getString(1), cursor.getString(2), cursor.getString(3)
            ));
        }while (cursor.moveToNext());
    }

    private void studentSibling(){
        StudentSiblingHelper database = new StudentSiblingHelper(requestDataForStudent.getContext());
        Cursor cursor = database.showSibling(uid);
        checkDataBase(cursor);
        //todo showing latest updated sibling data
        siblingDataCacheList = new ArrayList<>();
        do{
            siblingDataCacheList.add(new StudentDataCache(cursor.getString(19),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), cursor.getString(11),
                    cursor.getString(12), cursor.getString(13), cursor.getString(14),
                    cursor.getString(15), cursor.getString(16),cursor.getString(17)
            ));
        }while (cursor.moveToNext());
    }

    private void checkDataBase(Cursor cursor){
        //todo if there is no data
        if(cursor.getCount() == 0){
            //showMessage("There is no student data in database.");
            return;
        }
        //todo if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }
    }

    private void cacheOffline(Cursor cursor){
        offlineCache = new Cache(cursor.getString(17),
                studentDataCacheList,parentDataCacheList,guardianDataCacheList,siblingDataCacheList);
        showMessage(offlineCache.studentDataCaches.get(0).address);
        requestDataForStudent.studentData(offlineCache);
    }

    private void showMessage(String message){
        requestDataForStudent.setMessage(message);
    }
}
