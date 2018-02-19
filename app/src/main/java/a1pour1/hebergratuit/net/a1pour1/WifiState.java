package a1pour1.hebergratuit.net.a1pour1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SPORE on 18/02/2018.
 */



public class WifiState {

    Context context;

    public WifiState(Context context) {
        this.context = context;
    }

    public boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;

        }
        if (haveConnectedWifi == true || haveConnectedMobile == true) {

            return true;

        } else {
            return false;
        }
    }
    public boolean wifiNetworkConnection(){
        boolean haveConnectedWifi = false;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;

        }
        if (haveConnectedWifi == true) {

            return true;

        } else {
            return false;
        }
    }
    public boolean mobileNetworkConnection(){
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {

            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;

        }
        if (haveConnectedMobile == true) {

            return true;

        } else {
            return false;
        }
    }
}

