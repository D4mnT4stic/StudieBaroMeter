package imtpmd.studiebarometer.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import imtpmd.studiebarometer.Database.DatabaseHelper;
import imtpmd.studiebarometer.Database.DatabaseInfo;
import imtpmd.studiebarometer.R;
import imtpmd.studiebarometer.Settings;
/**
 * Created by SuperJoot on 23-5-2016.
 */
public class MainFragment extends Fragment {
    String DEFAULT = "";
    TextView welkomMainFrame2;
    TextView aantalECTS;
    TextView periode;
    TextView jaar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Gebruik sharedPreferences om de naam van de student op te halen.
        sharedPreferences = this.getActivity().getSharedPreferences("Shared_prefs", Context.MODE_PRIVATE);
        String voornaam = sharedPreferences.getString("voornaam", DEFAULT);

        welkomMainFrame2=(TextView)rootView.findViewById(R.id.welkomMainFrame2);
        aantalECTS=(TextView)rootView.findViewById(R.id.ectsAantal);
        periode=(TextView)rootView.findViewById(R.id.periodeNummer);
        jaar=(TextView)rootView.findViewById(R.id.jaarNummer);

        int berekenECTS = 0;
        int berekenStatus = 100;

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getActivity());
        final Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        if (rs!= null && rs.moveToFirst())
        {
            while (rs.isAfterLast() == false)
            {
                if (rs.getString(3).equals("v")  || rs.getString(3).equals("V")){
                    //Als er een voldoende is gehaald, voeg bijbehorende aantal ECTS dan toe aan het totale aantal.
                    berekenECTS += Integer.parseInt(rs.getString(2));
                }
                else if(rs.getString(3).equals("o")|| rs.getString(3).equals("O")){
                    berekenStatus -= 10;
                    if(rs.getString(1).equals("IIPXXXX"))
                    {
                        //Verander de status
                        berekenStatus -=10;
                    }
                }
                else if(rs.getString(3).equals("1"))
                {
                    // Doe niets als er geen cijfer is.
                }
                else{
                    double cijfer = Double.parseDouble(rs.getString(3).replaceAll(",","."));
                    if(cijfer <= 5.4)
                    {
                        //Verander de status
                        berekenStatus -= 10;
                    } else
                    {
                        //Als er een voldoende is gehaald, voeg bijbehorende aantal ECTS dan toe aan het totale aantal.
                        berekenECTS += Integer.parseInt(rs.getString(2));
                    }
                }

                rs.moveToNext();
            }
        }
        if (voornaam.equals(DEFAULT))
        {
            Toast.makeText(getActivity(), "Geen naam opgegeven", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Set naam en aantal studiepunten
            welkomMainFrame2.setText(voornaam);
            aantalECTS.setText(Integer.toString(berekenECTS));
        }

        // Haal data/tijd op
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_YEAR);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        int year = c.get(Calendar.YEAR);

        // Bepaal in welk schooljaar we zijn
        if(day < 243)
        {
            jaar.setText(Integer.toString(year - 1) + "/" + Integer.toString(year));
        }
        else
        {
            jaar.setText(Integer.toString(year)+ "/" + Integer.toString(year + 1));
        }

        //Bepaal in welke periode we zijn
        if(week >= 36 && week <= 46)
        {
            periode.setText("1");
        }
        else if(week >= 47 && week <= 5)
        {
            periode.setText("2");
        }
        else if(week >= 6 && week <= 16)
        {
            periode.setText("3");
        }
        else if(week >= 17 && week <= 28)
        {
            periode.setText("4");
        }
        else
        {
            periode.setText("Zomerperiode");
        }

        // Bereken welke kleur de achtergrond moet zijn
        if( berekenStatus <= 59)
        {
            rootView.setBackgroundColor(getResources().getColor(R.color.rood));
        } else if (berekenStatus >= 60 && berekenStatus <= 69)
        {
            rootView.setBackgroundColor(getResources().getColor(R.color.oranje));
        } else if (berekenStatus >= 70 && berekenStatus <= 79)
        {
            rootView.setBackgroundColor(getResources().getColor(R.color.geel));
        } else if (berekenStatus >= 80 && berekenStatus <= 89)
        {
            rootView.setBackgroundColor(getResources().getColor(R.color.geelgroen));
        } else if (berekenStatus >= 90)
        {
            rootView.setBackgroundColor(getResources().getColor(R.color.groen));
        }
        Log.d("BerekenStatus", Double.toString(berekenStatus));
        return rootView;
    }


    @Override
    public void onResume() { // Eventuele aangepaste cijfer reloaden

        super.onResume();
        Log.d("debug", "OnResume");
        FragmentManager fm = getFragmentManager();
        if (Settings.aanpassing == true){
            fm.beginTransaction().replace(R.id.content_frame,new MainFragment()).commit();
            Settings.aanpassing = false;

        }
    }
}
