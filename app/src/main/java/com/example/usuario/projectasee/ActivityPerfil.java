package com.example.usuario.projectasee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityPerfil extends AppCompatActivity {
    private ImageButton bnombre, bapellidos, bsexo, bedad, baltura, bpeso;
    private TextView edit;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;

    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences ( this );
        if(p.getString ( "listColor","" ).equals ( "Azul" )){
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        }else{
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.perfilactivity );

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        prefsEditor = getSharedPreferences("User", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("User", MODE_PRIVATE);

        /*Iniciamos el valor de todos los campos*/
        edit = (TextView) findViewById(R.id.TextNombre);
        edit.setText(prefs.getString("Nombre", null));

        edit = (TextView) findViewById(R.id.TextApellidos);
        edit.setText(prefs.getString("Apellidos", null));

        edit = (TextView) findViewById(R.id.TextSexo);
        edit.setText(prefs.getString("Sexo", null));

        edit = (TextView) findViewById(R.id.TextEdad);
        edit.setText(prefs.getString("Edad", null));

        edit = (TextView) findViewById(R.id.TextAltura);
        edit.setText(prefs.getString("Altura", null));

        edit = (TextView) findViewById(R.id.TextPeso);
        edit.setText(prefs.getString("Peso", null));

        setSupportActionBar ( toolbar );
        bnombre = (ImageButton) findViewById(R.id.botonNombre);
        bapellidos = (ImageButton) findViewById(R.id.botonApellidos);
        bsexo = (ImageButton) findViewById(R.id.botonSexo);
        bedad = (ImageButton) findViewById(R.id.botonEdad);
        baltura = (ImageButton) findViewById(R.id.botonAltura);
        bpeso = (ImageButton) findViewById(R.id.botonPeso);

        bnombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
                alertDialog.setTitle("Nombre");
                alertDialog.setMessage("Editar nombre:");

                final EditText input = new EditText(getBaseContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit = (TextView) findViewById(R.id.TextNombre);
                    prefsEditor.putString("Nombre", input.getText().toString());
                    prefsEditor.commit();
                    edit.setText(prefs.getString("Nombre", null));
                }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }

        );

        bapellidos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
            alertDialog.setTitle("Apellidos");
            alertDialog.setMessage("Editar apellidos:");

            final EditText input = new EditText(getBaseContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            alertDialog.setView(input);
            alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit = (TextView) findViewById(R.id.TextApellidos);
                    prefsEditor.putString("Apellidos", input.getText().toString());
                    prefsEditor.commit();
                    edit.setText(prefs.getString("Apellidos", null));
                }
             });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
            alertDialog.show();
                 }
                 }

        );
        bsexo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
                                        alertDialog.setTitle("Sexo");
                                        alertDialog.setMessage("Editar sexo:");

                                        final EditText input = new EditText(getBaseContext());
                                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                                        alertDialog.setView(input);
                                        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                edit = (TextView) findViewById(R.id.TextSexo);
                                                prefsEditor.putString("Sexo", input.getText().toString());
                                                prefsEditor.commit();
                                                edit.setText(prefs.getString("Sexo", null));
                                            }
                                        });

                                        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                        alertDialog.show();
                                    }
                                }

        );
        bedad.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
                                        alertDialog.setTitle("Edad");
                                        alertDialog.setMessage("Editar edad:");

                                        final EditText input = new EditText(getBaseContext());
                                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        alertDialog.setView(input);
                                        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                edit = (TextView) findViewById(R.id.TextEdad);
                                                prefsEditor.putString("Edad", input.getText().toString());
                                                prefsEditor.commit();
                                                edit.setText(prefs.getString("Edad", null));
                                            }
                                        });

                                        alertDialog.show();
                                    }
                                }

        );
        baltura.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View v) {
                                          AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
                                          alertDialog.setTitle("Altura");
                                          alertDialog.setMessage("Editar altura:");

                                          final EditText input = new EditText(getBaseContext());
                                          input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                          alertDialog.setView(input);
                                          alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  edit = (TextView) findViewById(R.id.TextAltura);
                                                  prefsEditor.putString("Altura", input.getText().toString());
                                                  prefsEditor.commit();
                                                  edit.setText(prefs.getString("Altura", null));
                                              }
                                          });
                                          alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int id) {
                                                  dialog.dismiss();
                                              }
                                          });

                                          alertDialog.show();
                                      }
                                  }

        );
        bpeso.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPerfil.this);
                                        alertDialog.setTitle("Peso");
                                        alertDialog.setMessage("Editar peso:");

                                        final EditText input = new EditText(getBaseContext());
                                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        alertDialog.setView(input);
                                        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                edit = (TextView) findViewById(R.id.TextPeso);
                                                prefsEditor.putString("Peso", input.getText().toString());
                                                prefsEditor.commit();
                                                edit.setText(prefs.getString("Peso", null));
                                            }
                                        });
                                        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });

                                        alertDialog.show();
                                    }
                                }

        );
    }

    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences ( this );
        if(p.getString ( "listColor","" ).equals ( "Azul" )){
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        }else{
            if (p.getString ( "listColor" , "" ).equals ( "Blanco" )) {
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
            }else{
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.VerdeBackground ) );
            }        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.menu_main , menu );*/
        getMenuInflater ().inflate ( R.menu.menu_main , menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.ic_action_setting:
                Log.i("setting","Configuracion");
                Intent i=new Intent (this,ActivityConfiguracion.class);
                startActivity ( i );
                Log.i("setting","Configuracion terminada");
                break;
            case  R.id.ic_action_perfil:
                Log.i("perfil","Perfil");
                i=new Intent (this,ActivityPerfil.class);
                startActivity ( i );
                Log.i("perfil","Perfil terminado");
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
