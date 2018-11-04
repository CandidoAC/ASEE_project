package com.example.usuario.projectasee;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById ( R.id. mytoolbar);
        setSupportActionBar ( toolbar );
    }

    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater ().inflate ( R.menu.menu_main ,menu );
        return true;
    }
}
