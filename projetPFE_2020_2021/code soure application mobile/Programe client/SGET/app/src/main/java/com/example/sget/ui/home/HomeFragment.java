package com.example.sget.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sget.data.Donnee;
import com.example.sget.MainActivity;
import com.example.sget.R;
import com.example.sget.adapter.Adapter1;
import com.example.sget.data.DataModel1;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import tech.gusavila92.websocketclient.WebSocketClient;

import static com.example.sget.ui.dashboard.DashboardFragment.etatssemaine;


/*** By El Raptor ***/

public class HomeFragment extends Fragment {

    private WebSocketClient webSocketClient;
    private static RecyclerView recyclerView;
    private static Adapter1 adapter1;
    private static TextView notification;
    private RecyclerView.LayoutManager layoutManager;
    public static ArrayList<DataModel1> data1 = new ArrayList<DataModel1>() ;
    private static boolean etats = true,listinit = true;
    private static String context = "";
    private static boolean removeNotif = false;
    NotificationManager notificationManager;
    int bundleNotificationId = 100;
    int singleNotificationId = 100;
    NotificationCompat.Builder summaryNotificationBuilder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        if (removeNotif){
            data1.clear();
            removeNotif = false;
        }
        if (Donnee.etatsnotif){
            Donnee.etatsnotif = false;
            removeNotif = true;
        }

        createWebSocketClient();
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view2);
        notification = (TextView) root.findViewById(R.id.simpleTextView);
        recyclerView.setHasFixedSize(true);
         context = "l'emploie de temps de la semaine du "+Donnee.LIBELLER+" es maintenant  disponible";
//        String context1 = "l'emploie de temp de la semaine du 02 janvier 2021 au 09 janvier 2021 es maintenant  disponible";
//        String context2 = "l'emploie de temp de la semaine du 16 fevrier 2021 au 23 fevrier 2021 es maintenant  disponible";
//        String context3 = "l'emploie de temp de la semaine du 02 juielle 2021 au 09 juielle 2021 es maintenant  disponible";
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        if (listinit){
//            data1.add(new DataModel1(context1));
//            data1.add(new DataModel1(context2));
//            data1.add(new DataModel1(context3));
//            listinit = false;
//        }

        adapter1 = new Adapter1(getActivity(),data1);
        recyclerView.setAdapter(adapter1);

        notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);


        return root;
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
                    Log.i("DashboardFragment","matricule : "+Donnee.MATRICULE);
                    webSocketClient.send("DashboardFragment5"+"@"+Donnee.FILIERE+"&"+Donnee.DATA+"#");
                }else {
                    Toast.makeText(getContext(),"svp veillez- vous connecter",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTextReceived(String s) {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = s;

                        String message0 = message.substring(0,message.indexOf("@"));
                        if (message0.equals("notification")){
                            String message1 = message.substring(message0.length()+1,message.indexOf("&"));
                            String message2 = message.substring(message1.length()+message0.length()+2,message.indexOf("#"));
                            String message3 = message.substring(message2.length()+message1.length()+message0.length()+3,message.indexOf("$"));
                            Donnee.DATA = message2;
                            Donnee.LIBELLER = message1;
                            Log.i("HomeFragment","libeller "+message1);
                            Log.i("HomeFragment","data "+message2);
                            Log.i("HomeFragment","npmbre de semaine "+message3);
                            data1.add(new DataModel1(context));
                            Donnee.NBRSEMAINE = message3;
                            Donnee.etatsnotif = true;
                            etatssemaine = true;

                            Toast.makeText(getContext(),"un nouvelle emploie de temps a été publié",Toast.LENGTH_LONG).show();
                           notification();
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

    public void notification(){


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel groupChannel = new NotificationChannel("bundle_channel_id", "bundle_channel_name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(groupChannel);
        }
        bundleNotificationId += 100;
        singleNotificationId = bundleNotificationId;
        String bundle_notification_id = "bundle_notification_" + bundleNotificationId;
        Intent resultIntent = new Intent(getContext(), MainActivity.class);
        resultIntent.putExtra("notification", "Summary Notification Clicked");
        resultIntent.putExtra("notification_id", bundleNotificationId);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), bundleNotificationId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        summaryNotificationBuilder = new NotificationCompat.Builder(getContext(), "bundle_channel_id")
                .setGroup(bundle_notification_id)
                .setGroupSummary(true)
                .setContentTitle("Nouveau emploie de temps")
                .setContentText(context)
                .setSmallIcon(R.drawable.ic_user1)
                .setContentIntent(resultPendingIntent);

        notificationManager.notify(bundleNotificationId, summaryNotificationBuilder.build());

    }

    public static List<String> getNbreSemaine(String Semaine, List<String> listS){
        int NbreSemaine = 0 ;
        NbreSemaine = Integer.parseInt(Semaine);
        for (int i = 0; i < NbreSemaine; i++){
            Log.i("HomeFragment","NBRSEMAINE : "+(i+1));
            listS.add("1");
        }
        return listS;
    }

}
