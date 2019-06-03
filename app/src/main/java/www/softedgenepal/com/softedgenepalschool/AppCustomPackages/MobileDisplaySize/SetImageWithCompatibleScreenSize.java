package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize;

import android.widget.ImageView;

public class SetImageWithCompatibleScreenSize {
    private ImageView imageView;
    private int width, height;

    public SetImageWithCompatibleScreenSize(ImageView imageView, int width, int height) {
        this.imageView=imageView;
        this.width=width;
        this.height=height;
    }

    public void setImage() {
        imageView.requestLayout();
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void setCompitableForWidth(double divisionValueForWidth){
        imageView.getLayoutParams().width = (int)(width/divisionValueForWidth);
    }

    public void setCompitableForHeight(double divisionValueForHeight){
        imageView.getLayoutParams().height = (int)(height/divisionValueForHeight);
    }
}
