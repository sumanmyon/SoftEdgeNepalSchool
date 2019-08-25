package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline;

import android.database.Cursor;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class FetchDataOnline {
    RequestDataForStudent requestDataForStudent;
    String url;
    String uid;
    public FetchDataOnline(RequestDataForStudent requestDataForStudent) {
        this.requestDataForStudent=requestDataForStudent;
        uid = user.Id;
        //url = new URL(requestDataForStudent.getContext()).getLoginUrl()+"UserName="+userCache.getUserName()+"Password="+userCache.getPassword();     //+ "UserName=suman&Password=admin123"
    }

    public void getJson() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //requestDataForStudent.setMessage(response.toString());
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestDataForStudent.setMessage(error.getMessage());
            }
        });
//
        //calling volley interface to get data
        final RequestQueue requestQueue = Volley.newRequestQueue(requestDataForStudent.getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void parseJson(JSONObject request) {
        requestDataForStudent.parseJson(request);
    }

    public void saveforOffline(Cache cacheData) {
        Cache cache = cacheData;

        //todo store student profile data
        StudentProfileHelper profileHelper = new StudentProfileHelper(requestDataForStudent.getContext());
        Cursor cursor = profileHelper.showProfile(uid);
        if(cursor.getCount() == 0){
            studentProfile(profileHelper, cache);
        }else{
            if(profileHelper.deleteProfile(uid)!=0){
                showMessage("Student user :: Successfully deleted and Stored in database");
                studentProfile(profileHelper, cache);
            }
        }

        //todo store student parent data
        if(cache.isParent.equals("true")){
            StudentParentHelper parentHelper = new StudentParentHelper(requestDataForStudent.getContext());
            Cursor cursor1 = parentHelper.showParent(uid);
            if(cursor1.getCount() ==0){
                studentParent(parentHelper, cache);
            }else {
                if(parentHelper.delete(uid)!=0){
                    showMessage("Parent user :: Successfully deleted and Stored in database");
                    studentParent(parentHelper,cache);
                }
            }
        }

        //todo store student parent data
        if(cache.isGuardian.equals("true")){
            StudentGuardianHelper guardianHelper = new StudentGuardianHelper(requestDataForStudent.getContext());
            Cursor cursor1 = guardianHelper.showGurdain(uid);
            if(cursor1.getCount() == 0){
                studentGuardian(guardianHelper, cache);
            }else {
                if(guardianHelper.delete(uid)!=0){
                    showMessage("Guardian user :: Successfully deleted and Stored in database");
                    studentGuardian(guardianHelper, cache);
                }
            }
        }

        //todo store student sibling data
        if(cache.isSibling.equals("true")){
            StudentSiblingHelper siblingHelper = new StudentSiblingHelper(requestDataForStudent.getContext());
            if(siblingHelper.showSibling(uid).getCount() == 0){
                studentSibling(siblingHelper,cache);
            }else {
                if(siblingHelper.delete(uid)!=0){
                    showMessage("Sibling user :: Successfully deleted and Stored in database");
                    studentSibling(siblingHelper,cache);
                }
            }
        }
    }


    private void studentProfile(StudentProfileHelper profileHelper, Cache cache) {
        List<StudentDataCache> studentDataList = cache.studentDataCaches;
        StudentDataCache studentDataCache = studentDataList.get(0);
        boolean isProfileSuccess = profileHelper.insertProfile(
                studentDataCache.username,studentDataCache.userclass, studentDataCache.section,
                studentDataCache.rollno, studentDataCache.gender, studentDataCache.dateOfBirthBS,
                studentDataCache.dateOfBirthAD,studentDataCache.contact,studentDataCache.email,
                studentDataCache.house,studentDataCache.religion,studentDataCache.caste,
                studentDataCache.address,studentDataCache.bloodGroup,studentDataCache.busStop,
                studentDataCache.busRoute,studentDataCache.imageUrl,
                cache.isParent,cache.isGuardian,cache.isSibling,
                uid);
        if(isProfileSuccess){
            showMessage("Student user :: Successfully Stored in database");
        }else {
            showMessage("Student user :: Failed Stored in database");
        }
    }

    private void studentParent(StudentParentHelper parentHelper, Cache cache) {
        List<ParentDataCache> parentDataCacheList = cache.parentDataCaches;
        ParentDataCache parentDataCache = parentDataCacheList.get(0);
        boolean isParentSuccess = parentHelper.insertParent(
                parentDataCache.fatherName, parentDataCache.fatherOccupation, parentDataCache.fatherContact,
                parentDataCache.motherName, parentDataCache.motherOccupation, parentDataCache.motherContact,
                uid
        );
        if(isParentSuccess){
            showMessage("Parent user :: Successfully Stored in database");
        }else {
            showMessage("Parent user :: Failed Stored in database");
        }
    }


    private void studentGuardian(StudentGuardianHelper guardianHelper, Cache cache) {
        List<GuardianDataCache> guardianDataCacheList = cache.guardianDataCaches;
        GuardianDataCache guardianDataCache = guardianDataCacheList.get(0);
        boolean isGuardianSuccess = guardianHelper.insertGuardian(
                guardianDataCache.guardianName, guardianDataCache.guardianOccupation, guardianDataCache.guardianContact,
                uid
        );

        if(isGuardianSuccess){
            showMessage("Guardian user :: Successfully Stored in database");
        }else {
            showMessage("Guardian user :: Failed Stored in database");
        }
    }

    private void studentSibling(StudentSiblingHelper siblingHelper, Cache cache) {
        List<StudentDataCache> siblingDataCacheList = cache.siblingDataCaches;
        //todo looping no.s of sibling data
        for(int i=0; i<siblingDataCacheList.size(); i++) {
            StudentDataCache siblingDataCache = siblingDataCacheList.get(i);
            boolean isSiblingSuccess = siblingHelper.insertSibling(
                    siblingDataCache.username, siblingDataCache.userclass,
                    siblingDataCache.section, siblingDataCache.rollno,
                    siblingDataCache.gender, siblingDataCache.dateOfBirthBS,
                    siblingDataCache.dateOfBirthAD, siblingDataCache.contact,
                    siblingDataCache.email, siblingDataCache.house, siblingDataCache.religion,
                    siblingDataCache.caste, siblingDataCache.address, siblingDataCache.bloodGroup,
                    siblingDataCache.busStop, siblingDataCache.busRoute, siblingDataCache.imageUrl,
                    uid);
            if (isSiblingSuccess) {
                showMessage("Sibling user :: Successfully Stored in database");
            } else {
                showMessage("Sibling user :: Failed Stored in database");
            }
        }
    }

    public void showMessage(String message){
        //requestDataForStudent.handleMessage(message);
    }

}
