package a1pour1.hebergratuit.net.a1pour1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by SPORE on 18/02/2018.
 */

public class alertDialogInternet {

    public alertDialogInternet(){

    }
    public static void showAlertConnection(final Context context){
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(context)
                        .setTitle("Internet is Settings")
                        .setMessage("Internet is not Enabled. Do you want to go to settings menu?")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                context.startActivities(new Intent[]{intent});
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        // Show the AlertDialog.
        alertDialogBuilder.show();
    }
}
