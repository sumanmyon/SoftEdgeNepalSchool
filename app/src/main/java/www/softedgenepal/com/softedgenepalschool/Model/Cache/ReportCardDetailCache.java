package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;
import java.util.List;

public class ReportCardDetailCache implements Serializable {
    public String Position;
    public String Result;
    public List<Marks> Marks;

    public static class Marks implements Serializable{
        public String SubjectCode;
        public String Subject;

        public String FullMarks;
        public String PassMarks;
        public String ObtainedMarks;

        public String PracticalFullMarks;
        public String PracticalPassMarks;
        public String PracticalObtainedMarks;

        public String IsAbsentPractical;
        public String IsAbsentTheory;

        public String Highest;
        public String Grade;
        public String GradePoint;
        public String ThGrade;
        public String PrGrade;

        public Marks(String subjectCode, String subject,
                     String fullMarks, String passMarks, String obtainedMarks,
                     String practicalFullMarks, String practicalPassMarks, String practicalObtainedMarks,
                     String isAbsentPractical, String isAbsentTheory,
                     String highest, String grade, String gradePoint, String thGrade, String prGrade) {
            SubjectCode = subjectCode;
            Subject = subject;
            FullMarks = fullMarks;
            PassMarks = passMarks;
            ObtainedMarks = obtainedMarks;
            PracticalFullMarks = practicalFullMarks;
            PracticalPassMarks = practicalPassMarks;
            PracticalObtainedMarks = practicalObtainedMarks;
            IsAbsentPractical = isAbsentPractical;
            IsAbsentTheory = isAbsentTheory;
            Highest = highest;
            Grade = grade;
            GradePoint = gradePoint;
            ThGrade = thGrade;
            PrGrade = prGrade;
        }
    }

    public ReportCardDetailCache(String position, String result, List<ReportCardDetailCache.Marks> marks) {
        Position = position;
        Result = result;
        Marks = marks;
    }
}
