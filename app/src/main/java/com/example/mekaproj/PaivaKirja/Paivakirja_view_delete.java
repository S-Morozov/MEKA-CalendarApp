package com.example.mekaproj.PaivaKirja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mekaproj.MekaDataBase;
import com.example.mekaproj.*;

import java.util.List;

/**here we create the view for clicked object of listview*/
public class Paivakirja_view_delete extends AppCompatActivity {
    /**This is the Textview for Story/Kirje/Text of the diary/Päiväkirja from database*/
    private TextView tv_Paivakirjadatakirje;  //Kirje Textview osio mihin on kirjoitettu tarina
    /**The Header from database*/
    private TextView tv_Paivakirjadataotsikko; //Tarinan Otsikko
    /**The Päiväkirja Data info for getting the Database*/
    private PaivaKirjaData getpk;
    /**The Main DataBase where we can delete the data*/
    private MekaDataBase getdb;
    @Override

    /**Here we create infromation on screen for the clicked object,that was clicked in the list*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paivakirja_view_delete);

        //Id kentät
        tv_Paivakirjadatakirje = findViewById(R.id.viewKirje);
        tv_Paivakirjadataotsikko = findViewById(R.id.viewOtsikko);

        MekaDataBase pkdata = new MekaDataBase(Paivakirja_view_delete.this);
        List<PaivaKirjaData> arrayList = pkdata.getEverything();
        Intent intent = getIntent();
        String pos = intent.getStringExtra("POSITION");
        int position = Integer.parseInt(pos);
        PaivaKirjaData paivakirja = arrayList.get(position);
        String otsikko = paivakirja.getOtsikko();
        String kirje = paivakirja.getKirje();
        tv_Paivakirjadataotsikko.setText(otsikko);
        tv_Paivakirjadatakirje.setText(kirje);
        getpk = paivakirja;
        getdb = pkdata;
    }
    /**When this is pressed the data is deleted from the database*/
    public void btn_Delete_View (View view){
        getdb.deleteOne(getpk);
        Intent intent = new Intent(Paivakirja_view_delete.this, PaivaKirjaData_Displayer.class);
        startActivity(intent);
        finish();

    }

}