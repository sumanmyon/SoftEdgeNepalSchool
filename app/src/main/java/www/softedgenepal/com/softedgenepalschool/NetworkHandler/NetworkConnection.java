package www.softedgenepal.com.softedgenepalschool.NetworkHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {
    Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    public boolean isConnectionSuccess(){
        //state of network connectivity and Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //get an instance that represents the current network connection.
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvabiable = false;
        if(!networkInfo.equals(null) && networkInfo.isConnected()){
            isAvabiable = true;
        }
        return isAvabiable;
    }
}
