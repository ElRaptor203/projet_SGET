package com.example.sget.data;

import android.os.Build;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class DataModel1 {


    String semaine;
    String matiere;
    String notif;
    String codeMatiere;
    String dureeCour;
    String enseignent;
    String statuts;
    String messageDeleguer;
    WebView webView;

    public void getWebView1(TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(notif, Html.FROM_HTML_MODE_LEGACY));
        } else
            textView.setText(Html.fromHtml(notif));    }

    public DataModel1(String notif) {
        this.notif = notif;
    }

    public String getNotif() {

        return notif;
    }

    public DataModel1(String semaine, String matiere, String codeMatiere, String dureeCour, String enseignent, String statuts, String messageDeleguer) {
        this.semaine = semaine;
        this.matiere = matiere;
        this.codeMatiere = codeMatiere;
        this.dureeCour = dureeCour;
        this.enseignent = enseignent;
        this.statuts = statuts;
        this.messageDeleguer = messageDeleguer;
    }


    public String getSemaine() {
        return semaine;
    }

    public String getMatiere() {
        return matiere;
    }

    public String getCodeMatiere() {
        return codeMatiere;
    }

    public String getDureeCour() {
        return dureeCour;
    }

    public String getEnseignent() {
        return enseignent;
    }

    public String getStatuts() {
        return statuts;
    }

    public String getMessageDeleguer() {
        return messageDeleguer;
    }
}
