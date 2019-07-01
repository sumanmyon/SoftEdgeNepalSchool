package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import www.softedgenepal.com.softedgenepalschool.R;

public abstract class AlertDialog extends Dialog {
    private TextView titleTextView, messageTextView;
    private Button cancelButton, exitButton;

    protected AlertDialog(Context context) {
        super(context);
        dialogCreate();
    }

    @Override
    public void show() {
        super.show();
    }

    private void dialogCreate() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog);

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);

        setCancelable(false);

        //casting
        casting();

        //exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeButtonClick(v);
            }
        });

        //cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positiveButtonClick(v);
            }
        });
    }


    private void casting() {
        titleTextView = findViewById(R.id.alertDialogTitle);
        messageTextView = findViewById(R.id.alertDialogMessage);
        cancelButton = findViewById(R.id.alertDialogCancelButton);
        exitButton = findViewById(R.id.alertDialogExitButton);
    }


    public void setCustomTitle(String title){
        titleTextView.setText(title);
    }
    public void setCustomMessage(String message){
        messageTextView.setText(message);
    }

    public void setPositiveButtonText(String positiveButtonText) {
        cancelButton.setText(positiveButtonText);
    }

    public void setNegativeButtonText(String negativeButtonText) {
        exitButton.setText(negativeButtonText);
    }

    protected abstract void positiveButtonClick(View v);
    protected abstract void negativeButtonClick(View v);
}
