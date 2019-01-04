package com.example.usuario.projectasee;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.usuario.projectasee.Activity.MainActivity;

public class Notification {

    Context c;
    private NotificationManager mManager;
    public Notification(Context context){
       c=context;
    }
    // Creates and displays a notification
    public void addNotification() {
        Intent i = new Intent ( c , MainActivity.class );
        PendingIntent pi = PendingIntent.getActivity ( c , 0 , i , 0 );
        android.app.Notification.Builder not = new android.app.Notification.Builder ( c );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i ( "Notificación" , "A.Oreo" );
            this.createChannels ();
            android.app.Notification.Builder nb = this.getAndroidChannelNotification ( "CCRun" , "La aplicación está activa" );

            this.getManager ().notify ( 101 , nb.build () );

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mManager = (NotificationManager) c.getSystemService ( Context.NOTIFICATION_SERVICE );
                not.setContentTitle ( "CCRun" ).
                        setContentText ( "La aplicación está activa" ).
                        setSmallIcon ( R.mipmap.icono_app_round ).
                        setLargeIcon ( BitmapFactory.decodeResource ( c.getResources () , R.mipmap.icono_app_round ) ).
                        setContentIntent ( pi ).
                        setVibrate ( new long[]{android.app.Notification.DEFAULT_VIBRATE} ).
                        setPriority ( android.app.Notification.PRIORITY_MAX );
                mManager.notify ( 0 , not.build () );
            }
        }


    }
    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel ( "CCRun" ,
                    "CCRun" , NotificationManager.IMPORTANCE_DEFAULT );
            androidChannel.enableLights ( true );
            androidChannel.enableVibration ( true );
            androidChannel.setLightColor ( Color.GREEN );
            androidChannel.setLockscreenVisibility ( android.app.Notification.VISIBILITY_PRIVATE );

            getManager ().createNotificationChannel ( androidChannel );
        }
    }

    public void destroyNotifications(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mManager.deleteNotificationChannel ( "CCRun" );
        }else{
            mManager.cancel ( 0 );
        }

    }
    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) c.getSystemService ( c.NOTIFICATION_SERVICE );
        }
        return mManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public android.app.Notification.Builder getAndroidChannelNotification(String title , String body) {
        return new android.app.Notification.Builder ( c , "CCRun" )
                .setContentTitle ( title )
                .setContentText ( body )
                .setSmallIcon ( R.mipmap.icono_app_round )
                .setAutoCancel ( true )
                .setOngoing ( true );
    }
}
