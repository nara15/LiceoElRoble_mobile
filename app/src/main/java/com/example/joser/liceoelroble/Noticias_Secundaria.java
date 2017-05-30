package com.example.joser.liceoelroble;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class Noticias_Secundaria
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoticiaListFragment.OnNoticiaListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
        {
            NoticiaListFragment fragment = NoticiaListFragment.newInstance();
            fragment.setURL_Rest("http://www.liceoelroble.com/MODEL/NoticiasGeneralREST.php");
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.app_noticias_secundaria, fragment, "noticiaList")
                    .commit();
        }

        setContentView(R.layout.activity_noticias__secundaria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Inicio) {
            Intent intent = new Intent(Noticias_Secundaria.this, NoticiasPrincipal.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnNoticiaListenerSelected(String image, String name, String description)
    {
        final NoticiaDetailFragment detailFragment =
                NoticiaDetailFragment.newInstance(image, name);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_noticias_secundaria, detailFragment, "detalleNoticia")
                .addToBackStack(null)
                .commit();
    }
}
