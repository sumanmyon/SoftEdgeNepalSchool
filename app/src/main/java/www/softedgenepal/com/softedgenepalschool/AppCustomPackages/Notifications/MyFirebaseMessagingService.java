package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.NotificationSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Rumble;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "Notification_MFrom: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification_MBody: " + remoteMessage.getFrom() + " " + remoteMessage.getNotification().getBody());

        //todo only get notification when setting is turn on
        if(NotificationSetting.getNotification(this).equals("TurnOn")
                || NotificationSetting.getNotification(this).equals("No name defined")) {
            notification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void notification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        ShowNotification notification = new ShowNotification(intent, this, title, body);
//        notification.show();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationAboveO notification = new NotificationAboveO(intent, this, title, body);
            notification.show();
        }else {
            ShowNotification notification = new ShowNotification(intent, this, title, body);
            notification.show();
        }
    }
}
