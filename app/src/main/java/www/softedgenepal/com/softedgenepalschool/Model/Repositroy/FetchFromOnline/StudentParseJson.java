package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.RequestDataForStudent;
import www.softedgenepal.com.softedgenepalschool.Presenter.StudentHomePagePresenter;

public class StudentParseJson {
    private JSONObject parseJson;
    private StudentHomePagePresenter presenter;
    private RequestDataForStudent requestDataForStudent;

    private List<StudentDataCache> studentDataCacheList;
    private List<ParentDataCache> parentDataCacheList;
    private List<GuardianDataCache> guardianDataCacheList;
    private List<StudentDataCache> siblingDataCacheList;
    public Cache cache;


    public StudentParseJson(RequestDataForStudent requestDataForStudent, JSONObject parseJson) {
        this.requestDataForStudent=requestDataForStudent;
        this.parseJson=parseJson;

        studentDataCacheList = new ArrayList<>();
        parentDataCacheList = new ArrayList<>();
        guardianDataCacheList = new ArrayList<>();
        siblingDataCacheList = new ArrayList<>();
    }

    public void parse(StudentHomePagePresenter presenter) {
        this.presenter = presenter;
        data();
    }

    private void data(){
        String response = "";
        String isParent = "false";
        String isGuardian = "false";
        String isSibling = "false";
        try {
            if(parseJson.getString("response").equals("failed")){
                if(parseJson.getString("data").equals("null")){
                    response="There is not any data to show.";
                }
            }else if(parseJson.getString("response").equals("success")){
                if(parseJson.getString("data").equals("null")){
                    response="There is not any data to show.";
                }else {
                    //todo set show details
                    JSONObject data = new JSONObject(parseJson.getString("data"));
                    studentDetail(new JSONObject(parseJson.getString("studentDetail")), isSibling);  //here isSibling : false

                    if(data.getString("isParent").equals("true")){
                        isParent = "true";
                        parentDetail(new JSONObject(parseJson.getString("parentDetail")));
                        if(data.getString("isSibling").equals("true")){
                            isSibling = "true";
                            siblingDetail(parseJson.getJSONArray("siblingDetail"), isSibling);  // here isSibling : true
                        }else{
                            isSibling = "false";
                        }
                    }

                    if(data.getString("isGuardian").equals("true")){
                        isGuardian = "true";
                        guardianDetail(new JSONObject(parseJson.getString("guardianDetail")));
                        if(data.getString("isSibling").equals("true")){
                            isSibling = "true";
                            siblingDetail(parseJson.getJSONArray("siblingDetail"), isSibling);  //here isSibling : true
                        }else{
                            isSibling = "false";
                        }
                    }

                    //todo work from here
                    cache = new Cache(
                            isParent,isGuardian,isSibling,
                            studentDataCacheList,parentDataCacheList,
                            guardianDataCacheList, siblingDataCacheList);

                    //todo when online update profile
                    requestDataForStudent.studentData(cache);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void studentDetail(JSONObject studentDetail, String isSibling) throws Exception{
        String username = studentDetail.getString("username");
        String userclass = studentDetail.getString("class");
        String section = studentDetail.getString("section");
        String rollno = studentDetail.getString("rollno");
        String gender = studentDetail.getString("gender");

        String dateOfBirthBS = studentDetail.getString("dateOfBirthBS");
        String dateOfBirthAD = studentDetail.getString("dateOfBirthAD");

        String contact = studentDetail.getString("contact");
        String email  = studentDetail.getString("email");
        String house  = studentDetail.getString("house");

        String religion  = studentDetail.getString("religion");
        String caste  = studentDetail.getString("caste");
        String address  = studentDetail.getString("address");
        String bloodGroup  = studentDetail.getString("blood");
        String busStop  = studentDetail.getString("busStop");
        String busRoute  = studentDetail.getString("busRoute");

        String imageUrl  = studentDetail.getString("imageUrl");

        //todo for image removing ~ from image url and storing
        String image = null;
        if(imageUrl.equals("null")){
            image = null;
        }else {
            String[] s = imageUrl.split("~");
            //requestDataForStudent.setMessage(s[1]);
             image = "http://192.168.100.100:423"+s[1];
            //requestDataForStudent.setMessage(image);
        }

        if(isSibling.equals("false")) {
            studentDataCacheList.add(new StudentDataCache(
                    username, userclass, section, rollno,
                    gender, dateOfBirthBS, dateOfBirthAD, contact,
                    email, house, religion, caste, address,
                    bloodGroup, busStop, busRoute, image));
        }else {
            //presenter.handleMessage("siblingDetail\n"+studentDetail.toString());
            siblingDataCacheList.add(new StudentDataCache(
                    username, userclass, section, rollno,
                    gender, dateOfBirthBS, dateOfBirthAD, contact,
                    email, house, religion, caste, address,
                    bloodGroup, busStop, busRoute, image));
        }
    }

    private void parentDetail(JSONObject parentDetail) throws Exception{
        String fatherName = parentDetail.getString("fatherName");
        String fatherOccupation = parentDetail.getString("fatherOccupation");
        String fatherContact = parentDetail.getString("fatherContact");
        String motherName = parentDetail.getString("motherName");
        String motherOccupation = parentDetail.getString("motherOccupation");
        String motherContact = parentDetail.getString("motherContact");

        parentDataCacheList.add(new ParentDataCache(
                fatherName, fatherOccupation, fatherContact,
                motherName,motherOccupation,motherContact));
    }

    private void guardianDetail(JSONObject guardianDetail) throws Exception{
        String guardianName = guardianDetail.getString("guardianName");
        String guardianOccupation = guardianDetail.getString("guardianOccupation");
        String guardianContact = guardianDetail.getString("guardianContact");

        guardianDataCacheList.add(new GuardianDataCache(
                guardianName,guardianOccupation,guardianContact));
    }

    private void siblingDetail(JSONArray siblingDetail, String isSibling)throws Exception{
        for (int i=0; i<siblingDetail.length();i++){
            studentDetail(siblingDetail.getJSONObject(i), isSibling);
        }
    }

    public Cache getCache() {
        return cache;
    }
}
