package www.softedgenepal.com.softedgenepalschool.Model.FakeApi;

import android.content.Context;
import java.io.InputStream;

public class StudentApi {
    private Context context;

    public StudentApi(Context context) {
        this.context = context;
    }

    public String getStudentFalse() {
        return getInStringFormate("StudentFailed.json");
    }

    public String getStudentTrueForParentWithSibling() {
        return studentTrueForParentWithSibling;  //getInStringFormate("StudentParentWithSibling.json");
    }

    public String getStudentTrueForParentWithNoSibling() {
        return getInStringFormate("StudentParentWithNoSibling.json");
    }

    public String getStudentTrueForGuardianWithSibling() {
        return getInStringFormate("StudentGuardianWithSibling.json");
    }

    public String getStudentTrueForGuardianWithNoSibling() {
        return getInStringFormate("StudentGuardianWithNoSibling.json");
    }


    private String getInStringFormate(String s){
        String jsonFormat = "";
        try {
            InputStream inputStream = context.getAssets().open(s);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonFormat = new String(buffer, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

        return jsonFormat;
    }
    private String studentFalse = "{\n" + "  \"response\":\"failed\",\n" + "  \"data\":null\n" + "}";

    private String studentTrueForParentWithSibling = "{\n" + "  \"response\":\"success\",\n" + "  \"data\":{\n" + "    \"isParent\":true,\n" + "    \"isGuardian\":false,\n" + "    \"isSibling\":true\n" + "  },\n" + "  \"studentDetail\":{\n" + "  \"registrationNo\":\"ravindra123\",\n" + " \"username\":\"Ravindra Subedhi\",\n" + "    \"class\":7,\n" + "    \"section\":\"A\",\n" + "    \"rollno\":\"17\",\n" + "    \"gender\":\"male\",\n" + "    \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "    \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "    \"contact\":\"9844779933\",\n" + "    \"email\":\"sumanmyon.sam@gmail.com\",\n" + "    \"house\":\"White House\",\n" + "    \"religion\":null,\n" + "    \"caste\":null,\n" + "    \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "    \"blood\":\"O +ve\",\n" + "    \"busStop\":\"Lalitpur, Hattiban\",\n" + "    \"busRoute\":\"38B\",\n" + "    \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "  },\n" + "  \"parentDetail\":{\n" + "      \"fatherName\":\"Hari Subedhi\",\n" + "      \"fatherOccupation\":\"Bank Manager\",\n" + "      \"fatherContact\":\"9846723346\",\n" + "      \"motherName\":\"Hari Subedhi\",\n" + "      \"motherOccupation\":\"Bank Manager\",\n" + "      \"motherContact\":\"9846723346\"\n" + "   },\n" + "   \"siblingDetail\":[\n" + "     {\n" + "      \"username\":\"Hari Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    },\n" + "     {\n" + "      \"username\":\"Ravindra Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    },\n" + "     {\n" + "      \"username\":\"Suman Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    }\n" + "  ]\n" + "}";

    private String studentTrueForParentWithNoSibling = "{\n" + "  \"response\":\"success\",\n" + "  \"data\":{\n" + "    \"isParent\":true,\n" + "    \"isGuardian\":false,\n" + "    \"isSibling\":false\n" + "  },\n" + "  \"studentDetail\":{\n" + "    \"username\":\"Ravindra Subedhi\",\n" + "    \"class\":7,\n" + "    \"section\":\"A\",\n" + "    \"rollno\":\"17\",\n" + "    \"gender\":\"male\",\n" + "    \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "    \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "    \"contact\":\"9844779933\",\n" + "    \"email\":\"sumanmyon.sam@gmail.com\",\n" + "    \"house\":\"White House\",\n" + "    \"religion\":null,\n" + "    \"caste\":null,\n" + "    \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "    \"blood\":\"O +ve\",\n" + "    \"busStop\":\"Lalitpur, Hattiban\",\n" + "    \"busRoute\":\"38B\",\n" + "    \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "  },\n" + "  \"parentDetail\":{\n" + "      \"fatherName\":\"Hari Subedhi\",\n" + "      \"fatherOccupation\":\"Bank Manager\",\n" + "      \"fatherContact\":\"9846723346\",\n" + "      \"motherName\":\"Hari Subedhi\",\n" + "      \"motherOccupation\":\"Bank Manager\",\n" + "      \"motherContact\":\"9846723346\"\n" + "   }\n" + "}";

    private String studentTrueForGuardianWithSibling = "{\n" + "  \"response\":\"success\",\n" + "  \"data\":{\n" + "    \"isParent\":false,\n" + "    \"isGuardian\":true,\n" + "    \"isSibling\":true\n" + "  },\n" + "  \"studentDetail\":{\n" + "    \"username\":\"Ravindra Subedhi\",\n" + "    \"class\":7,\n" + "    \"section\":\"A\",\n" + "    \"rollno\":\"17\",\n" + "    \"gender\":\"male\",\n" + "    \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "    \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "    \"contact\":\"9844779933\",\n" + "    \"email\":\"sumanmyon.sam@gmail.com\",\n" + "    \"house\":\"White House\",\n" + "    \"religion\":null,\n" + "    \"caste\":null,\n" + "    \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "    \"blood\":\"O +ve\",\n" + "    \"busStop\":\"Lalitpur, Hattiban\",\n" + "    \"busRoute\":\"38B\",\n" + "    \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "  },\n" + "  \"guardianDetail\":{\n" + "      \"guardianName\":\"Hari Subedhi\",\n" + "      \"guardianOccupation\":\"Bank Manager\",\n" + "      \"guardianContact\":\"9846723346\"\n" + "   },\n" + "   \"siblingDetail\":[\n" + "     {\n" + "      \"username\":\"Seema Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    },\n" + "     {\n" + "      \"username\":\"Ravindra Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    },\n" + "     {\n" + "      \"username\":\"Shyaam Subedhi\",\n" + "      \"class\":7,\n" + "      \"section\":\"A\",\n" + "      \"rollno\":\"17\",\n" + "      \"gender\":\"male\",\n" + "      \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "      \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "      \"contact\":\"9844779933\",\n" + "      \"email\":\"sumanmyon.sam@gmail.com\",\n" + "      \"house\":\"White House\",\n" + "      \"religion\":null,\n" + "      \"caste\":null,\n" + "      \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "      \"blood\":\"O +ve\",\n" + "      \"busStop\":\"Lalitpur, Hattiban\",\n" + "      \"busRoute\":\"38B\",\n" + "      \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "    }\n" + "  ]\n" + "}";

    private String studentTrueForGuardianWithNoSibling = "{\n" + "  \"response\":\"success\",\n" + "  \"data\":{\n" + "    \"isParent\":false,\n" + "    \"isGuardian\":true,\n" + "    \"isSibling\":false\n" + "  },\n" + "  \"studentDetail\":{\n" + "    \"username\":\"Ravindra Subedhi\",\n" + "    \"class\":7,\n" + "    \"section\":\"A\",\n" + "    \"rollno\":\"17\",\n" + "    \"gender\":\"male\",\n" + "    \"dateOfBirth(BS)\":\"2051-05-13\",\n" + "    \"dateOfBirth(AD)\":\"1994-08-29\",\n" + "    \"contact\":\"9844779933\",\n" + "    \"email\":\"sumanmyon.sam@gmail.com\",\n" + "    \"house\":\"White House\",\n" + "    \"religion\":null,\n" + "    \"caste\":null,\n" + "    \"address\":\"Lalitpur, Hattiban, Shitapakha\",\n" + "    \"blood\":\"O +ve\",\n" + "    \"busStop\":\"Lalitpur, Hattiban\",\n" + "    \"busRoute\":\"38B\",\n" + "    \"imageUrl\":\"https://www.filmibeat.com/best-of-img-2017/280x166/best-of/2017/19/137/1076.jpg\"\n" + "  },\n" + "  \"guardianDetail\":{\n" + "      \"guardianName\":\"Hari Subedhi\",\n" + "      \"guardianOccupation\":\"Bank Manager\",\n" + "      \"guardianContact\":\"9846723346\"\n" + "  }\n" + "}";

}