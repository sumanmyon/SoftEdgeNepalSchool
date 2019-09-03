package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.PreferencesForObject;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.StoreInSharePreference;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.AboutActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.EventActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.LoginActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SettingActivity;

public class SchoolNav {
    private Activity activity;
    private int itemId;

    public SchoolNav(Activity activity, int itemId) {
        this.activity=activity;
        this.itemId=itemId;
    }

    public void set(){
        switch (itemId){
            case R.id.event:
                redirectToActivity(EventActivity.class);
                break;
            case R.id.gallery:
                showMessage("Coming Soon");
                break;
            case R.id.videos:
                showMessage("Coming Soon");
                break;
            case R.id.about:
                redirectToActivity(AboutActivity.class);
                break;
            case R.id.setting:
                redirectToActivity(SettingActivity.class);
                break;
            case R.id.logout:
                PreferencesForObject.clear(Constants.LoginCredential);
                reDirectToMainActivityAfterLogout();
                break;
            case R.id.login:
                redirectToActivity(LoginActivity.class);
                activity.finish();
                break;
            default:
                break;
        }
    }
    private void reDirectToMainActivityAfterLogout(){
        showMessage("log out successfully");
        Log.d("LoginForm", "3. log out successfully");
        Intent intent = new Intent(activity, MainActivity.class);
        MainActivity.userType = "School";
        MainActivity.user = null;
        activity.finish();
        activity.startActivity(intent);
    }
    private void redirectToActivity(Class<?> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }
    private void showMessage(String message){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
