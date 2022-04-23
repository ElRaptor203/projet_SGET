package com.example.sget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.sget.R;
import com.example.sget.data.DataModel1;

import java.util.ArrayList;


public class Adapter1 extends RecyclerView.Adapter<Adapter1.MyViewHolder> {

    public Context mcontext;
    private ArrayList<DataModel1> dataSet;
    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adapter1(Context context, ArrayList<DataModel1> data) {
        this.mcontext = context;
        this.dataSet = data;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notif;
        TextView matiere;
        TextView codeMatiere;
        TextView dureeCour;
        TextView enseignent;
        TextView statuts;
        TextView messageDeleguer;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.notif = (TextView) itemView.findViewById(R.id.simpleTextView);
//            this.matiere = (TextView) itemView.findViewById(R.id.libelleMatiere);
//            this.codeMatiere = (TextView) itemView.findViewById(R.id.libelleCodeMatiere);
//            this.dureeCour = (TextView) itemView.findViewById(R.id.libelleDureCour);
//            this.enseignent = (TextView) itemView.findViewById(R.id.libelleEnsiegnent);
//            this.statuts = (TextView) itemView.findViewById(R.id.libelleStatus);
//            this.messageDeleguer = (TextView) itemView.findViewById(R.id.libelleMessageDeleguer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(mcontext)
                .inflate(R.layout.card_layout1, parent, false);

        // view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        DataModel1 dataModel = dataSet.get(listPosition);
        TextView notif = holder.notif;
        TextView matiere = holder.matiere;
        TextView codeMatiere = holder.codeMatiere;
        TextView dureeCour = holder.dureeCour;
        TextView enseignent = holder.enseignent;
        TextView statuts = holder.statuts;
        TextView messageDeleguer = holder.messageDeleguer;


        dataModel.getWebView1(notif);
//        matiere.setText(dataModel.getMatiere());
//        codeMatiere.setText(dataModel.getCodeMatiere());
//        dureeCour.setText(dataModel.getDureeCour());
//        enseignent.setText(dataModel.getEnseignent());
//        statuts.setText(dataModel.getStatuts());
//        messageDeleguer.setText(dataModel.getMessageDeleguer());

        //        Log.i("IMAGEPHAT",dataModel.getImageArticle());
//        Picasso.with(mcontext).load(dataModel.getImageArticle()).placeholder(R.drawable.ic_launcher_background).fit().centerInside().into(imageView);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}