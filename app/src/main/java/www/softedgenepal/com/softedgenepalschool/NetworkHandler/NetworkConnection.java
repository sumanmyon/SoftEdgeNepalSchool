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
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvabiable = false;
        if(!networkInfo.equals(null) && networkInfo.isConnected()){
            isAvabiable = true;
        }
        return isAvabiable;
    }
}
