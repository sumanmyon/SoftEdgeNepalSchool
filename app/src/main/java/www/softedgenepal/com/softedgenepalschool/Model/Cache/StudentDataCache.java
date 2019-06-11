package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;

public class StudentDataCache implements Serializable{

    public String username;
    public String userclass;
    public String section;
    public String rollno;
    public String gender;
    public String dateOfBirthBS;
    public String dateOfBirthAD;
    public String contact;
    public String email;
    public String house;
    public String religion;
    public String caste;
    public String address;
    public String bloodGroup;
    public String busStop;
    public String busRoute;
    public String imageUrl;

    public StudentDataCache(String username, String userclass, String section, String rollno,
                            String gender, String dateOfBirthBS, String dateOfBirthAD,
                            String contact, String email, String house, String religion,
                            String caste, String address, String bloodGroup, String busStop,
                            String busRoute, String imageUrl) {
        this.username = username;
        this.userclass = userclass;
        this.section = section;
        this.rollno=rollno;
        this.gender = gender;
        this.dateOfBirthBS = dateOfBirthBS;
        this.dateOfBirthAD = dateOfBirthAD;
        this.contact = contact;
        this.email = email;
        this.house = house;
        this.religion = religion;
        this.caste = caste;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.busStop = busStop;
        this.busRoute = busRoute;
        this.imageUrl = imageUrl;
    }
}
