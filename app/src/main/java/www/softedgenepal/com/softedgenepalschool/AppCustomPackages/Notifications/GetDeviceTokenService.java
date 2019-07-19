package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class GetDeviceTokenService extends FirebaseInstanceIdService {
    private static final String TAG = "FCM Service";

    @Override
    public void onTokenRefresh() {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, deviceToken);
    }
}
