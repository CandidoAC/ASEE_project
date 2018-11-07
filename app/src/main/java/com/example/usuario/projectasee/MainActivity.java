package com.example.usuario.projectasee;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById ( R.id. mytoolbar);
        setSupportActionBar ( toolbar );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater ().inflate ( R.menu.menu_main ,menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ic_action_setting) {
            return true;
        }

        return super.onOptionsItemSelected ( item );
    }
}
