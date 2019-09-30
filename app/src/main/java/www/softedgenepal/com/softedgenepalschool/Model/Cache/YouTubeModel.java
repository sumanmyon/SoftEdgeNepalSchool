package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class YouTubeModel implements Serializable {
    public String id;
    public snippet snippet;

    public class snippet implements Serializable{
        public String publishedAt;
        public String channelId;
        public String title;
        public String description;
        public thumbnails thumbnails;

        public class thumbnails implements Serializable{
            public maxres standard;

            public class maxres implements Serializable{
                public String url = "";
                public String width;
                public String height;
            }
        }

        public String channelTitle;
        public String playlistId;
        public String position;
        public resourceId resourceId;

        public class resourceId implements Serializable{
            public String videoId;
        }
    }

    public contentDetails contentDetails;
    public class contentDetails implements Serializable{
        public String duration;
        public String definition;
        public String caption;
    }
}
