package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;

public class DefaultDataStore {
    public static class YoutubePlayLists {
        public static void store(Context context, List<YouTubeModel> data) {
            PreferencesForObject.store(context, Constants.YouTube_PlayList, Constants.YouTube_PlayList, data);
        }

        public static Object get(Context context, Type type) {
            return PreferencesForObject.get(context, Constants.YouTube_PlayList, Constants.YouTube_PlayList, "null", type);
        }
    }
}
