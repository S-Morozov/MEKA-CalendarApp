package com.example.mekaproj.PaivaKirja;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mekaproj.MekaDataBase;
import com.example.mekaproj.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**Here we create fucnctions that we use to write the diary/Päiväkirja*/
public class paivaKirja_kirjaaminen extends AppCompatActivity {

    /**The save button for creating diary/päiväkirja*/
    Button btnTallennaPK;

    /**we use these to save the input into database*/
    EditText editTextKirje,editTextOtsikko;
    /**The Date input,we use this to save the inputted date*/
    private String paivakirjaPaiva;
    @Override
    /**Here we create the database with the inputted data given by the user*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiva_kirja_kirjaaminen);

        // Getting the current date for päiväkirja.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        paivakirjaPaiva = sdf.format(new Date());

        //Napit + Tekstikentät.
        btnTallennaPK = findViewById(R.id.btnTallennaPK);
        editTextKirje = findViewById(R.id.editTextKirje);
        editTextOtsikko = findViewById(R.id.editTextOtsikko);

        ///Button Listeners
        btnTallennaPK.setOnClickListener(new View.OnClickListener() {

            @Override
            /**here we save the inputs into database*/
            public void onClick(View view) {


                PaivaKirjaData paivaKirjaData; ///Päivä kirjan datan kirjaaminen

                // Tässä testataan ensiksi että menikö arvot päiväkirjadataan tai ei, jos ei mennyt se palautta Catchin eli errorin.
                try{
                    paivaKirjaData = new PaivaKirjaData(-1,editTextOtsikko.getText().toString(),editTextKirje.getText().toString(),paivakirjaPaiva);

                    Toast.makeText(paivaKirja_kirjaaminen.this,"Tallennettu",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(paivaKirja_kirjaaminen.this,"Paivakirjan tekemine epäonnistui",Toast.LENGTH_SHORT).show();

                    paivaKirjaData = new PaivaKirjaData(0,"ERROR","ERROR","ERROR");

                }

                MekaDataBase mekaDataBase = new MekaDataBase(paivaKirja_kirjaaminen.this);

                //Datan lähettäminen
                mekaDataBase.addOnePK(paivaKirjaData);    //Datan lähettäminen

                //Tulostaa toastin joka kertoo että arvot tallennettu databaseen.
                Toast.makeText(paivaKirja_kirjaaminen.this,"Tallenettu",Toast.LENGTH_SHORT).show();

                //Vie suoraan PaivaKirjaData_Displayer activitiin.
                Intent intent = new Intent(paivaKirja_kirjaaminen.this, PaivaKirjaData_Displayer.class);
                startActivity(intent);
                finish();

            }


        });

    }
}