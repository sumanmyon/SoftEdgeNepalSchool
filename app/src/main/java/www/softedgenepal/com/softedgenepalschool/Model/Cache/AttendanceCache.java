package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;
import java.util.List;

public class AttendanceCache implements Serializable {
    public String IsPresentToday;
    public List<MonthWise> MonthWiseList;

    public AttendanceCache(String isPresentToday, List<MonthWise> monthWiseList) {
        IsPresentToday = isPresentToday;
        MonthWiseList = monthWiseList;
    }

    public static class MonthWise implements Serializable{
        public String MonthCode;
        public String MonthName;
        public String WorkingDays;
        public String PresentDays;

        public MonthWise(String monthCode, String monthName, String workingDays, String presentDays) {
            MonthCode = monthCode;
            MonthName = monthName;
            WorkingDays = workingDays;
            PresentDays = presentDays;
        }
    }
}
