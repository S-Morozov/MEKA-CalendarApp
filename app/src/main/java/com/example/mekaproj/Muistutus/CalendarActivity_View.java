package com.example.mekaproj.Muistutus;

import android.annotation.SuppressLint;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mekaproj.MekaDataBase;
import com.example.mekaproj.*;

import java.util.List;



/**Here we check out and delete, the data we clicked in memory_list(ListView)*/
public class CalendarActivity_View extends AppCompatActivity  {

    // Setting the properties needed to name the textviews on the activity.
    /**Textview for Date*/
    private TextView tv_MuistutusSPAIVA;

    /**Textview for Medicine Name*/
    private TextView tv_MEDnimi;

    /**Textview for Time*/
    private TextView tv_MTIME;

    /**For getting inputs to fetch from database*/
    private MuistutusData Muget;

    /**For getting Information from DataBase, with the help of "MuistutusData Muget"*/
    private MekaDataBase mekget;

    /**Time taken from MuistutusData*/
    private String timem;

    /**Date taken from MuistutusData*/
    private String startdate;

    /**Medicine name taken from MuistutusData*/
    private String medname;

    /**Notificantion ID integer from MuistutusData,used for AlarmManager Data*/
    private Integer notifyid;

    /**Gets Medicine name text and Date/Time text into activity so the user can read the Notification(Muistutus) He/she/unknown created.*/
     @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

            //Id kentät
            tv_MuistutusSPAIVA = findViewById(R.id.startDateTXT);
            tv_MEDnimi = findViewById(R.id.editTextNAMEMED);
            tv_MTIME = findViewById(R.id.timeTxt);

            //Getting the database
        MekaDataBase muistdata = new MekaDataBase(CalendarActivity_View.this);
        List<MuistutusData> arrayList = muistdata.getMuitsAll(); // Getting the whole Muistutus table from database.

        //Getting the intent that was sent to us from "Calendar_memory_list" class. (Fetching the info from intent)
        Intent intent = getIntent();
        String pos = intent.getStringExtra("POSITIONM");

        // getting the position on the clicked item,wich can be used inside data base to indentify the row id. wich the clicked data is stored.
        int position = Integer.parseInt(pos);
        MuistutusData muistutus = arrayList.get(position);

        //Getting the database info with the methods from "Muistutus Data" class
        medname = muistutus.getMedName();
        startdate = muistutus.getStartDate();
        timem = muistutus.getTime();
        notifyid = muistutus.getNotifyid();

        //Setting the text into the activity with fetched info from database.
            tv_MEDnimi.setText(medname);
            tv_MuistutusSPAIVA.setText(startdate);
            tv_MTIME.setText(timem);
            Muget = muistutus;
            mekget = muistdata;

    }

    /**Removes the "Muistutus"(Notification) also removes it from the AlarmManage,wich means the notification wont pop up and the notification channel will be deleted from the memory.*/
     public void btn_calendar_Delete (View view){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Here we get the AlertReceiver class and tell it to cancel the Notification in AlarmManager.
        Intent intent2 = new Intent(getApplicationContext(), AlertReceiver.class);
        intent2.putExtra("id", notifyid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notifyid, intent2, PendingIntent.FLAG_ONE_SHOT);

        // Canceling the notification.
        am.cancel(pendingIntent);

        // Deleting the data from the database.
        mekget.deleteOneM(Muget);
        Intent intent = new Intent(CalendarActivity_View.this, Calendar_memory_list.class);
        startActivity(intent);

        //Finishing the activity so u cant return to the old saved item you selected,because u just deleted it.
        finish();
    }

}

