package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;

public class ParentDataCache implements Serializable{
    public String fatherName;
    public String fatherOccupation;
    public String fatherContact;
    public String motherName;
    public String motherOccupation;
    public String motherContact;

    public ParentDataCache(String fatherName, String fatherOccupation, String fatherContact,
                           String motherName, String motherOccupation, String motherContact) {
        this.fatherName = fatherName;
        this.fatherOccupation = fatherOccupation;
        this.fatherContact = fatherContact;
        this.motherName = motherName;
        this.motherOccupation = motherOccupation;
        this.motherContact = motherContact;
    }
}
