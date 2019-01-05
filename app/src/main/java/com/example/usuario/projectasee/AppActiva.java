package com.example.usuario.projectasee;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import com.example.usuario.projectasee.Activity.MainActivity;

import java.util.Iterator;
import java.util.List;

public abstract class AppActiva /*extends Service*/{
    public AppActiva() {
    }

//    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException ( "Not yet implemented" );
    }

    public void obtenerAplicaciones(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        boolean enc=false;
        while(!enc && i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
            try {
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                if(c.toString ().equals ( "CCRun" )){
                    enc=true;
                }
            }catch(Exception e) {
                Log.d("Aplicaciones", e.getMessage());
            }
        }
        if(!enc){
//            this.getSystemService ( this.NOTIFICATION_SERVICE ).;
//            stopSelf ();
        }
    }
}
