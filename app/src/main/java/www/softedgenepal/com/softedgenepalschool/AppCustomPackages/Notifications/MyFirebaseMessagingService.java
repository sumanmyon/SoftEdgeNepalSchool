package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService{}
//extends FirebaseMessagingService {
//    private static final String TAG = "FCM Service";
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // TODO: Handle FCM messages here.
//        // If the application is in the foreground handle both data and notification messages here.
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated.
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        notification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//    }
//
//    private void notification(String title, String message){
////        Intent intent = new Intent(context, MainActivity.class);
////        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentTitle(title);
//        builder.setContentText(message);
//        builder.setPriority(Notification.PRIORITY_MAX);
//        builder.setAutoCancel(false);
//        builder.setDefaults(3);     //TODO provide nothing=0, sound=1, vibration=2, sound & vibration=3 4 bata auta repeate hunxa
//        builder.setTicker("Please drink water");  //TODO chainxa//Set the text that is displayed in the status bar when the notification first arrives.
//
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        int notificationId = 1;
//        manager.notify(notificationId, builder.build());
//    }
//}
