package com.example.mb7.sportappbp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mb7.sportappbp.Adapters.FitnessFragebogenViewAdapter;
import com.example.mb7.sportappbp.BusinessLayer.FitnessFragebogen;
import com.example.mb7.sportappbp.BusinessLayer.Fragebogen;
import com.example.mb7.sportappbp.MotivationMethods.MotivationMethod;
import com.example.mb7.sportappbp.R;
import com.example.mb7.sportappbp.UI_Controls.FragebogenListview;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Felix on 01.03.2017.
 */

public class ActivityFitnessFragebogen extends AppCompatActivity {
    private FitnessFragebogenViewAdapter adapter;

    FragebogenListview lststuhlaufstehen;
    FragebogenListview lsteinkaufskorb;
    FragebogenListview lstkistetragen;
    FragebogenListview lstsitup;
    FragebogenListview lstkofferheben;
    FragebogenListview lstkoffertragen;
    FragebogenListview lsthantelstemmen;

    FragebogenListview lstflottgehen;
    FragebogenListview lsttreppengehen;
    FragebogenListview lst2kmgehen;
    FragebogenListview lst1kmjoggen;
    FragebogenListview lst30minjoggen;
    FragebogenListview lst60minjoggen;
    FragebogenListview lstmarathon;

    FragebogenListview lstanziehen;
    FragebogenListview lstsitzendboden;
    FragebogenListview lstschuhebinden;
    FragebogenListview lstrueckenberuehren;
    FragebogenListview lststehendboden;
    FragebogenListview lstkopfknie;
    FragebogenListview lstbruecke;

    FragebogenListview lsttrepperunter;
    FragebogenListview lsteinbeinstand;
    FragebogenListview lstpurzelbaum;
    FragebogenListview lstballprellen;
    FragebogenListview lstzaunsprung;
    FragebogenListview lstkurveohnehand;
    FragebogenListview lstradschlagen;


    int kraftscore;
    int ausdauerscore;
    int beweglichkeitsscore;
    int koordinationsscore;
    int gesamtscore;

    private Firebase mRootRef;

    FitnessFragebogen fitnessFragebogen=null;

    boolean INSERT=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        Log.e("Oncreate enter", "Entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnessquestions);



        // Now read the extra key - val
        Intent iin= getIntent();
        Bundle extras = iin.getExtras();
        Log.e("Oncreate","We have reached it");
        if(extras!=null )
        {
            // read the datetime as this is the unique value in the db for the notification
            String notificationDate =(String) extras.get("NotificationDate");
            fitnessFragebogen=(FitnessFragebogen) iin.getSerializableExtra(getString(R.string.fitnessfragebogen));
            if (fitnessFragebogen!=null)
            {
                INSERT=false;
            }

            // now we have delete this notification from the db cause it is read if there has been any
            // we could enter this activity without clicking any notification!
            // we delete it from the database, because now the notification is read and it should not be shown in the notification tab cardview
            if (notificationDate!=null)
                removeNofiication(this, notificationDate);
        }

        mRootRef = new Firebase("https://sportapp-cbd6b.firebaseio.com/users");
        this.InitializeControlls();

        //super.onStart();
    }

    @Override
    protected void onResume(){super.onResume();}

    void setControlData(Fragebogen fragebogen)
    {

    }

    void removeNofiication(Context context, String notificationDate)
    {
        // get the current user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        // build the current URL
        Firebase ref = new Firebase("https://sportapp-cbd6b.firebaseio.com/" + "users/" + preferences.getString("logedIn","") + "/Notifications/" );
        ref.child(context.getString( R.string.fitnessfragebogen)).child(notificationDate).removeValue();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //set an other menu xml
        inflater.inflate(R.menu.menu_info_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Spericherbutton
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //check which icon was hidden in the toolbar
        switch (item.getItemId()){
            case R.id.icon_save:
                speicheralert();
                return super.onOptionsItemSelected(item);
            case R.id.icon_info:
                infoalert();

            case android.R.id.home:
                finish();
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

    private void infoalert() {
        AlertDialog.Builder infobuilder=new AlertDialog.Builder(this);
                infobuilder.setTitle(getString( R.string.Information));
                infobuilder.setMessage(getString( R.string.Fitnessfragebogeninfo));
                infobuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                infobuilder.show();
    }

    private void speicheralert() {
        AlertDialog.Builder speicherbuilder=new AlertDialog.Builder(this);
        speicherbuilder.setTitle(getString( R.string.Ergebnis));
        speicherbuilder.setMessage(
                getString( R.string.Kraftscore) +" " + kraftscoring() +" " + getString( R.string.von_Punkten)+"\n" +
                        getString( R.string.Ausdauerscore) +" " + ausdauerscoring() +" "  + getString( R.string.von_Punkten)+ "\n" +
                        getString( R.string.Bewglichkeitsscore) +" " + bewglichkeitsscoring()+" " + getString( R.string.von_Punkten) + "\n" +
                        getString( R.string.Koordinationsscore) +" " + koordinationscoring()+" "  + getString( R.string.von_Punkten)+ "\n" +
                        getString( R.string.Gesamtscore)+" " + scoringwert()+" " + getString( R.string.von_Gesamtpunkten) );
        speicherbuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveData();
                finish();
                Toast ausgabe= Toast.makeText(ActivityMain.activityMain,
                        getString( R.string.Erfolgreich_gespeichert),Toast.LENGTH_LONG);
                ausgabe.show();

            }

        });
        speicherbuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        speicherbuilder.show();
    }

    private FitnessFragebogen getData(){
        FitnessFragebogen fitnessfragebogen=new FitnessFragebogen();

        fitnessfragebogen.scorekraft=kraftscoring();
        fitnessfragebogen.scoreausdauer=ausdauerscoring();
        fitnessfragebogen.scorebeweglichkeit=bewglichkeitsscoring();
        fitnessfragebogen.scorekoordination=koordinationscoring();
        fitnessfragebogen.scoregesamt=scoringwert();

        fitnessfragebogen.stuhlaufstehen=lststuhlaufstehen.getIndexFitness();
        fitnessfragebogen.einkaufskorb=lsteinkaufskorb.getIndexFitness();
        fitnessfragebogen.kistetragen=lstkistetragen.getIndexFitness();
        fitnessfragebogen.situp=lstsitup.getIndexFitness();
        fitnessfragebogen.kofferheben=lstkofferheben.getIndexFitness();
        fitnessfragebogen.koffertragen=lstkoffertragen.getIndexFitness();
        fitnessfragebogen.hantelstemmen=lsthantelstemmen.getIndexFitness();

        fitnessfragebogen.flottgehen=lstflottgehen.getIndexFitness();
        fitnessfragebogen.treppengehen=lsttreppengehen.getIndexFitness();
        fitnessfragebogen.zweikmgehen=lst2kmgehen.getIndexFitness();
        fitnessfragebogen.einkmjoggen=lst1kmjoggen.getIndexFitness();
        fitnessfragebogen.dreißigminjoggen=lst30minjoggen.getIndexFitness();
        fitnessfragebogen.sechzigminjoggen=lst60minjoggen.getIndexBSA2();
        fitnessfragebogen.marathon=lstmarathon.getIndexFitness();

        fitnessfragebogen.anziehen=lstanziehen.getIndexFitness();
        fitnessfragebogen.sitzendboden=lstsitzendboden.getIndexFitness();
        fitnessfragebogen.schuhebinden=lstschuhebinden.getIndexFitness();
        fitnessfragebogen.rueckenberuehren=lstrueckenberuehren.getIndexFitness();
        fitnessfragebogen.stehendboden=lststehendboden.getIndexFitness();
        fitnessfragebogen.kopfknie=lstkopfknie.getIndexFitness();
        fitnessfragebogen.bruecke=lstbruecke.getIndexFitness();

        fitnessfragebogen.trepperunter=lsttrepperunter.getIndexFitness();
        fitnessfragebogen.einbeinstand=lsteinbeinstand.getIndexFitness();
        fitnessfragebogen.purzelbaum=lstpurzelbaum.getIndexFitness();
        fitnessfragebogen.ballprellen=lstballprellen.getIndexFitness();
        fitnessfragebogen.zaunsprung=lstzaunsprung.getIndexFitness();
        fitnessfragebogen.kurveohnehand=lstkurveohnehand.getIndexFitness();
        fitnessfragebogen.radschlagen=lstradschlagen.getIndexFitness();

        if(INSERT){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        fitnessfragebogen.Date = sdf.format(new Date());}
        else
        {
            fitnessfragebogen.Date=this.fitnessFragebogen.FirebaseDate;

        }


        return fitnessfragebogen;
    }

    private boolean SaveData() {
        FitnessFragebogen fitnessfragebogen = getData();
        if (INSERT)
        ActivityMain.mainUser.InsertFitnessFragebogen(fitnessfragebogen);
        else
            ActivityMain.mainUser.UpdateFitnessFragebogen(fitnessfragebogen);

        return true;
    }



    /**
     * Initialisiert die Listviews -> Über Funktionen aus Fragebogenlistview
     */
    private void InitializeControlls(){

        // set the listivew
        // first create the adapters


        lststuhlaufstehen = (FragebogenListview)findViewById( R.id.lvstuhlaufstehen);
        lsteinkaufskorb = (FragebogenListview)findViewById( R.id.lveinkaufskorb);
        lstkistetragen = (FragebogenListview)findViewById( R.id.lvkistetragen);
        lstsitup=(FragebogenListview)findViewById( R.id.lvsitup);
        lstkofferheben=(FragebogenListview)findViewById( R.id.lvkofferheben);
        lstkoffertragen=(FragebogenListview)findViewById( R.id.lvkoffertragen);
        lsthantelstemmen=(FragebogenListview)findViewById( R.id.lvhantelstemmen);

        lstflottgehen=(FragebogenListview)findViewById( R.id.lvflottgehen);
        lsttreppengehen=(FragebogenListview)findViewById( R.id.lvtreppengehen);
        lst2kmgehen=(FragebogenListview)findViewById( R.id.lv2kmgehen);
        lst1kmjoggen=(FragebogenListview)findViewById( R.id.lv1kmjoggen);
        lst30minjoggen=(FragebogenListview)findViewById( R.id.lv30minjoggen);
        lst60minjoggen=(FragebogenListview)findViewById( R.id.lv60minjoggen);
        lstmarathon=(FragebogenListview)findViewById( R.id.lvmarathon);

        lstanziehen=(FragebogenListview)findViewById( R.id.lvanziehen);
        lstsitzendboden=(FragebogenListview)findViewById( R.id.lvsitzendboden);
        lstschuhebinden=(FragebogenListview)findViewById( R.id.lvschuhebinden);
        lstrueckenberuehren=(FragebogenListview)findViewById( R.id.lvrueckenberuehren);
        lststehendboden=(FragebogenListview)findViewById( R.id.lvstehendboden);
        lstkopfknie=(FragebogenListview)findViewById( R.id.lvkopfknie);
        lstbruecke=(FragebogenListview)findViewById( R.id.lvbruecke);

        lsttrepperunter=(FragebogenListview)findViewById( R.id.lvtrepperunter);
        lsteinbeinstand=(FragebogenListview)findViewById( R.id.lveinbeinstand);
        lstpurzelbaum=(FragebogenListview)findViewById( R.id.lvpurzelbaum);
        lstballprellen=(FragebogenListview)findViewById( R.id.lvballprellen);
        lstzaunsprung=(FragebogenListview)findViewById( R.id.lvzaunsprung);
        lstkurveohnehand=(FragebogenListview)findViewById( R.id.lvkurveohnehand);
        lstradschlagen=(FragebogenListview)findViewById( R.id.lvradschlagen);

        /**
         * Adapter setzen
         */
        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.auf_einem_Stuhl_sitzend_ohne_Hilfe_aufstehen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.stuhlaufstehen!=null? fitnessFragebogen.stuhlaufstehen:-1);
        lststuhlaufstehen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_schweren_Einkaufskorb_über_mehrere_Etagen_tragen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.einkaufskorb!=null? fitnessFragebogen.einkaufskorb:-1);
        lsteinkaufskorb.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.eine_volle_Bierkiste_in_den_Keller_tragen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.kistetragen!=null? fitnessFragebogen.kistetragen:-1);
        lstkistetragen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.aus_der_Rückenlage_ohne_Hilfe_der_Arme_den_Oberkörper_aufrichten));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.situp!=null? fitnessFragebogen.situp:-1);
        lstsitup.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_schweren_Koffer_über_Kopfhöhe_heben));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.kofferheben!=null? fitnessFragebogen.kofferheben:-1);
        lstkofferheben.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.zwei_schwere_Koffer_über_mehrere_Etagen_tragen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.koffertragen!=null? fitnessFragebogen.koffertragen:-1);
        lstkoffertragen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.eine_Hantel_mit_mehr_als_Ihrem_Körpergewicht_hochstemmen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.hantelstemmen!=null? fitnessFragebogen.hantelstemmen:-1);
        lsthantelstemmen.setAdapter(adapter);


        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.um_mehrere_Blocks_flott_gehen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.flottgehen!=null? fitnessFragebogen.flottgehen:-1);
        lstflottgehen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.mehrere_Treppen_hochgehen_ohne_sich_auszuruhen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.treppengehen!=null? fitnessFragebogen.treppengehen:-1);
        lsttreppengehen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.zwei_Kilometer_schnell_gehen_ohne_sich_auszuruhen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.zweikmgehen!=null? fitnessFragebogen.zweikmgehen:-1);
        lst2kmgehen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_Kilometer_ohne_Pause_joggen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.einkmjoggen!=null? fitnessFragebogen.einkmjoggen:-1);
        lst1kmjoggen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.Minuten_ohne_Pause_joggen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.dreißigminjoggen!=null? fitnessFragebogen.dreißigminjoggen:-1);
        lst30minjoggen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.eine_Stunde_ohne_Pause_joggen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.sechzigminjoggen!=null? fitnessFragebogen.sechzigminjoggen:-1);
        lst60minjoggen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_Marathon_laufen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.marathon!=null? fitnessFragebogen.marathon:-1);
        lstmarathon.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_engen_Pulli_und_Socken_alleine_aus_und_anziehen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.anziehen!=null? fitnessFragebogen.anziehen:-1);
        lstanziehen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.auf_einem_Stuhl_sitzend_mit_den_Händen_den_Boden_erreichen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.sitzendboden!=null? fitnessFragebogen.sitzendboden:-1);
        lstsitzendboden.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.im_Stehen_Schuhe_binden));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.schuhebinden!=null? fitnessFragebogen.schuhebinden:-1);
        lstschuhebinden.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.mit_der_Hand_von_unten_auf_dem_Rücken_ein_Schulterblatt_berühren));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.rueckenberuehren!=null? fitnessFragebogen.rueckenberuehren:-1);
        lstrueckenberuehren.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.aus_dem_Stand_mit_den_Händen_den_Boden_erreichen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.stehendboden!=null? fitnessFragebogen.stehendboden:-1);
        lststehendboden.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.im_Stehen_mit_dem_Kopf_die_gestreckten_Knie_berühren));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.kopfknie!=null? fitnessFragebogen.kopfknie:-1);
        lstkopfknie.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.rückwärts_bis_in_die_Brücke_abbeugen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.bruecke!=null? fitnessFragebogen.bruecke:-1);
        lstbruecke.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.eine_Treppe_hinab_gehen_ohne_sich_festzuhalten));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.trepperunter!=null? fitnessFragebogen.trepperunter:-1);
        lsttrepperunter.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.auf_einem_Bein_stehen_ohne_sich_festzuhalten));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.einbeinstand!=null? fitnessFragebogen.einbeinstand:-1);
        lsteinbeinstand.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.einen_Purzelbaum));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.purzelbaum!=null? fitnessFragebogen.purzelbaum:-1);
        lstpurzelbaum.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.im_schnellen_Gehen_einen_Ball_prellen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.ballprellen!=null? fitnessFragebogen.ballprellen:-1);
        lstballprellen.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.mit_Abstützen_über_einen_ein_Meter_hohen_Zaun_springen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.zaunsprung!=null? fitnessFragebogen.zaunsprung:-1);
        lstzaunsprung.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.freihändig_mit_dem_Fahrrad_um_eine_Kurve_fahren));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.kurveohnehand!=null? fitnessFragebogen.kurveohnehand:-1);
        lstkurveohnehand.setAdapter(adapter);

        adapter = new FitnessFragebogenViewAdapter(this);
        adapter.setFitnessFragebogen(fitnessFragebogen, getString(R.string.ein_Rad_schlagen));
        adapter.setSelectedIndex(fitnessFragebogen!=null && fitnessFragebogen.radschlagen!=null? fitnessFragebogen.radschlagen:-1);
        lstradschlagen.setAdapter(adapter);

        /**
         * Initialsieren
         */

        lststuhlaufstehen.InitializeFitness();
                lsteinkaufskorb.InitializeFitness();
        lstkistetragen.InitializeFitness();
                lstsitup.InitializeFitness();
        lstkofferheben.InitializeFitness();
                lstkoffertragen.InitializeFitness();
        lsthantelstemmen.InitializeFitness();

                lstflottgehen.InitializeFitness();
        lsttreppengehen.InitializeFitness();
                lst2kmgehen.InitializeFitness();
        lst1kmjoggen.InitializeFitness();
                lst30minjoggen.InitializeFitness();
        lst60minjoggen.InitializeFitness();
                lstmarathon.InitializeFitness();

        lstanziehen.InitializeFitness();
                lstsitzendboden.InitializeFitness();
        lstschuhebinden.InitializeFitness();
                lstrueckenberuehren.InitializeFitness();
        lststehendboden.InitializeFitness();
                lstkopfknie.InitializeFitness();
        lstbruecke.InitializeFitness();

                lsttrepperunter.InitializeFitness();
        lsteinbeinstand.InitializeFitness();
                lstpurzelbaum.InitializeFitness();
        lstballprellen.InitializeFitness();
                lstzaunsprung.InitializeFitness();
        lstkurveohnehand.InitializeFitness();
                lstradschlagen.InitializeFitness();
}


private int scoringrechnung(int index){
    switch(index){
        case 4: return 5;
        case 3: return 4;
        case 2: return 3;
        case 1: return 2;
        case 0: return 1;
        default: return 0;
    }
}

public int kraftscoring(){
    kraftscore=scoringrechnung(lststuhlaufstehen.getIndexFitness())+scoringrechnung(lsteinkaufskorb.getIndexFitness())+scoringrechnung(lstkistetragen.getIndexFitness())+scoringrechnung(lstsitup.getIndexFitness())+scoringrechnung(lstkofferheben.getIndexFitness())+scoringrechnung(lstkoffertragen.getIndexFitness())+scoringrechnung(lsthantelstemmen.getIndexFitness());
    return kraftscore;
}

private int ausdauerscoring(){
    ausdauerscore=scoringrechnung(lstflottgehen.getIndexFitness())+scoringrechnung(lsttreppengehen.getIndexFitness())+scoringrechnung(lst2kmgehen.getIndexFitness())+scoringrechnung(lst1kmjoggen.getIndexFitness())+scoringrechnung(lst30minjoggen.getIndexFitness())+scoringrechnung(lst60minjoggen.getIndexFitness())+scoringrechnung(lstmarathon.getIndexFitness());
    return ausdauerscore;
    }

    private int bewglichkeitsscoring(){
        beweglichkeitsscore=scoringrechnung(lstanziehen.getIndexFitness())+scoringrechnung(lstsitzendboden.getIndexFitness())+scoringrechnung(lstschuhebinden.getIndexFitness())+scoringrechnung(lstrueckenberuehren.getIndexFitness())+scoringrechnung(lststehendboden.getIndexFitness())+scoringrechnung(lstkopfknie.getIndexFitness())+scoringrechnung(lstbruecke.getIndexFitness());
        return beweglichkeitsscore;
    }

    private int koordinationscoring(){
        koordinationsscore=scoringrechnung(lsttrepperunter.getIndexFitness())+scoringrechnung(lsteinbeinstand.getIndexFitness())+scoringrechnung(lstpurzelbaum.getIndexFitness())+scoringrechnung(lstballprellen.getIndexFitness())+scoringrechnung(lstzaunsprung.getIndexFitness())+scoringrechnung(lstkurveohnehand.getIndexFitness())+scoringrechnung(lstradschlagen.getIndexFitness());
        return koordinationsscore;
    }

    private int scoringwert(){
               gesamtscore=kraftscoring()+ausdauerscoring()+bewglichkeitsscoring()+koordinationscoring();

        return gesamtscore;
    }

}

