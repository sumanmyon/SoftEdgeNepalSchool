package www.softedgenepal.com.softedgenepalschool.Model.Cache.Student;

public class AssignmentCache {
    public String Class;
    public String ClassConfigurationCode;
    public String Homework;
    public String CreateDate;
    public String Deadline;
    public String SubjectNameEng;
    public String SubjectCode;
    public String ImageUrl;
    public String FontType;

    public boolean expand = false;

    public AssignmentCache(String aClass, String classConfigurationCode, String homework,
                           String createDate, String deadline,
                           String subjectNameEng, String subjectCode,
                           String imageUrl, String fontType) {
        Class = aClass;
        ClassConfigurationCode = classConfigurationCode;
        Homework = homework;
        CreateDate = createDate;
        Deadline = deadline;
        SubjectNameEng = subjectNameEng;
        SubjectCode = subjectCode;
        ImageUrl = imageUrl;
        this.FontType = fontType;
    }
}
