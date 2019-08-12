package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;
import static android.content.Context.MODE_PRIVATE;

public class LanguageSettingv2 {
    private Context context;

    public LanguageSettingv2(Context context){
        this.context = context;
    }

    public void changeLanguage(String lang) {
        setLocate(lang);
    }

    private void setLocate(String language) {
        Locale locale = new Locale(language);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        //todo above N
        Resources applicationRes = context.getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(locale);
        applicationRes.updateConfiguration(applicationConf, applicationRes.getDisplayMetrics());

        //todo save data to sharepreference
        SharedPreferences.Editor editor = context.getSharedPreferences("LanguageSetting",MODE_PRIVATE).edit();
        editor.putString("language", language);
        editor.apply();
    }

    public String loadLanguage(){
        //todo get data from sharepreference
        SharedPreferences preferences = context.getSharedPreferences("LanguageSetting",MODE_PRIVATE);
        String language = preferences.getString("language", "en");
        changeLanguage(language);
        return language;
    }
}
