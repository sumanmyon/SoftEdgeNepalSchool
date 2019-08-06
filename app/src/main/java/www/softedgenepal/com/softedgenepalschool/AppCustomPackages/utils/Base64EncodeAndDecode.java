package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Base64EncodeAndDecode {
    public static String encode(String text){
        // Encode data on your side using BASE64
        byte[] data = text.getBytes();//("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.DEFAULT); //NO_WRAP
        return base64;
    }

    public static String decode(String text){
        // Encode data on your side using BASE64
        byte[] data = Base64.decode(text, Base64.DEFAULT);
        String convert = null;
        try {
             convert = new String(data);//, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convert;
    }


//    Or with StandardCharsets:
//
//    // Sending side
//    byte[] data = text.getBytes(StandardCharsets.UTF_8);
//    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
//
//    // Receiving side
//    byte[] data = Base64.decode(base64, Base64.DEFAULT);
//    String text = new String(data, StandardCharsets.UTF_8);
}
