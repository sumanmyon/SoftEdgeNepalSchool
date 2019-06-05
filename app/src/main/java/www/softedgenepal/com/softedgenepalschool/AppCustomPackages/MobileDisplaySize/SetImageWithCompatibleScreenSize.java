package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize;

import android.app.Activity;
import android.widget.ImageView;

public class SetImageWithCompatibleScreenSize {
    private ImageView imageView;
    private DisplaySizeInPixel pixel;

    public SetImageWithCompatibleScreenSize(Activity activity, ImageView imageView) {
        this.imageView=imageView;
        pixel = new DisplaySizeInPixel(activity);
        pixel.setByWindowManager();
    }

    public void setImage() {
        imageView.requestLayout();
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void setCompitableForWidth(double divisionValueForWidth){
        imageView.getLayoutParams().width = (int)(pixel.getWidth()/divisionValueForWidth);
    }

    public void setCompitableForHeight(double divisionValueForHeight){
        imageView.getLayoutParams().height = (int)(pixel.getHeight()/divisionValueForHeight);
    }

}
