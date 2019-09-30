package www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher;

import java.io.Serializable;
import java.util.List;

public class TeacherClassRoutineModel2 implements Serializable {
    public String ShiftCode;
    public String ShiftName;
    public List<DayDetail> Detail;

    public class DayDetail implements Serializable{
        public String Day;
        public List<TimeDetail> Detail;

        public class TimeDetail implements Serializable{
            public String Time;
            public String Name;
            public List<FacultyDetail> Detail;

            public class FacultyDetail implements Serializable{
                public String FacultyName;
                public String ClassName;
                public String Section;
                public String StartTime;
                public String EndTime;
                public String SubjectNameEng;
                public String SectionCode;
            }
        }
    }


}
