package com.example.sget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sget.data.Donnee;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

import static com.example.sget.ui.dashboard.DashboardFragment.etatssemaine;
import static com.example.sget.ui.dashboard.DashboardFragment.semaine;

/*** By El Raptor ***/

public class MainActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;


    ViewGroup viewGroup;
    String pseudouser = "";
    String passuser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        createWebSocketClient();

        if (!Donnee.isconnected){
            connection();
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.seconnecter:
                Toast.makeText(this,"se connecter",Toast.LENGTH_SHORT).show();
                connection();
                return true;

            case R.id.sedeconnecter:
                Toast.makeText(this,"se deconnecter",Toast.LENGTH_SHORT).show();
                Donnee.isconnected = false;
                Donnee.MATRICULE = "";
                Donnee.IMAGE = "";
                Donnee.NOM = "";
                Donnee.PRENOM = "";
                Donnee.STATUS = "";
                semaine.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void connection(){
        Toast.makeText(this,"se connecter",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection");
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.activity_custom_dialog, viewGroup, false);
        // Set up the input
        EditText pseudoUser = (EditText) viewInflated.findViewById(R.id.pseudoUser);
        EditText passUser = (EditText) viewInflated.findViewById(R.id.passwordUser);
        TextInputLayout pseudoInput = (TextInputLayout) viewInflated.findViewById(R.id.pseudoInput);
        TextInputLayout passInput = (TextInputLayout) viewInflated.findViewById(R.id.passInput);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);
        pseudoInput.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                pseudoInput.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passInput.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                passInput.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 pseudouser = pseudoUser.getText().toString().trim();
                 passuser = passUser.getText().toString().trim();

             //   Toast.makeText(getApplicationContext(),pseudouser+"|"+passuser,Toast.LENGTH_SHORT).show();
                webSocketClient.send("connection"+"@"+pseudouser+"&"+passuser+"#");
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
                webSocketClient.send("AccueilFragment"+"@");

            }

            @Override
            public void onTextReceived(String s) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = s;

                        String message0 = message.substring(0,message.indexOf("&"));

                        if (message0.equals("reussiteconnection")){
                            String message1 = message.substring(message0.length()+1,message.indexOf("#"));
                            String message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("%"));
                            String message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
                            String message4 = message.substring(message3.length()+message1.length()+message2.length()+message0.length()+4,message.indexOf("^"));
                            String message5 = message.substring(message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+5,message.indexOf("¼"));
                            String message6 = message.substring(message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+6,message.indexOf("½"));
                            String message7 = message.substring(message6.length()+message5.length()+message4.length()+message3.length()+message1.length()+message2.length()+message0.length()+7,message.indexOf("~"));

                            Donnee.MATRICULE = message5;
                            Donnee.IMAGE = message4;
                            Donnee.NOM = message1;
                            Donnee.PRENOM = message2;
                            Donnee.STATUS = message3;
                            Donnee.FILIERE = message6;
                            Donnee.NBRSEMAINE = message7;
                            Log.i("mainactivity","MATRICULE : "+Donnee.MATRICULE);
                            Log.i("mainactivity","IMAGE : "+Donnee.IMAGE);
                            Log.i("mainactivity","NOM : "+Donnee.NOM);
                            Log.i("mainactivity","PRENOM : "+Donnee.PRENOM);
                            Log.i("mainactivity","STATUS : "+Donnee.STATUS);
                            Log.i("mainactivity","FILIERE : "+Donnee.FILIERE);
                            Log.i("mainactivity","NBRSEMAINE : "+Donnee.NBRSEMAINE);
                           // DashboardFragment dashboardFragment;

                            etatssemaine = true;
                         //   HomeFragment.getNbreSemaine(message7, DashboardFragment.semaine);


                            Toast.makeText(getApplicationContext(),"bienvenue "+Donnee.NOM+"  "+Donnee.PRENOM,Toast.LENGTH_LONG).show();
                            Donnee.isconnected = true;
                            startActivity(new Intent(MainActivity.this,MainActivity.class));
                        }

                        if (message0.equals("echecconnection")){
                            Donnee.isconnected = false;
                            Donnee.MATRICULE = "";
                            Donnee.IMAGE = "";
                            Donnee.NOM = "";
                            Donnee.PRENOM = "";
                            Donnee.STATUS = "";
                            Toast.makeText(getApplicationContext(),"Le nom d'utilisateur ou le mots de passe es incorecte",Toast.LENGTH_SHORT).show();
                        }

                        if (message0.equals("usernoexit")){
                            Donnee.isconnected = false;
                            Donnee.MATRICULE = "";
                            Donnee.IMAGE = "";
                            Donnee.NOM = "";
                            Donnee.PRENOM = "";
                            Donnee.STATUS = "";
                            Toast.makeText(getApplicationContext(),"L'utilisateur "+pseudouser+" n'existe pas dans la base de donnee",Toast.LENGTH_SHORT).show();
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


}
