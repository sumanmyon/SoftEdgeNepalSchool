package www.softedgenepal.com.softedgenepalschool.View.NavigationBindingAndTabLayoutAdapter.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.AboutActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.EventActivity;

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
//            case R.id.blog:
//                showMessage("Coming Soon");
//                break;
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
                showMessage("Coming Soon");
                break;
            case R.id.logout:
                showMessage("Coming Soon");
                break;
            default:
                break;
        }
    }
    private void redirectToActivity(Class<?> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }
    private void showMessage(String message){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
