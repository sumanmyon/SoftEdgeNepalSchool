package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class LanguageSetting {
    private Activity activity;
    private Context context;

    public LanguageSetting(Activity activity){
        this.activity = activity;
        context = activity;
    }

    public void changeLanguage(String lang) {
        setLocate(lang);
    }

    private void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = new Configuration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        //activity.getBaseContext().getResources().updateConfiguration(configuration, activity.getBaseContext().getResources().getDisplayMetrics());

        //save data to sharepreference
        SharedPreferences.Editor editor = activity.getSharedPreferences("LanguageSetting",MODE_PRIVATE).edit();
        editor.putString("language", language);
        editor.apply();
    }

    public String loadLanguage(){
        SharedPreferences preferences = activity.getSharedPreferences("LanguageSetting",MODE_PRIVATE);
        String language = preferences.getString("language", "en");
        changeLanguage(language);
        return language;
    }
}
