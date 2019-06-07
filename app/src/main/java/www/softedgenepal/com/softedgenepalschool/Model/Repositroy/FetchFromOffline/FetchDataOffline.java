package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase.StudentProfileHelper;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.RequestDataForStudent;

public class FetchDataOffline {
    RequestDataForStudent requestDataForStudent;

    private List<StudentDataCache> studentDataCacheList;
    private List<ParentDataCache> parentDataCacheList;
    private List<GuardianDataCache> guardianDataCacheList;
    private List<StudentDataCache> siblingDataCacheList;

    public Cache offlineCache;

    public FetchDataOffline(RequestDataForStudent requestDataForStudent) {
        this.requestDataForStudent=requestDataForStudent;
    }

    public void getFromLocalStore() {
        StudentProfileHelper dataBase = new StudentProfileHelper(requestDataForStudent.getContext());
        Cursor cursor = dataBase.showProfile("1");
        //todo if there is no data
        if(cursor.getCount() == 0){
            requestDataForStudent.setMessage("There is no data in database.");
            return;
        }
        //requestDataForStudent.setMessage("There is data in database.");

        //todo if cursor is at first of row or not
        if(!cursor.isFirst()){
            cursor.moveToFirst();
        }

        //todo showing latest updated student data
        cursor.moveToLast();
        studentDataCacheList = new ArrayList<>();
        studentDataCacheList.add(new StudentDataCache(
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10),
                cursor.getString(11), cursor.getString(12), cursor.getString(13),
                cursor.getString(14), cursor.getString(15), cursor.getString(16)
        ));
        offlineCache = new Cache(cursor.getString(14), cursor.getString(15),cursor.getString(16),
                studentDataCacheList,null,null,null);
        requestDataForStudent.studentData(offlineCache);
    }
}
