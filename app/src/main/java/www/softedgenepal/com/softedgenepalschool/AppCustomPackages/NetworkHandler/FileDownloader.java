package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Notifications.ShowNotification;

public class FileDownloader {
    private Activity activityClass;
    private long downloadID;

    private String FolderName = "SoftSchool";
    public static String Photo = "Photos";
    public static String Video = "Videos";
    public static String PDF = "Pdf";
    public static String Song = "Song";

    private String storedFolder, storedFileName;

    public FileDownloader (Activity activityClass){
        this.activityClass = activityClass;
    }

    private File setfileName(String fileName, String fileType){
        File main=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));
        setLog(main.getAbsolutePath());

        String appDirectory = null;
        File file = null;

        File[]t=main.getParentFile().listFiles();
        if(t==null){
            appDirectory = String.valueOf(main.getAbsoluteFile());
        }else {
            for (File dir : t) {
                setLog(dir.getAbsolutePath());
                appDirectory = dir.getAbsolutePath();
            }
        }
        //todo .........SoftSchool/Videos
        //todo create folder in storage
        File folder = new File(appDirectory + "/" + FolderName);
        if (!folder.exists()) {
            try {
                setLog(folder.toString());
                if (!folder.mkdirs()) {
                    Log.d("FileDownlaod", "failed to create directory");
                } else {
                    Log.d("FileDownlaod", "success to create directory");
                    File subfolder = new File(appDirectory + "/" + FolderName + "/" + fileType);
                    if (!subfolder.mkdirs()) {
                        Log.d("FileDownlaod", "failed to create sub directory");
                    } else {
                        Log.d("FileDownlaod", "success to create sub directory");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        storedFolder = appDirectory + "/" + FolderName;
        storedFileName = fileName;
        file = new File(appDirectory + "/" + FolderName + "/" + fileType, fileName);
        setLog(file.toString());

        return file;
    }

    private DownloadManager.Request downloadManagerRequest(String downloadUrl, String destinationFileName, String fileType, String title, String description){
        Uri downloadUri = Uri.parse(downloadUrl);                          // Path where you want to download file.
        Uri destiantionUri = Uri.fromFile(setfileName(destinationFileName, fileType));    //Uri.fromFile(destinationPath);     // Uri of the destination file
        setLog(destiantionUri.toString());

        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setDestinationUri(destiantionUri);

        request.setTitle(title);
        request.setDescription(description);
        request.setVisibleInDownloadsUi(true);                  //display this download in the Downloads UI

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE); // Visibility of the download Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresCharging(false);                 // Set if charging is required to begin the download
        }
        request.setAllowedOverMetered(true);                    // Set if download is allowed on Mobile network
        request.setAllowedOverRoaming(true);                    // Set if download is allowed on roaming network

        return request;
    }

    public void downloadFile(String downloadPath, String destinationPath, String fileType, String title, String description){
        DownloadManager downloadManager = (DownloadManager) activityClass.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = downloadManagerRequest(downloadPath, destinationPath,fileType, title, description);
        downloadID = downloadManager.enqueue(request);
    }

    public void registerReciver(){
        activityClass.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void unRegisterReciver(){
        activityClass.unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if(downloadID == id){
                setMessage("Download Completed");
                //viewInDownloads();
                openWhereFileIsSaved();
            }

            //todo download failed, pause, stop....... remaining
        }
    };

    private void openWhereFileIsSaved() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);   //intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse(storedFolder + "/");
        intent.setDataAndType(uri, "*/*");  //"file/*");   //"text/csv");
        //activityClass.startActivity(Intent.createChooser(intent, "Open folder"));

        ShowNotification notification = new ShowNotification(intent, activityClass, "HomeWork", storedFileName);
        notification.show();
    }

    private void viewInDownloads() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        activityClass.startActivity(i);
    }

    private void setMessage(String message){
        Toast.makeText(activityClass, message, Toast.LENGTH_SHORT).show();
    }

    private void setLog(String message){
        Log.d("FileDownlaod", message);
    }
}
