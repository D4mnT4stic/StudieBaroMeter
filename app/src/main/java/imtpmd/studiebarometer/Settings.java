package imtpmd.studiebarometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button opslaan;
    EditText ed1;
    public static final String MYPREFERENCES = "MyPrefs";
    public static final String Name ="";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ed1 = (EditText) findViewById(R.id.gebruikersNaam);
        opslaan = (Button) findViewById(R.id.buttonOpslaanNaam);
        loadSavedPreference();
        sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = ed1.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Name, n);
                editor.commit();
                Toast.makeText(Settings.this, "Naam opgeslagen!", Toast.LENGTH_LONG).show();
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        setTitle("Instellingen");
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void loadSavedPreference(){
        SharedPreferences userDetails = this.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String tempName = userDetails.getString(Name, "");  // (key, default)
        ed1.setText(tempName);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_instellingen) {
            startActivity(new Intent(Settings.this, Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hoofdmenu) {
            startActivity(new Intent(Settings.this, MainActivity.class));
        } else if (id == R.id.nav_vakkenencijfers) {
            startActivity(new Intent(Settings.this, Vakken.class));

        } else if (id == R.id.nav_advies) {
            startActivity(new Intent(Settings.this, Advies.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
