package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.QRandBarCode;

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;
import www.softedgenepal.com.softedgenepalschool.View.Login.LoginActivity;

public class QRScan implements View.OnClickListener{
    LoginActivity activity;
    IntentIntegrator qScan;


    public QRScan(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        qScan = new IntentIntegrator(activity);
        qScan.initiateScan();
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        //https://www.simplifiedcoding.net/android-qr-code-scanner-tutorial/
        if(result!=null){
            if(result.getContents()== null){

            }else {
                try{
                    JSONObject object = new JSONObject(result.getContents());
                    activity.editTextUserName.setText(String.valueOf(object));
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }else {
            //super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
