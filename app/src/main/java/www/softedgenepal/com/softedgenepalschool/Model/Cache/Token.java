package www.softedgenepal.com.softedgenepalschool.Model.Cache;

public class Token {
    private static String USER_TOKEN_KEY;

    public static void setUserTokenKey(String tokenKey){
        USER_TOKEN_KEY = tokenKey;
    }

    public static String getUserTokenKey() {
        return USER_TOKEN_KEY;
    }
}
