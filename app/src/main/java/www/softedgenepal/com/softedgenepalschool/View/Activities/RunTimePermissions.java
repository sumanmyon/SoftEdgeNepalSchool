package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import www.softedgenepal.com.softedgenepalschool.R;

public class RunTimePermissions extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public static String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime);
        openPermissions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        openPermissions();
    }

    @AfterPermissionGranted(123)
    private void openPermissions() {
        if(EasyPermissions.hasPermissions(this,perms)){
            setToast("Opeaning..");
            redirect();

        }else {
            EasyPermissions.requestPermissions(
                    this,
                    "We need permission because this and that",
                    123,
                    perms);
        }
    }

    private void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            // Do something after user returned from app settings screen, like showing a Toast.
            //setToast("Do something after user returned from app settings screen, like showing a Toast");
            openPermissions();
        }
    }

    private void redirect(){
        Intent runIntent = new Intent(this, LoginActivity.class);
        finish();
        runIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(runIntent);
    }
}
