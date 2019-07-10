package www.softedgenepal.com.softedgenepalschool.CustomImage;

public class ImageSplitter {

    public static String[] imageSplit(String imageUrl, String splitType){
        return imageUrl.split(splitType);
    }

    public static String convertSplit(String imageUrl,String regx, String replacement){
        return imageUrl.replaceAll(regx,replacement);
    }
}
