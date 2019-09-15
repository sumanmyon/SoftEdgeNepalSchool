package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Cache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.RequestDataForStudent;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
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
        this.requestDataForStudent = requestDataForStudent;
        this.parseJson = parseJson;

        studentDataCacheList = new ArrayList<>();
        parentDataCacheList = new ArrayList<>();
        guardianDataCacheList = new ArrayList<>();
        siblingDataCacheList = new ArrayList<>();
    }

    public void parse(StudentHomePagePresenter presenter) {
        this.presenter = presenter;
        data();
    }

    private void data() {
        String response = "";
        String HasSibling = "false";
        try {
            if (parseJson.getString("Response").equals("failed")) {
                if (parseJson.getString("Data").equals("null") || parseJson.getString("data").equals("")) {
                    response = "There is not any data to show.";
                }
            } else if (parseJson.getString("Response").equals("success")) {
                if (parseJson.getString("Data").equals("null")) {
                    response = "There is not any data to show.";
                } else {
                    //todo set show details
                    JSONObject data = new JSONObject(parseJson.getString("Data"));
                    studentDetail(new JSONObject(parseJson.getString("StudentDetail")), HasSibling);  //here isSibling : false

                    //todo for parent
                    parentDetail(new JSONObject(parseJson.getString("ParentDetail")));

                    //todo for Guardian
                    guardianDetail(new JSONObject(parseJson.getString("GuardianDetail")));

                    //todo for sibling
                    if (data.getString("HasSibling").equals("true")) {
                        HasSibling = "true";
                        siblingDetail(parseJson.getJSONArray("SiblingDetail"), HasSibling);  // here isSibling : true
                    }

                    //todo work from here
                    cache = new Cache(HasSibling,
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

    private void studentDetail(JSONObject studentDetail, String isSibling) throws Exception {
        String registrationNo = studentDetail.getString("RegistrationNo");
        String username = studentDetail.getString("StudentName");

        String userclass = studentDetail.getString("ClassName");
        String section = studentDetail.getString("SectionName");
        String rollno = studentDetail.getString("RollNo");
        String gender = studentDetail.getString("Gender");

        String dateOfBirthBS = studentDetail.getString("DateofBirth");
        if(!dateOfBirthBS.equals("null") || !dateOfBirthBS.equals("")) {
            dateOfBirthBS = DateTime.splitDateOrTime(dateOfBirthBS, "date");
        }

        String dateOfBirthAD = "";

        String contact = studentDetail.getString("Contact");
        if(contact.equals("null")){
            contact = "";
        }
        String email = studentDetail.getString("Email");
        String house = studentDetail.getString("House");

        String religion = studentDetail.getString("Religion");
        String caste = studentDetail.getString("Caste");
        String address = studentDetail.getString("Address");
        String bloodGroup = studentDetail.getString("Blood");

        String busStop = studentDetail.getString("BusStop");
        String busRoute = studentDetail.getString("BusRoute");
        String imageUrl = studentDetail.getString("ImageUrl");

        //todo for image removing ~ from image url and storing
        String image = null;
        if (imageUrl.equals("null")) {
            image = null;
        } else {
            String[] s = imageUrl.split("~");
            //requestDataForStudent.setMessage(s[1]);
            image = new URL().imgUrl + s[1];
            requestDataForStudent.setMessage(image);
        }

        if (isSibling.equals("false")) {
            // presenter.handleMessage("StudentDetail\n"+studentDetail.toString());
           // studentDataCacheList.add(new StudentDataCache(registrationNo, username, userclass, section, rollno, gender, dateOfBirthBS, dateOfBirthAD, contact, email, house, religion, caste, address, bloodGroup, busStop, busRoute, image));
        } else {
            //presenter.handleMessage("siblingDetail\n"+studentDetail.toString());
            //siblingDataCacheList.add(new StudentDataCache(registrationNo, username, userclass, section, rollno, gender, dateOfBirthBS, dateOfBirthAD, contact, email, house, religion, caste, address, bloodGroup, busStop, busRoute, image));
        }
    }

    private void parentDetail(JSONObject parentDetail) throws Exception {
        String fatherName = parentDetail.getString("FatherName");
        String fatherOccupation = parentDetail.getString("FatherOccupation");
        String fatherContact = parentDetail.getString("FatherContact");
        String motherName = parentDetail.getString("MotherName");
        String motherOccupation = parentDetail.getString("MotherOccupation");
        String motherContact = parentDetail.getString("MotherContact");

        //parentDataCacheList.add(new ParentDataCache(fatherName, fatherOccupation, fatherContact, motherName, motherOccupation, motherContact));
        //presenter.handleMessage("parentDetail\n"+parentDetail.toString());
    }

    private void guardianDetail(JSONObject guardianDetail) throws Exception {
        String guardianName = guardianDetail.getString("GuardianName");
        String guardianOccupation = guardianDetail.getString("GuardianOccupation");
        String guardianContact = guardianDetail.getString("GuardianContact");

        //guardianDataCacheList.add(new GuardianDataCache(guardianName, guardianOccupation, guardianContact));
    }

    private void siblingDetail(JSONArray siblingDetail, String isSibling) throws Exception {
        presenter.handleMessage("siblingDetail\n" + siblingDetail.length());
        for (int i = 0; i < siblingDetail.length(); i++) {
            studentDetail(siblingDetail.getJSONObject(i), isSibling);
        }
    }

    public Cache getCache() {
        return cache;
    }
}
