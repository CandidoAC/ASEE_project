package com.example.usuario.projectasee.Fragments;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.usuario.projectasee.Activity.ActivityConfiguracion;
import com.example.usuario.projectasee.Activity.MainActivity;
import com.example.usuario.projectasee.R;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SwitchPreference s=(SwitchPreference) findPreference ( "switchId");
        s.setOnPreferenceChangeListener(new SwitchPreference.OnPreferenceChangeListener () {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onPreferenceChange(Preference preference , Object newValue) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity ());
                if(!((SwitchPreference) preference).isChecked()){
                    Toast.makeText ( getActivity () , "Selected" , Toast.LENGTH_SHORT ).show ();
                    addNotification ();
                }else{
                    Toast.makeText ( getActivity () , "Not selected" , Toast.LENGTH_SHORT ).show ();
                }
                return true;
            }
        });
        ListPreference l=(ListPreference)findPreference ( "listColor" );
        l.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener () {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent=new Intent ( getActivity (),ActivityConfiguracion.class );
                startActivity ( intent );
                return true;
            }
        });
    }
    // Creates and displays a notification
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addNotification() {
        Intent i=new Intent ( getActivity (),MainActivity.class );
        PendingIntent pi=PendingIntent.getActivity ( getActivity (),0 , i,0);
        Notification.Builder not=new Notification.Builder ( getActivity () );
        not.setContentTitle ( "La aplicación está activa"  ).
                setSmallIcon ( R.mipmap.icono_app_round ).
                setLargeIcon ( BitmapFactory.decodeResource ( getResources (),R.mipmap.icono_app_round ) ).
                setContentIntent ( pi ).
                setVibrate ( new long[] {Notification.DEFAULT_VIBRATE}).
                setPriority ( Notification.PRIORITY_MAX );
        NotificationManager nm=(NotificationManager) getContext ().getSystemService ( Context.NOTIFICATION_SERVICE );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            Log.i("Not","ENTRAAAAAAAAAAa");
            nm.createNotificationChannel(channel);
        }
        nm.notify ( 0,not.build() );


    }
}
