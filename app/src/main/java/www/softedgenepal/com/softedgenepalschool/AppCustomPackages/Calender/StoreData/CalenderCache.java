package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData;

import org.json.JSONObject;

public class CalenderCache {
    public JSONObject data;

    public String title;
    public String description;
    public String start;
    public String end;
    public String type;
    public String backgroundColor;
    public String isActive;

    public CalenderCache(JSONObject data){
        this.data = data;
    }

    public CalenderCache(String title, String description, String start, String end, String type, String backgroundColor, String isActive) {
        this.data = data;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.type = type;
        this.backgroundColor = backgroundColor;
        this.isActive = isActive;
    }
}
