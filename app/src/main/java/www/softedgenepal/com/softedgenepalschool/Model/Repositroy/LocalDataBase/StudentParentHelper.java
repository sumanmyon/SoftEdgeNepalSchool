package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentParentHelper extends StudentDataBase{

    //for parent
    private static final String StudentParentTable = "StudentParentTable";

    private static final String id = "id";
    private static final String fatherName = "fatherName";
    private static final String fatherOccupation = "fatherOccupation";
    private static final String fatherContact = "fatherContact";
    private static final String motherName = "motherName";
    private static final String motherOccupation = "motherOccupation";
    private static final String motherContact = "motherContact";
    private static final String sid = "sid";


    public StudentParentHelper(Context context) {
        super(context);
    }

    //todo insert
    public boolean insertParent(String fName, String fOccupation, String fContact,
                                String mName, String mOccupation, String mContact,
                                String ssid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fatherName, fName);
        values.put(fatherOccupation, fOccupation);
        values.put(fatherContact, fContact);
        values.put(motherName,mName);
        values.put(motherOccupation,mOccupation);
        values.put(motherContact,mContact);
        values.put(sid, ssid);

        long result = db.insert(StudentParentTable, null, values);
        //db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //todo show
    public Cursor showParent(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+StudentParentTable+" where sid="+uid,null);
        //db.close();
        return cursor;
    }


    //todo update

    //todo delete
    public int delete(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(StudentParentTable,"sid=?",new String[]{uid});
    }
}
