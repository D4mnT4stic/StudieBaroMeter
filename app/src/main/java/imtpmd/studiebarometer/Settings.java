package imtpmd.studiebarometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by SuperJoot on 23-5-2016.
 */
public class Settings extends AppCompatActivity {
    public static final String DEFAULT="Niet gevonden";

    EditText naamSettings;
    Spinner spinner_spec;
    public static Boolean aanpassing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        naamSettings=(EditText)findViewById(R.id.naamSettings);
        spinner_spec=(Spinner)findViewById(R.id.spinner_spec);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.hide();

        // Gebruik weer SharedPreferences
        final SharedPreferences sharedPreferences = getSharedPreferences("Shared_prefs", Context.MODE_PRIVATE);
        String voornaam = sharedPreferences.getString("voornaam", DEFAULT);
        int speci = sharedPreferences.getInt("specialisatie", 0);

        if (voornaam.equals(DEFAULT))
        {
            Toast.makeText(this, "Geen data gevonden", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Als er data is gevonden, set deze dan
            naamSettings.setText(voornaam);
            spinner_spec.setSelection(speci);
        }

        //Zorgt ervoor dat veranderingen meteen worden opgeslagen zonder button
        naamSettings.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    //Set de nieuwe data
                                                    String naam = naamSettings.getText().toString();
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("voornaam", naam);
                                                    editor.commit();
                                                    aanpassing = true;
                                                }
                                            }

        );

        spinner_spec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Sla de aangepaste specialisatie op
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("specialisatie", spinner_spec.getSelectedItemPosition());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
{
}
