package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.util.List;

public class BusRouteCache {
    public String SystemCode;
    public String StaffCode;
    public String FullName;
    public String RouteName;
    public List<RouteDetails> RouteDetailsList;


    public static class RouteDetails{
        public String StationName;
        public String Latitude;
        public String Longitude;
        public String Order;

        public RouteDetails(String stationName, String latitude, String longitude, String order) {
            StationName = stationName;
            Latitude = latitude;
            Longitude = longitude;
            Order = order;
        }
    }

    public BusRouteCache(String systemCode, String staffCode, String fullName, String routeName, List<RouteDetails> RouteDetailsList) {
        SystemCode = systemCode;
        StaffCode = staffCode;
        FullName = fullName;
        RouteName = routeName;
        this.RouteDetailsList = RouteDetailsList;
    }
}
