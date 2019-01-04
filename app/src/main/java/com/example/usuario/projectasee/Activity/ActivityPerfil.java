package com.example.usuario.projectasee.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario.projectasee.R;

import java.io.FileNotFoundException;

public class ActivityPerfil extends AppCompatActivity {
    private static final int SELECT_PICTURE = 0;
    private TableRow bnombre, bapellidos, bsexo, bedad, baltura, bpeso;
    private TextView edit;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;
    private ImageView imageView;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        if (p.getString("listColor", "").equals("Azul")) {
            findViewById(R.id.main_content).setBackgroundColor(getResources().getColor(R.color.defaultBackground));
        } else {
            if (p.getString("listColor", "").equals("Blanco")) {
                findViewById(R.id.main_content).setBackgroundColor(getResources().getColor(R.color.BlancoBackground));
            } else {
                findViewById(R.id.main_content).setBackgroundColor(getResources().getColor(R.color.VerdeBackground));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfilactivity);
        imageView = findViewById(R.id.perfilIcon);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        prefsEditor = getSharedPreferences("User", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("User", MODE_PRIVATE);

        /*Iniciamos el valor de todos los campos*/
        edit = findViewById(R.id.TextNombre);
        edit.setText(prefs.getString("Nombre", "Luke"));

        edit = findViewById(R.id.TextApellidos);
        edit.setText(prefs.getString("Apellidos", "Skywalker"));

        edit = findViewById(R.id.TextSexo);
        edit.setText(prefs.getString("Sexo", "H"));

        edit = findViewById(R.id.TextEdad);
        edit.setText(prefs.getString("Edad", "50"));

        edit = findViewById(R.id.TextAltura);
        edit.setText(prefs.getString("Altura", "165"));

        edit = findViewById(R.id.TextPeso);
        edit.setText(prefs.getString("Peso", "70"));

        if (!prefs.getString("Foto", "").equals("")) {
            String path = prefs.getString("Foto", "");
            Uri uri = Uri.parse(path);
            imageView.setImageURI(uri);
        }

        setSupportActionBar(toolbar);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectImage();
            }
        });
        bnombre = findViewById(R.id.Nombre);
        bapellidos = findViewById(R.id.Apellidos);
        bsexo = findViewById(R.id.Sexo);
        bedad = findViewById(R.id.Edad);
        baltura = findViewById(R.id.Altura);
        bpeso = findViewById(R.id.Peso);

        bnombre.setOnClickListener(new View.OnClickListener() {
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
                                                   edit = findViewById(R.id.TextNombre);
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

        bapellidos.setOnClickListener(new View.OnClickListener() {
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
                                                      edit = findViewById(R.id.TextApellidos);
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
                                                 edit = findViewById(R.id.TextSexo);
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
        bedad.setOnClickListener(new View.OnClickListener() {
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
                                                 edit = findViewById(R.id.TextEdad);
                                                 prefsEditor.putString("Edad", input.getText().toString());
                                                 prefsEditor.commit();
                                                 edit.setText(prefs.getString("Edad", null));
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
        baltura.setOnClickListener(new View.OnClickListener() {
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
                                                   edit = findViewById(R.id.TextAltura);
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
        bpeso.setOnClickListener(new View.OnClickListener() {
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
                                                 edit = findViewById(R.id.TextPeso);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_action_setting:
                Log.i("setting", "Configuracion");
                Intent i = new Intent(this, ActivityConfiguracion.class);
                startActivity(i);
                Log.i("setting", "Configuracion terminada");
                break;
            case R.id.ic_action_perfil:
                Log.i("perfil", "Perfil");
                i = new Intent(this, ActivityPerfil.class);
                startActivity(i);
                Log.i("perfil", "Perfil terminado");
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                Uri result = data.getData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    this.getContentResolver().takePersistableUriPermission(result, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(result));
                prefsEditor.putString("Foto", String.valueOf(result));
                prefsEditor.commit();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

//    private Bitmap getPath(Uri uri) {
//
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//
//        // Convert file path into bitmap image using below line.
//        Bitmap bitmap = BitmapFactory.decodeFile(result);
//        return bitmap;
//    }

    public void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        final int takeFlags = intent.getFlags()
//                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


//    private class GetImagen extends AsyncTask<Uri, Void, Bitmap> {
//        private Bitmap bitmap;
//
//        public Bitmap getBitmapFromUri(Uri uri) throws IOException {
//            ParcelFileDescriptor parcelFileDescriptor =
//                    getContentResolver().openFileDescriptor(uri, "r");
//            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//            parcelFileDescriptor.close();
//            return image;
//        }
//
//        @Override
//        protected Bitmap doInBackground(Uri... uri) {
//            try {
//                bitmap = getBitmapFromUri(uri[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//
//        }
//    }

}
