package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentGuardianHelper extends StudentDataBase {
    private static final String StudentGuardianTable = "StudentGuardianTable";

    private static final String id = "id";
    private static final String guardianName = "guardianName";
    private static final String guardianOccupation = "guardianOccupation";
    private static final String guardianContact = "guardianContact";
    private static final String sid = "sid";

    public StudentGuardianHelper(Context context) {
        super(context);
    }

    //todo insert
    public boolean insertGuardian(String gName, String gOccupation, String gContact, String ssid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(guardianName,gName);
        values.put(guardianOccupation,gOccupation);
        values.put(guardianContact,gContact);
        values.put(sid,ssid);

        long result = db.insert(StudentGuardianTable, null, values);
        //db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    //todo show
    public Cursor showGurdain(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+StudentGuardianTable+" where sid="+uid,null);
        //db.close();
        return cursor;
    }
    //todo update

    //todo delete
    public int delete(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(StudentGuardianTable,"sid=?",new String[]{uid});
    }
}
