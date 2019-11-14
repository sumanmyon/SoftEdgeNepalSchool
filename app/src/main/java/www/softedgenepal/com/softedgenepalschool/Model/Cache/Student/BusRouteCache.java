package www.softedgenepal.com.softedgenepalschool.Model.Cache.Student;

import java.io.Serializable;
import java.util.List;

public class BusRouteCache implements Serializable {
    public String SystemCode;
    public String StaffCode;
    public String FullName;

    public String Driver;
    public String BusNo;
    public String RouteName;
    public List<RouteDetails> RouteDetails;


    public static class RouteDetails implements Serializable{
        public String StationName;
        public String Latitude;
        public String Longitude;
        public String PickUpTime;
        public String DropTime;
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
        this.RouteDetails = RouteDetailsList;
    }
}
