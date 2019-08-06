package www.softedgenepal.com.softedgenepalschool.Model.Cache.User;

import java.io.Serializable;

public class UserCache implements Serializable {
    private String FullName;
    private String Id;
    private String ImageUrl;
    private String Password;
    private String Role;
    private String SystemCode;
    private String UserName;

    public UserCache(){

    }

    public UserCache(String fullName, String id, String imageUrl, String password, String role, String systemCode, String userName) {
        this.FullName = fullName;
        this.Id = id;
        this.ImageUrl = imageUrl;
        this.Password = password;
        this.Role = role;
        this.SystemCode = systemCode;
        this.UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public String getId() {
        return Id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return Role;
    }

    public String getSystemCode() {
        return SystemCode;
    }

    public String getUserName() {
        return UserName;
    }
}
