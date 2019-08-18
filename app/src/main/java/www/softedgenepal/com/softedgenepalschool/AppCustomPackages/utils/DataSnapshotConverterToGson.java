package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;

public class DataSnapshotConverterToGson {
    private static Gson gson = new Gson();

    private static String toString(DataSnapshot dataSnapshot){
        Object s = dataSnapshot.getValue();
        return gson.toJson(s);
    }

    private static JsonElement toJsonElement(DataSnapshot dataSnapshot){
        Object s = dataSnapshot.getValue();
        return gson.toJsonTree(s);
    }

    public static JsonArray getJsonArrayFromJsonElement(DataSnapshot dataSnapshot){
        JsonElement jsonElement = toJsonElement(dataSnapshot);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        return jsonArray;
    }

    public static JSONArray getJsonArrayFromString(DataSnapshot dataSnapshot){
        String string = toString(dataSnapshot);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
        }catch (Exception e){
            jsonArray = null;
        }
        return jsonArray;
    }
}
