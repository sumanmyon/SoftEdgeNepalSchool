package www.softedgenepal.com.softedgenepalschool.Model.Cache.Student;

import java.io.Serializable;
import java.util.List;

public class RoutineCache implements Serializable {
    public String ExamNameEng;
    public List<Routine> routine;

    public static class Routine implements Serializable{
        public String ExamDate;
        public String StartTime;
        public String EndTime;
        public String SubjectNameEng;

        public Routine(String examDate, String startTime, String endTime, String subjectNameEng) {
            ExamDate = examDate;
            StartTime = startTime;
            EndTime = endTime;
            SubjectNameEng = subjectNameEng;
        }
    }

    public RoutineCache(String examNameEng, List<Routine> routine) {
        ExamNameEng = examNameEng;
        this.routine = routine;
    }
}
