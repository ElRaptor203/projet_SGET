package com.example.sget.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sget.CircleTransform;
import com.example.sget.data.Donnee;
import com.example.sget.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/*** By El Raptor ***/

public class NotificationsFragment extends Fragment {

    private TextView nom;
    private TextView prenom;
    private TextView status;
    private TextView matricule;
    private ImageView image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        nom = (TextView) root.findViewById(R.id.tv12);
        prenom = (TextView) root.findViewById(R.id.tv22);
        matricule = (TextView) root.findViewById(R.id.tv32);
        status = (TextView) root.findViewById(R.id.tv42);
        image = (ImageView) root.findViewById(R.id.photouser);

        nom.setText(Donnee.NOM);
        prenom.setText(Donnee.PRENOM);
        status.setText(Donnee.STATUS);
        matricule.setText(Donnee.MATRICULE);

        Log.i("IMAGEPHAT_Notifications",Donnee.PATH+Donnee.IMAGE);
        Picasso.with(getContext())
                .load(Donnee.PATH+Donnee.IMAGE)
                .transform(new CircleTransform())
                .placeholder(R.drawable.ic_user1)
                .fit().centerInside()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(image);

        return root;
    }
}
