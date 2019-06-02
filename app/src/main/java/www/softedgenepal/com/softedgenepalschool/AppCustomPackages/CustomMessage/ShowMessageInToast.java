package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;

public class ShowMessageInToast {
    Activity activity;
    Toast toast;

    public int toastShortLength = Toast.LENGTH_SHORT;
    public int toastLongLength = Toast.LENGTH_LONG;


    //todo set activity for toast
    public ShowMessageInToast(Activity activity) {
        this.activity = activity;
        toast = new Toast(activity);
    }
    //todo default toast
    public void setMessage(String message, int length){
        toast.makeText(activity,message,length);
//        toast.setText(message);
//        toast.setDuration(length);
    }

    public void show(){
        toast.show();
    }

   //todo set gravity for specific location in screen
    public void setGravity(int gravity, int x, int y){
        toast.setGravity(gravity, x, y);
    }

    //todo set Duration for toast
    public void setDuration(int length){
        toast.setDuration(length);
    }

    //todo  Show images in Toast
    public void setImage(int resourceFromDrawable, int height, int width){
        LinearLayout linearLayout = (LinearLayout)toast.getView();
        ImageView imageView = new ImageView(activity);
        imageView.setImageResource(resourceFromDrawable);
        linearLayout.addView(imageView,height, width);
    }

     /*
    todo from below here custom toast works
    if you use the method then don't use setMessage() method
     */
     public void setCustomToast(int drawableImage, String message, int length, int gravity, int x, int y){
         //get custom layout
         View toastView = activity.getLayoutInflater().inflate(R.layout.custom_toast,null);
         //create layout
         TextView textView = toastView.findViewById(R.id.customToastText);
         ImageView imageView = toastView.findViewById(R.id.customToastImage);
         textView.setText(message);
         imageView.setImageResource(drawableImage);
         //set custom toast
         // Initiate the Toast instance.
         toast = new Toast(activity);
         setDuration(Toast.LENGTH_SHORT);
         setGravity(gravity, x, y);
         toast.setView(toastView);
         /*
         this field is required here ,
         use this two field from about two methods
         called :: setGravity() & setDuration()
         and then call show() method

         toast.setDuration(length);
         toast.setGravity(Gravity.CENTER,0,0);
         */
     }

     /*
         todo if need then integrate this
         Toast Message From Child Thread.
         https://www.dev2qa.com/android-custom-toast-example/
      */

}
