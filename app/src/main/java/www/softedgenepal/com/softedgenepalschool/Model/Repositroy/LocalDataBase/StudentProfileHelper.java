package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentProfileHelper extends StudentDataBase {
    private static final String StudentProfileTable = "StudentProfileTable";

    private static final String id = "id";
    private static final String username = "username";
    private static final String userclass = "class";
    private static final String section = "section";
    private static final String rollno = "rollno";
    private static final String gender = "gender";
    private static final String dateOfBirthBS = "dateOfBirthBS";
    private static final String dateOfBirthAD = "dateOfBirthAD";
    private static final String contact = "contact";
    private static final String email = "email";
    private static final String house = "house";
    private static final String religion = "religion";
    private static final String caste = "caste";
    private static final String address = "address";
    private static final String bloodGroup = "blood";
    private static final String busStop = "busStop";
    private static final String busRoute = "busRoute";
    private static final String imageUrl = "imageUrl";

    private static final String isParent = "isParent";
    private static final String isGuardian = "isGuardian";
    private static final String isSibling = "isSibling";
    private static final String sid = "sid";


    public StudentProfileHelper(Context context) {
        super(context);
    }

    //todo insert
    public boolean insertProfile( String susername, String sclass, String ssection,
                          String srollno, String sgender, String sdateOfBirthBS, String sdateOfBirthAD,
                          String scontact, String semail, String shouse, String sreligion,
                          String scaste, String saddress, String sbloodGroup, String sbusStop,
                          String sbusRoute, String simageUrl, String sisParent, String sisGuardian,
                          String sisSibling, String ssid){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(username,susername);
        values.put(userclass,sclass);
        values.put(section, ssection);
        values.put(rollno,srollno);
        values.put(gender,sgender);
        values.put(email,semail);
        values.put(dateOfBirthBS,sdateOfBirthBS);
        values.put(dateOfBirthAD,sdateOfBirthAD);
        values.put(contact, scontact);
        values.put(house, shouse);
        values.put(religion, sreligion);
        values.put(caste,scaste);
        values.put(address,saddress);
        values.put(bloodGroup,sbloodGroup);
        values.put(busStop,sbusStop);
        values.put(busRoute,sbusRoute);
        values.put(imageUrl,simageUrl);
        values.put(isParent,sisParent);
        values.put(isGuardian,sisGuardian);
        values.put(isSibling, sisSibling);
        values.put(sid, ssid);

        long result = db.insert(StudentProfileTable, null, values);
        //db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //todo show
    public Cursor showProfile(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+StudentProfileTable+" where sid="+uid,null);
        //db.close();
        return cursor;
    }

    //todo update

    //todo delete
    public Integer deleteProfile(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(StudentProfileTable,"sid=?",new String[]{uid});
    }
}
