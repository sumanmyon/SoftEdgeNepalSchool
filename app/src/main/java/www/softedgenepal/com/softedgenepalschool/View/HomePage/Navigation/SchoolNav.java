package www.softedgenepal.com.softedgenepalschool.View.HomePage.Navigation;

import android.app.Activity;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.R;

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
                showMessage("Coming Soon");
                break;
            case R.id.blog:
                showMessage("Coming Soon");
                break;
            case R.id.gallery:
                showMessage("Coming Soon");
                break;
            case R.id.videos:
                showMessage("Coming Soon");
                break;
            case R.id.about:
                showMessage("Coming Soon");
                break;
            case R.id.setting:
                showMessage("Coming Soon");
                break;
            default:
                break;
        }
    }

    private void showMessage(String message){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
