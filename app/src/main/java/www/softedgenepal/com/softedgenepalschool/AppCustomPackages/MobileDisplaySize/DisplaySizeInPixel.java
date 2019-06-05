package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.MobileDisplaySize;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplaySizeInPixel {
    private Activity activity;
    int width, height;
    public DisplaySizeInPixel(Activity activity) {
       this.activity=activity;
    }

    public void setByWindowManager(){
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    public void setByDisplayMetrics(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
