package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.util.List;

public class Cache {
    public String isParent = "";
    public String isGuardian = "";
    public String isSibling = "";
    public List<StudentDataCache> studentDataCaches;
    public List<ParentDataCache> parentDataCaches;
    public List<GuardianDataCache> guardianDataCaches;
    public List<StudentDataCache> siblingDataCaches;

    public Cache(String isParent, String isGuardian, String isSibling,
                 List<StudentDataCache> studentDataCaches,
                 List<ParentDataCache> parentDataCaches,
                 List<GuardianDataCache> guardianDataCaches,
                 List<StudentDataCache> siblingDataCaches) {
        this.isParent = isParent;
        this.isGuardian = isGuardian;
        this.isSibling = isSibling;
        this.studentDataCaches = studentDataCaches;
        this.parentDataCaches = parentDataCaches;
        this.guardianDataCaches = guardianDataCaches;
        this.siblingDataCaches = siblingDataCaches;
    }
}
