package com.example.usuario.projectasee.Fragments;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

public class SettingFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SwitchPreference s=(SwitchPreference) findPreference ( "switchId");

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
}
