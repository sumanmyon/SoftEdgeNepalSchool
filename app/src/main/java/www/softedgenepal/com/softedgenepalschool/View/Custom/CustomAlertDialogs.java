package www.softedgenepal.com.softedgenepalschool.View.Custom;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import www.softedgenepal.com.softedgenepalschool.R;

public class CustomAlertDialogs {

    public static void showErrorPopUp(String title, String message, Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);//, R.style.AppTheme2);
        builder1.setMessage(message);
        builder1.setTitle(title);
        builder1.setIcon(context.getResources().getDrawable(R.drawable.ic_error));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void NetworkError(Context context){
        showErrorPopUp("Network Problem", context.getString(R.string.Network_error), context);
    }
}
