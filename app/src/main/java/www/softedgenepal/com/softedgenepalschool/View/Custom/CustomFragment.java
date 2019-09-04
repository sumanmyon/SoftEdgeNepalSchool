package www.softedgenepal.com.softedgenepalschool.View.Custom;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;

public class CustomFragment extends Fragment {
    public int pendingRequestsCount = 0;
    public static final Gson GSON = new Gson();
    private Fragment fragment = this;

    protected void showErrorPopUp(String title, String message) {
        CustomAlertDialogs dialog = new CustomAlertDialogs();
        dialog.showErrorPopUp(title, message, fragment.getContext());
    }

    protected void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
