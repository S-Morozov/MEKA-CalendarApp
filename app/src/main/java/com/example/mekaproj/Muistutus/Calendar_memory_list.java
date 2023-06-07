package com.example.mekaproj.Muistutus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mekaproj.MekaDataBase;
import com.example.mekaproj.*;

import java.util.List;


/**In here we create the Listview for the activity that gets the information from the DataBase*/
public class Calendar_memory_list extends AppCompatActivity {

    //All we need for listview creation and data fetching.
    /**used for creating the list with this listview*/
    private ListView lv_Muistutusdata;
    /**used for stacking up list objects*/
    ArrayAdapter MuistiArrayAdapter;

    @Override
    /**This creates new list for the activity view*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_memory_list);

        //This will show saved rows into the viewlist from database.
        lv_Muistutusdata = findViewById(R.id.list_Memo_Calendar);
        MekaDataBase mekaDataBase = new MekaDataBase(Calendar_memory_list.this);
        List<MuistutusData> everything = mekaDataBase.getMuitsAll();  // This will get all data from Muistilista Table.

        // Viewlist creation
        MuistiArrayAdapter = new ArrayAdapter<>(Calendar_memory_list.this, android.R.layout.simple_list_item_1, everything);
        lv_Muistutusdata.setAdapter(MuistiArrayAdapter);

        // This will send the user into the CalendarActivity_view activity, to read the data he made and to remove it.
        lv_Muistutusdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            //Sends the information to CalendarActivity_view,so it can write the data into that activity.
            /**Sends information to CalendarActivity,so the info can be viewed by the user.
             * @param position this finds the position of the selected row in the database.*/
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                Intent intent = new Intent(Calendar_memory_list.this, CalendarActivity_View.class);
                intent.putExtra("POSITIONM",String.valueOf(position));
                startActivity(intent);

                //Finishes the activity so the return button will send the user back to Mainactivity
                finish();
            }
        });
    }
}