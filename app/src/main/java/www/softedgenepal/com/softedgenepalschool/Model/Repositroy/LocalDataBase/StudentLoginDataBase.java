package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LocalDataBase;

import android.content.Context;


public class StudentLoginDataBase extends StudentDataBase{
    public static final String StudentDB = "StudentDB";
    public static final String StudentLoginTable = "StudentLoginTable";

    public static final String uid = "uid";
    public static final String username = "username";
    public static final String password = "password";

    public StudentLoginDataBase(Context context) {
        super(context);
    }

    //todo Insert

    //todo Check

}
