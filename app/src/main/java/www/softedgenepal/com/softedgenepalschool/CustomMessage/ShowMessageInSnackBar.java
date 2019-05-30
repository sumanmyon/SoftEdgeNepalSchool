package www.softedgenepal.com.softedgenepalschool.CustomMessage;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import www.softedgenepal.com.softedgenepalschool.R;

public class ShowMessageInSnackBar {
    View view;
    Activity activity;
    Snackbar snackbar;
    private String message;

    public int snackShortLength = Snackbar.LENGTH_SHORT;
    public int snackLongLength = Snackbar.LENGTH_LONG;
    public int snackIndefinedLength = Snackbar.LENGTH_INDEFINITE;

    //todo set view for snackbar
    public ShowMessageInSnackBar(View view, Activity activity) {
        this.view = view;
        this.activity=activity;
    }


    //todo default snack bar
    public void showSnackBar(String message, int length){
         snackbar = Snackbar.make(view,message, length);
         this.message=message;
    }

    //todo set color
    //eg Color.RED
    private void setActionColorForButton(int color){
        snackbar.setActionTextColor(color);
    }


    public void show(){
        snackbar.show();
    }

    public void setAction(int stringResourceId, final OnClickActionEventSnackBar onClickActionEventForSnackBar){
        // Set button with text Open at right side..
        snackbar.setAction(stringResourceId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActionEventForSnackBar.setActionForSnackBar();
            }
        });
    }

    //todo default/standard snack bar
    public void setDefaultActionToGiveMoreInformation(int stringResourceId,
                                                      final OnClickActionEventSnackBar onClickActionEventForSnackBar,
                                                      final OnClickActionEventSnackBar.ForCallBack forCallBack){
        // Set button with text Open at right side..
        snackbar.setAction(stringResourceId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActionEventForSnackBar.setActionForSnackBar();
            }
        });
        // Set the Callback function.
        snackbar.setCallback(new Snackbar.Callback(){
            @Override
            public void onShown(Snackbar sb) {
                forCallBack.onCreate();
            }

            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                forCallBack.onDestroy();
            }
        });
    }

    //todo work for custom snack bar
    public void setCustomAction(int stringResourceId,
                                final OnClickActionEventSnackBar onClickActionEventForSnackBar,
                                int colorForButton,
                                int backgroundColor){
        // Set button with text Open at right side..
        snackbar.setAction(stringResourceId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActionEventForSnackBar.setActionForSnackBar();
            }
        });
        setActionColorForButton(colorForButton);

        //Get snackBar view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(backgroundColor);
    }

    //todo custom view with layout
    public void setCustomView(int drawableImage){
        //to implement this method first add
        //setAction() method
        snackbar.setText("");
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackground(activity.getResources().getDrawable(R.color.colorSecondary));
        View customView = activity.getLayoutInflater().inflate(R.layout.custom_snackbar, null);
        //create layout
        TextView textView = customView.findViewById(R.id.customSnackText);
        ImageView imageView = customView.findViewById(R.id.customSnackImage);
        textView.setText(message);
        imageView.setImageResource(drawableImage);
        snackbarLayout.addView(customView);
    }

    //todo show snack bar on top of that view you click or given
    //https://www.dev2qa.com/android-snackbar-example/

}
