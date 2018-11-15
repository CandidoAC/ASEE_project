package com.example.usuario.projectasee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SwitchPreference s=(SwitchPreference) findPreference ( "switchId");
        s.setOnPreferenceChangeListener(new SwitchPreference.OnPreferenceChangeListener () {
            @Override
            public boolean onPreferenceChange(Preference preference , Object newValue) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity ());
                if(!((SwitchPreference) preference).isChecked()){
                    Toast.makeText ( getActivity () , "Selected" , Toast.LENGTH_SHORT ).show ();
                }else{
                    addNotification ();
                    Toast.makeText ( getActivity () , "Not selected" , Toast.LENGTH_SHORT ).show ();
                }
                return true;
            }
        });
    }
    // Creates and displays a notification
    private void addNotification() {
        String CHANNEL_ID = "my_channel_01";
        Notification notification =
                new NotificationCompat.Builder(getActivity ())
                        .setSmallIcon(R.mipmap.icono_app_round)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setChannelId(CHANNEL_ID).build();


        NotificationManager mNotificationManager =
                (NotificationManager) getActivity ().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
