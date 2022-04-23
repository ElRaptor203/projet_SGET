package com.example.sget.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;

import com.example.sget.data.Donnee;
import com.example.sget.R;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;






import tech.gusavila92.websocketclient.WebSocketClient;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Context context;
    public static WebSocketClient webSocketClient;
    public TextView descrip_semaine;
    public TextView matiere1;
    public TextView matiere2;
    public TextView matiere3;
    public TextView matiere4;
    public TextView enseignent1;
    public TextView enseignent2;
    public TextView enseignent3;
    public TextView enseignent4;
    public static Boolean etats1 = true, etatssemaine = false;
    private static final String DATE_FORMAT = "EEE-d-MMM-yyyy-HH:mm:ss";
    private SimpleDateFormat dateFormatter;
    private String date = "";
    Spinner spinnerjour;
    Spinner spinnersemaine;
    public static List<String> jour;
    public static int nbre_enter_in_spinner = 0;
    public static int nbre_enter_in_spinner_semaine = 0;
    public static List<String> semaine = new ArrayList<String>();
    public ArrayAdapter<String> dataAdapterjour;
    public CardView cardViewmatiere1;
    public CardView cardViewmatiere2;
    public CardView cardViewmatiere3;
    public CardView cardViewmatiere4;
    public CardView cardViewmatierepause;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        createWebSocketClient();

        // Spinner element
        etats1 = true;
        nbre_enter_in_spinner = 0;
        nbre_enter_in_spinner_semaine = 0;
        spinnerjour = (Spinner) root.findViewById(R.id.spinnerjour);
        spinnersemaine = (Spinner) root.findViewById(R.id.spinnersemaine);
        descrip_semaine = (TextView) root.findViewById(R.id.descrip_semaine);
        matiere1 = (TextView) root.findViewById(R.id.matiere1);
        matiere2 = (TextView) root.findViewById(R.id.matiere2);
        matiere3 = (TextView) root.findViewById(R.id.matiere3);
        matiere4 = (TextView) root.findViewById(R.id.matiere4);
        enseignent1 = (TextView) root.findViewById(R.id.enseignent1);
        enseignent2 = (TextView) root.findViewById(R.id.enseignent2);
        enseignent3 = (TextView) root.findViewById(R.id.enseignent3);
        enseignent4 = (TextView) root.findViewById(R.id.enseignent4);

        cardViewmatiere1 = (CardView) root.findViewById(R.id.cardmatiere1);
        cardViewmatiere2 = (CardView) root.findViewById(R.id.cardmatiere2);
        cardViewmatiere3 = (CardView) root.findViewById(R.id.cardmatiere3);
        cardViewmatiere4 = (CardView) root.findViewById(R.id.cardmatiere4);
        cardViewmatierepause = (CardView) root.findViewById(R.id.pause);
        // cardViewmatiere1.setBackgroundColor(R.color.violet);

        // date
        dateFormatter = new SimpleDateFormat(
                DATE_FORMAT, Locale.US);
        date = dateFormatter.format(new Date()).toString();
        Log.i("DATA", "date : "+date);

        // Spinner click listener
        spinnerjour.setOnItemSelectedListener(DashboardFragment.this);
        spinnersemaine.setOnItemSelectedListener(DashboardFragment.this);

        // Spinner Drop down elements
        jour = new ArrayList<String>();
        jour.add("lundi");
        jour.add("mardi");
        jour.add("mercredi");
        jour.add("jeudi");
        jour.add("vendredi");
        jour.add("samedi");


        //   DashboardFragment.semaine.add("1");
        if (etatssemaine){
            semaine.clear();
            etatssemaine = false;
            int NbreSemaine = 0 ;
            Log.i("HomeFragment","NBRSEMAINE DETAIL : "+(Donnee.NBRSEMAINE));
            NbreSemaine = Integer.parseInt(Donnee.NBRSEMAINE);
            for (int i = 0; i < NbreSemaine; i++){
                //   Log.i("HomeFragment","NBRSEMAINE DETAIL : "+(i));
                semaine.add(""+(i+1));
            }
            Donnee.NBRSEMAINE =  "";
        }

//        semaine.add("1");
//        semaine.add("2");


        // Creating adapter for spinner
        dataAdapterjour = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, jour);
        ArrayAdapter<String> dataAdaptersemaine = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, semaine);

        // Drop down layout style - list view with radio button
        dataAdapterjour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdaptersemaine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data ada pter to spinner
        spinnerjour.setAdapter(dataAdapterjour);
        spinnersemaine.setAdapter(dataAdaptersemaine);

        return root;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        boolean listejours = false;
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        for (int i = 0; i < jour.size(); i++){
            if (jour.get(i).equals(item)){
                listejours = true;
                break;
            }
        }

        if (Donnee.isconnected){

            if (nbre_enter_in_spinner == 3){
                if (listejours){
                    listejours = false;
                    Log.i("data", "jour : "+jour);
                    Log.i("data", "semaine : "+semaine);
                    Log.i("data", "Donnee.FILIERE : "+Donnee.FILIERE);
                    webSocketClient.send("senddata"+"@"+item+"&"+spinnersemaine.getSelectedItem()+"#"+Donnee.FILIERE+"$");
                  //  Toast.makeText(getContext(),"vous ete entrer dans jours",Toast.LENGTH_SHORT).show();
                } else {
                        if (nbre_enter_in_spinner_semaine == 1){
                            spinnerjour.setSelection(0);
                            webSocketClient.send("senddata"+"@"+spinnerjour.getSelectedItem()+"&"+spinnersemaine.getSelectedItem()+"#"+Donnee.FILIERE+"$");
                            // Toast.makeText(getContext(),"vous ete entrer dans semmaine",Toast.LENGTH_SHORT).show();
                        } else {
                            nbre_enter_in_spinner_semaine++;
                        }

                   }
            }
            else {
                nbre_enter_in_spinner++;
      //          Toast.makeText(getContext(),"nombre d'iteration : "+nbre_enter_in_spinner,Toast.LENGTH_SHORT).show();
//                Log.i("dashboardfragment","envoie de la permition");
//                webSocketClient.send("sendpermitiontoreadspinner"+"@");
            }

        }else {
            Toast.makeText(getContext(),"svp veuillez- vous connecter",Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI(Donnee.PATHWEBSOKET);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("-----------","----------------------------");
                Log.i("WebSocket HomeFragment", "Session HomeFragment is starting ");
                Log.i("-----------","----------------------------");

                if (Donnee.isconnected){
                    if (etats1){
                        Log.i("DashboardFragment15","envoie de donne au server");
                        webSocketClient.send("DashboardFragment3"+"@"+Donnee.MATRICULE+"&");
                        etats1 = false;
                    } else {
                        webSocketClient.send("etatsmatiere"+"@");
                    }
                }else {
                    Toast.makeText(getContext(),"svp veuillez- vous connecter",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextReceived(String s) {


                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint({"NewApi", "ResourceAsColor"})
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        String message = s;

                        String message0 = message.substring(0,message.indexOf("@"));
                        Log.i("dashboardfragment","message0 : "+message0);

                        if (message0.equals("receptioninit")){
                            String  message1 = message.substring(message0.length()+1,message.indexOf("&"));
                            String  message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
                            String  message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
                            String   message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.indexOf("%"));
                            String   message5 = message.substring(message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+5,message.indexOf("¼"));
                            String   message6 = message.substring(message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+6,message.indexOf("½"));
                            String   message7 = message.substring(message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+7,message.indexOf("~"));
                            String   message8 = message.substring(message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+8,message.indexOf("*"));
                            String   message9 = message.substring(message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+9,message.indexOf("¥"));
                            String   message10 = message.substring(message9.length()+message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+10,message.indexOf("^"));
                            String   message11 = message.substring(message10.length()+message9.length()+message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+11,message.indexOf("?"));
                            String   message12 = message.substring(message11.length()+message10.length()+message9.length()+message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+12,message.indexOf("!"));

                            descrip_semaine.setText(message1);
                            matiere1.setText(message2);
                            enseignent1.setText(message3);
                            matiere2.setText(message4);
                            enseignent2.setText(message5);
                            matiere3.setText(message6);
                            enseignent3.setText(message7);
                            matiere4.setText(message8);
                            enseignent4.setText(message9);
                            spinnersemaine.setSelection(Integer.parseInt(message10)-1);
                            for (int i = 0; i < jour.size(); i++) {
                                if (jour.get(i).equals(message11)){
                                    spinnerjour.setSelection(i);
                                }
                            }
                            Log.i("dashboardfragment","jour locale : "+message11);
                            Log.i("dashboardfragment","heure locale : "+message12);

                            getcardbagrown(message12);
                        }

                        if (message0.equals("receptiondatareussie")){
                            String  message1 = message.substring(message0.length()+1,message.indexOf("&"));
                            String  message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
                            String  message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
                            String   message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.indexOf("%"));
                            String   message5 = message.substring(message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+5,message.indexOf("¼"));
                            String   message6 = message.substring(message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+6,message.indexOf("½"));
                            String   message7 = message.substring(message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+7,message.indexOf("~"));
                            String   message8 = message.substring(message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+8,message.indexOf("*"));
                            String   message9 = message.substring(message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+9,message.indexOf("¥"));
                            String message10 = message.substring(message9.length()+message8.length()+message7.length()+message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+10,message.length());

                            descrip_semaine.setText(message1);
                            matiere1.setText(message2);
                            enseignent1.setText(message3);
                            matiere2.setText(message4);
                            enseignent2.setText(message5);
                            matiere3.setText(message6);
                            enseignent3.setText(message7);
                            matiere4.setText(message8);
                            enseignent4.setText(message9);

                            Log.i("dashboardfragment","message1: "+message1);
                            Log.i("dashboardfragment","message2 : "+message2);
                            Log.i("dashboardfragment","message3 : "+message3);
                            Log.i("dashboardfragment","message4 : "+message4);
                            Log.i("dashboardfragment","message5 : "+message5);
                            Log.i("dashboardfragment","message6 : "+message6);
                            Log.i("dashboardfragment","message7 : "+message7);
                            Log.i("dashboardfragment","message8 : "+message8);
                            Log.i("dashboardfragment","message9 : "+message9);

                            getcardbagrown(message10);
                        }

                        if (message0.equals("echecconnection")){

                        }

                        if (message0.equals("teakstosendautorisation")){
                            Log.i("dashboardfragment","reception de la permition");
                        }

                        if (message0.equals("verifieretatsmatiere")){
                            String  message1 = message.substring(message0.length()+1,message.indexOf("&"));
                            Log.i("dashboardfragment","message1: "+message1);
                            getcardbagrown(message1);
                           //   Toast.makeText(getContext(),"verifieretatsmatiere",Toast.LENGTH_SHORT).show();
                        }

                        if (message0.equals("echeqreceptioninit")){
                           // nobeginfirstspiner = true;

                            descrip_semaine.setText("pas d'emploie de temps disponible pour ce jour");
                            matiere1.setText("syteme de gestion des emploies de temps");
                            enseignent1.setText("SGET");
                            matiere2.setText("syteme de gestion des emploies de temps");
                            enseignent2.setText("SGET");
                            matiere3.setText("syteme de gestion des emploies de temps");
                            enseignent3.setText("SGET");
                            matiere4.setText("syteme de gestion des emploies de temps");
                            enseignent4.setText("SGET");
                         //   Toast.makeText(getContext(),"echeqreceptioninit",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void close() {
                super.close();
            }


            @Override
            public void onException(Exception e)
            {
                //  etats =true;
                Log.i("onException", "error onException");
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "onCloseReceived ");
            }
        };

        webSocketClient.setConnectTimeout(60000);
        webSocketClient.setReadTimeout(30000);
        webSocketClient.enableAutomaticReconnection(0);
        webSocketClient.connect();

    }

    public static boolean getEtatsArticle(String dateavant,String dateactuel,String dateapret)  {

        boolean etats = false;

        SimpleDateFormat sdfr = new SimpleDateFormat("HH:mm:ss");
        Date datedujour = null;
        try {
            datedujour = sdfr.parse(dateactuel);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Date dateAvant = null;
        try {
            dateAvant = sdfr.parse(dateavant);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Date dateApres = null;
        try {
            dateApres = sdfr.parse(dateapret);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        long diff1 = datedujour.getTime();
        long diff2 = dateAvant.getTime();
        long diff3 = dateApres.getTime();
        System.out.println("datedujour  diff1 : "+diff1 );
        System.out.println("dateAvant  diff2 : "+diff2 );
        System.out.println("dateApres  diff3 : "+diff3 );
        if(diff2 < diff1 && diff1 < diff3 ) {
            // System.out.println("la date du jour es compris entre la date d'avant et la date d'apres");
            etats = true;
        } else {
            //  System.out.println("cette date n'es  pas compris entre la date d'avant et la date d'apres");
            etats = false;
        }

        return etats;

    }

    @SuppressLint("ResourceAsColor")
    public  void getcardbagrown(String message1){
        try {
            if(getEtatsArticle("07:30:30",message1,"9:30:30")){
                cardViewmatiere1.setCardBackgroundColor(R.color.violet);
                Log.i("dashboardfragment","cardViewmatiere1 violet");
            } else {
                cardViewmatiere1.setCardBackgroundColor(R.color.gris);
                Log.i("dashboardfragment","cardViewmatiere1 gris");
            }
            if(getEtatsArticle("09:30:30",message1,"11:30:30")){
                cardViewmatiere2.setCardBackgroundColor(R.color.violet);
                cardViewmatiere1.setCardBackgroundColor(R.color.violet);
                Log.i("dashboardfragment","cardViewmatiere2 violet");
            } else {
                cardViewmatiere2.setCardBackgroundColor(R.color.gris);
                Log.i("dashboardfragment","cardViewmatiere2 gris");
            }


            if(getEtatsArticle("11:30:30",message1,"12:00:30")){
                cardViewmatierepause.setCardBackgroundColor(R.color.violet);
                cardViewmatiere2.setCardBackgroundColor(R.color.violet);
                cardViewmatiere1.setCardBackgroundColor(R.color.violet);
                Log.i("dashboardfragment","cardViewmatiere2 violet");
            } else {
                cardViewmatierepause.setCardBackgroundColor(R.color.gris);
                Log.i("dashboardfragment","cardViewmatiere2 gris");
            }

            if(getEtatsArticle("12:00:30",message1,"14:00:30")){
                cardViewmatierepause.setCardBackgroundColor(R.color.violet);
                cardViewmatiere3.setCardBackgroundColor(R.color.violet);
                cardViewmatiere2.setCardBackgroundColor(R.color.violet);
                cardViewmatiere1.setCardBackgroundColor(R.color.violet);
                Log.i("dashboardfragment","cardViewmatiere3 violet");
            } else {
                cardViewmatiere3.setCardBackgroundColor(R.color.gris);
                Log.i("dashboardfragment","cardViewmatiere3 gris");
            }
            if(getEtatsArticle("14:00:30",message1,"16:00:30")){
                cardViewmatiere4.setCardBackgroundColor(R.color.violet);
                cardViewmatierepause.setCardBackgroundColor(R.color.violet);
                cardViewmatiere3.setCardBackgroundColor(R.color.violet);
                cardViewmatiere2.setCardBackgroundColor(R.color.violet);
                cardViewmatiere1.setCardBackgroundColor(R.color.violet);
                Log.i("dashboardfragment","cardViewmatiere4 violet");
            } else {
                cardViewmatiere4.setCardBackgroundColor(R.color.gris);
                Log.i("dashboardfragment","cardViewmatiere4 gris");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}