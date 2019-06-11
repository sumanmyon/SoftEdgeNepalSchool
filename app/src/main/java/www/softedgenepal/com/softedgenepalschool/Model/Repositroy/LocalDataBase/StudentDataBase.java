package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDataBase extends SQLiteOpenHelper {
    private static final String StudentDB = "StudentDB";
    private static final String StudentLoginTable = "StudentLoginTable";
    private static final String StudentProfileTable = "StudentProfileTable";
    private static final String StudentParentTable = "StudentParentTable";
    private static final String StudentGuardianTable = "StudentGuardianTable";
    private static final String StudentSiblingTable = "StudentSiblingTable";

    public StudentDataBase(Context context){
        super(context, StudentDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //todo login table
        db.execSQL("Create Table "+StudentLoginTable+" (uid INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

        /*
            todo student
         */
        //todo student profile
        db.execSQL("Create Table "+StudentProfileTable+"(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " username TEXT, class TEXT, section TEXT, rollno TEXT, gender TEXT, " +
                " dateOfBirthBS TEXT, dateOfBirthAD TEXT, contact TEXT," +
                " email TEXT, house TEXT, religion TEXT, caste TEXT, " +
                " address TEXT, blood TEXT, busStop TEXT, busRoute TEXT, imageUrl TEXT," +
                " isParent TEXT, isGuardian TEXT, isSibling TEXT, sid TEXT);");
        //todo student parent
        db.execSQL("Create Table "+StudentParentTable+"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " fatherName TEXT, fatherOccupation TEXT, fatherContact TEXT," +
                " motherName TEXT, motherOccupation TEXT, motherContact TEXT," +
                " sid TEXT)");
        //todo student guardian
        db.execSQL("Create Table "+StudentGuardianTable+"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " guardianName TEXT, guardianOccupation TEXT, guardianContact TEXT," +
                " sid TEXT)");
        //todo student sibling
        db.execSQL("Create Table "+StudentSiblingTable+"(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " username TEXT, class TEXT, section TEXT, rollno TEXT, gender TEXT, " +
                " dateOfBirthBS TEXT, dateOfBirthAD TEXT, contact TEXT," +
                " email TEXT, house TEXT, religion TEXT, caste TEXT, " +
                " address TEXT, blood TEXT, busStop TEXT, busRoute TEXT, imageUrl TEXT," +
                " sid TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+StudentLoginTable);
        db.execSQL("DROP TABLE IF EXISTS "+StudentProfileTable);
        db.execSQL("DROP TABLE IF EXISTS "+StudentParentTable);
        db.execSQL("DROP TABLE IF EXISTS "+StudentGuardianTable);
        db.execSQL("DROP TABLE IF EXISTS "+StudentSiblingTable);
        onCreate(db);
    }
}
