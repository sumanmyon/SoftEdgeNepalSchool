package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils;

import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;

public class ImageUrlFormater {
    public String conver(String imageUrl){
        String image;
        if (imageUrl.equals("null") || imageUrl.equals("")) {
            image = "";
        } else {
            String[] s = imageUrl.split("~");
            //requestDataForStudent.setMessage(s[1]);
            image = new URL().imgUrl + s[1];
        }
        return image;
    }
}
