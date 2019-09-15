package www.softedgenepal.com.softedgenepalschool.Model.Cache.User;

import java.io.Serializable;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;

public class StudentProfileModel implements Serializable {
    public Data Data;
    public StudentDataCache StudentDetail;
    public ParentDataCache ParentDetail;
    public GuardianDataCache GuardianDetail;
    public List<StudentDataCache> SiblingDetail;

    public class Data implements Serializable{
        public Boolean HasSibling;
    }
}
