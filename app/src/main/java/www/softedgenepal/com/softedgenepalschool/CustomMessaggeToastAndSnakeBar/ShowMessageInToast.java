package www.softedgenepal.com.softedgenepalschool.CustomMessaggeToastAndSnakeBar;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class ShowMessage {
    Activity activity;
    View view;

    public  int toastShortLength = Toast.LENGTH_SHORT;
    public  int toastLongLength = Toast.LENGTH_LONG;

    public int snackShortLength = Snackbar.LENGTH_SHORT;
    public int snackLongLength = Snackbar.LENGTH_LONG;
    public int snackIndefinedLength = Snackbar.LENGTH_INDEFINITE;

    //todo set activity for toast
    public ShowMessage(Activity activity) {
        this.activity = activity;
    }

    //todo set view for snackbar
    public ShowMessage(View view) {
        this.view = view;
    }

    //todo default snackbar
    public void showToast(String message, int length){
        Toast
                .makeText(activity,message,length)
                .show();
    }

    //todo default snack bar
    public void showSnackBar(String message, int length){
        Snackbar
                .make(view,message, length)
                .show();
    }
    //todo work for custom toast

    //todo work for custom snack bar

}
