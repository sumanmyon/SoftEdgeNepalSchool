package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferencesForObject {
    //https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
    private static SharedPreferences preferences;
    private static Gson gson = new Gson();

    public static void store(Context context, String prefName, String key, Object value) {
        String json = gson.toJson(value);
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static Object get(Context context, String prefName, String key, String defaultValue, Class<?> aClass) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return gson.fromJson(preferences.getString(key, defaultValue), aClass);
    }

    //todo Removing single preference:
    public static void clear( String key) {
        preferences.edit().remove(key).apply();  //commit()
    }

    //todo Removing all preferences:
    public static void clearAll() {
        preferences.edit().clear().apply();
    }
}
