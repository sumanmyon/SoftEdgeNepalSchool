package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.GuardianDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.ParentDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;

public class Cache implements Serializable {
    public String isSibling = "";
    public List<StudentDataCache> studentDataCaches;
    public List<ParentDataCache> parentDataCaches;
    public List<GuardianDataCache> guardianDataCaches;
    public List<StudentDataCache> siblingDataCaches;

    public Cache(String isSibling,
                 List<StudentDataCache> studentDataCaches,
                 List<ParentDataCache> parentDataCaches,
                 List<GuardianDataCache> guardianDataCaches,
                 List<StudentDataCache> siblingDataCaches) {
        this.isSibling = isSibling;
        this.studentDataCaches = studentDataCaches;
        this.parentDataCaches = parentDataCaches;
        this.guardianDataCaches = guardianDataCaches;
        this.siblingDataCaches = siblingDataCaches;
    }
}
