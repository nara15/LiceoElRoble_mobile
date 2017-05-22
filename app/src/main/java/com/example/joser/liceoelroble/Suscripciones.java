package com.example.joser.liceoelroble;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import Controlador.ContruccionFrameDinamico;
import Model.ObtencionDatosWeb;

public class Suscripciones extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("liceoelroble4088@gmail.com"));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "liceoelroble4088@gmail.com" });
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Ingrese el texto");
                startActivity(sendIntent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.suscripciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Inicio) {
            Intent intent = new Intent(Suscripciones.this, NoticiasPrincipal.class);
            startActivity(intent);
            finish();
        }/* else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        try {

/*            new AsyncTask<Void, Void, String>(){

                @Override
                protected Void doInBackground( Void... voids ) {
                    //Do things...
                    return null;
                }



            }.execute();
            */
            //Thread.sleep((long)2000);
            RelativeLayout rl=(RelativeLayout)findViewById(R.id.fondoRL);
           // ScrollView sv = new ScrollView(this);

            //int ancho = ((ConstraintLayout)findViewById(R.id.backLyout)).getWidth();
            ContruccionFrameDinamico cfd = new ContruccionFrameDinamico(getApplicationContext());
            /*int contador =0;
            while (cfd.getLiner()== null){
                Thread.sleep((long) 100);
                contador++;
                if (contador > 10)
                    break;
            }*/

            //LinearLayout llFondo = (LinearLayout)findViewById(R.id.llFondo);
            LinearLayout llNuevo = cfd.actualizarContenido();
            rl.setBackgroundColor(Color.GRAY);

            //llNuevo.setBackgroundColor(android.R.color.holo_blue_dark);
            if (llNuevo != null) {
                //sv.addView(llNuevo);
                rl.addView(llNuevo);
            }

            /**/
        /*    ControladorBD bd = new ControladorBD();
            JSONObject jo = new JSONObject();
            jo.put("Seccion", " 7-1");
            JSONArray ja = new JSONArray();
            ja.put(jo);
            JSONObject jo2 = new JSONObject();
            jo2.put("Seccion", " 11-3");
            ja.put(jo2);
            JSONObject mainObj = new JSONObject();
            mainObj.put("Secciones", ja);

           bd.escribirLinea(getApplication(),mainObj.toString());
            String ret = bd.getTexto(getApplicationContext());
            ObtencionDatosWeb.mostrarTexto(getApplicationContext(),ret);/**/
        }
        catch(Exception e){
            ObtencionDatosWeb.mostrarTexto(getApplicationContext(),"Error "+e.getMessage());

        }


        //MyMethod();
    }


}
