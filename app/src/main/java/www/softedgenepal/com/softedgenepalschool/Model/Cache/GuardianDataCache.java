package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;

public class GuardianDataCache implements Serializable {
    public String guardianName;
    public String guardianOccupation;
    public String guardianContact;

    public GuardianDataCache(String guardianName, String guardianOccupation, String guardianContact) {
        this.guardianName = guardianName;
        this.guardianOccupation = guardianOccupation;
        this.guardianContact = guardianContact;
    }
}
