package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import www.softedgenepal.com.softedgenepalschool.R;

public class NotificationAboveO {
    private Intent intent;
    private Context context;
    private String title;
    private String body;

    private static int notificationId = 1;
    private static final String CHANNEL_ID = "www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications.DownloadNotifications";
    private static final String CHANNEL_NAME = "SoftSchool Channel";

    public NotificationAboveO(Intent intent, Context context, String title, String body) {
        this.intent = intent;
        this.context = context;
        this.title = title;
        this.body = body;
    }

    public void show(){
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri uri = Uri.parse("android.resource://"
//                + this.getPackageName() + "/" + R.raw.point);

        try {
            // Sound
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/point");
            Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
            r.play();

            // Vibrate
            //Rumble.once(500); // Vibrate for 500 milliseconds.
//            Rumble.makePattern()
//                    .beat(300)
//                    .rest(250)
//                    .beat(720)
//                    .playPattern();
//            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            // Output yes if can vibrate, no otherwise
//            if (v.hasVibrator()) {
//                Log.v("CanVibrate", "YES");
//            } else {
//                Log.v("CanVibrate", "NO");
//            }
//
//            long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//            } else {
//                //deprecated in API 26
//                v.vibrate(pattern, -1);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("CanVibrate", e.getMessage());
            //v.cancel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.notification_white);
        builder.setAutoCancel(true);
        //builder.setSound(uri);
        // Each element then alternates between delay, vibrate, sleep, vibrate, sleep
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000});
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //builder.setDefaults(2);
        builder.setLights(Color.RED, 3000, 3000);
        //builder.setSound(Uri.parse("uri://point.mp3"));
        builder.setContentIntent(p);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel(manager);

        // === Removed some obsoletes
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            CharSequence name = "Channel Name";
//            String description = "Channel Description";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
//                    importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviours after this
//            NotificationManager notificationManager =
//                    context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
        manager.notify(notificationId, builder.build());
    }


    private void createChannel(NotificationManager manager){
        //IMPORTANCE_DEFAULT = show everywhere, make noise but don't visually intrude
        //IMPORTANCE_HIGH = show everywhere, make noise and peeks
        //IMPORTANCE_LOW = show everywhere, don't visually intrude
        //IMPORTANCE_MIN = only show in the shade, below the fade
        //IMPORTANCE_NONE = a notification with no importance, dont show in shade
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.GRAY);
            //notificationChannel.setLockscreenVisibility(Notification.);

            manager.createNotificationChannel(notificationChannel);
        }
    }
}
