package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOffline;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class StoreInSharePreference {
    Context context;
    SharedPreferences prefs;
    static String MY_PREFS_NAME;
    private String stringName;
    public String Calender = "calender";

    public StoreInSharePreference(Context context) {
        this.context = context;
    }

    public void setType(String type){
        if(type.equals("calender")){
            MY_PREFS_NAME ="Calender";
            stringName = Calender;
        }

        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    public void storeData(String data){
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString(stringName, data);
        editor.apply();
    }

    public String getData(){
        String getUserData = null;
        try {
            String data = prefs.getString(stringName, "No name defined");//"No name defined" is the default value.
            getUserData = data;
        }catch (Exception e){
            getUserData = null;
        }
        //showMessage(getUserData);

        return getUserData;
    }

    private void showMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
