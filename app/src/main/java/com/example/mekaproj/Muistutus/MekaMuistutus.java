package com.example.mekaproj.Muistutus;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mekaproj.MekaDataBase;
import com.example.mekaproj.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**In here we Gather all the inputs from the user and send it to the database,NotificationManager,AlarmManager
 * to create data and notification pop ups for the app.*/
public class MekaMuistutus extends AppCompatActivity {

    /**This is the activitys Date button*/
    String timeTonotify;

    /**These are Date/PÄIVÄMÄÄRÄ and Time/AIKA buttons from activity*/
    Button btnSDate, btnTime;

    /**This is the activitys MedicineName input box*/
    EditText medicineNAME;

    /**GENERATES RANDOM NUMBERS FOR NOTIFICATION ID //SO THE CHANCES TO HIT THE SAME ID ARE 0.00001%*/
    Random random = new Random();

    /**Gets the int from random */
    private int id = random.nextInt();

    /**this is the user selected date/PÄIVÄ*/
    private String setStartingdate;

    /**this is the user selected Time/AIKA*/
    private String settime;

    /**this is the current calendar in the phone "place where we can get the current time and date"*/
    private Calendar calendar;

    /**this is the starting text on the DATE/PÄIVÄMÄÄRÄ button. its used for checking if the user has selected the date or not.*/
    private String originaldatetext;

    /**this is the starting text on the TIME/AIKA button. its used for checking if the user has selected the date or not.*/
    private String originaltimetext;

    /**this is the notification id that will be sent into the database for the current row/data that is being made.
     * (this is used for AlarmManager notification ids so we can delete the notification if needed)*/
    private Integer notifyid = id;



    @Override
    /**Here we create the Buttons and functions needed for the user to save the data that was written by the user. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meka_muistutus);

        /**This is the activitys Date/PÄIVÄMÄÄRÄ button*/
        btnSDate = findViewById(R.id.btn_date);

        /**This is the activitys Time/AIKA button*/
        btnTime= findViewById(R.id.btn_time);

        /**This is the activitys MedicineName/LääkeNimi input*/
        medicineNAME =  findViewById(R.id.editTextMedicine);

        /**This is the activitys Starting TEXT from DATE/PÄIVÄMÄÄRÄ button*/
        originaldatetext = btnSDate.getText().toString();

        /**This is the activitys Starting TEXT from TIME/AIKA button*/
        originaltimetext = btnTime.getText().toString();

        /**Date Button for selecting a date from the calender and setting it*/
        btnSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate();
            }
        });

        /**Time Button for selecting a Time from the Clock and setting it*/
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });

    }

    /**Method to set the Date/PÄIVÄMÄÄRÄ selected.*/
    private void setDate() {

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int data) {
                String days = Integer.toString(data);
                String months = Integer.toString(month + 1);
                String years = Integer.toString(year);

                setStartingdate = days + "-" + months + "-" + years;
                btnSDate.setText(setStartingdate);
            }
        },year, month, date);

        datePickerDialog.show();
    }

    /**Method to set the Time/AIKA selected.*/
    private void setTime(){

        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        boolean is24HoursView=true;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {

            @Override
            /**Here we get the hour and minutes.
             * @param hour This is the Hour of selected time
             * @param min This is the Minutes of selected time*/
            public void onTimeSet(TimePicker view, int hour, int min) {

                timeTonotify = hour + ":" + min;
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY,hour);
                calendar1.set(Calendar.MINUTE,min);
                updatetimeTEXT(calendar1);
                btnTime.setText(settime);
            }

        }, hour, min, is24HoursView);

        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();

    }

    /**Method to format time for AlarmManager and to set the new text for Time/AIKA button*/
    public void updatetimeTEXT(Calendar calendar1){
        String timetext;
        timetext = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar1.getTime());
        settime = timetext;
    }


    /**send's data to the Calendar_memory_List activity to create viewlist and database into SQLite*/
    public void btn_addToCalendar(View v) {

        String medname = medicineNAME.getText().toString().trim();         //access the data form the input field
        String date = btnSDate.getText().toString().trim();          //access the date from the choose date button
        String time = btnTime.getText().toString();   //access time from the choose time button

        ///Adding Muistutus Data (Notification Data and Database data)
        MuistutusData muistutusData;

        try{
            //Here we make the new database row with given rowid,date,time,name,notificationid (row id is -1 because it will go from 1 - how many u need)
            muistutusData = new MuistutusData(-1,medname,date,time,notifyid);
            Toast.makeText(MekaMuistutus.this,"Lisätty",Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            // If making the database fails we send the user that it failed and create and ERROR database that can be deleted by the user.
            Toast.makeText(MekaMuistutus.this,"Muistutuksen tekemine epäonnistui",Toast.LENGTH_SHORT).show();
            muistutusData = new MuistutusData(0,"ERROR","ERROR","ERROR",0);

        }

        // Here we check if all input fields are filled out,if not we tell the user "Valitse päivä ja aika" or "Kirjoita lääkkeen nimi"
        if (medname.isEmpty()) {

            // if medicine name field is empty we tell the user to fill it.
            Toast.makeText(getApplicationContext(), "Kirjoita lääkeen nimi.", Toast.LENGTH_SHORT).show();   //shows the toast if input Lääkeen nimi is empty

        } else {

            if (time.equals(originaltimetext) || date.equals(originaldatetext)) {         //shows toast if date and time are not selected
                Toast.makeText(getApplicationContext(), "Valitse päivä ja aika.", Toast.LENGTH_SHORT).show();

            } else {

                //Here we set the alarm for AlarmManager to create notification + we create the data base with given inputs.
                setAlarm(medicineNAME.getText().toString(),setStartingdate,settime);
                MekaDataBase mekaDataBase = new MekaDataBase(MekaMuistutus.this);
                mekaDataBase.addOneMUIS(muistutusData);
                Intent intent = new Intent(MekaMuistutus.this, Calendar_memory_list.class);
                startActivity(intent);

                //we finish the activity so it cant be returned to with the return button.
                finish();

            }
        }


    }

    /**Here we set the alarm for AlarmManager that will notify us on the right time.
     * @param date Given Date from the input.
     * @param text Given Medicine Name from the input.
     * @param time Given Time from the input. */
    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);     //assigining alarm manager object to set alarm

        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);

        //sending data to alarm class to create channel and notification
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        intent.putExtra("id",notifyid);

        //Sending infromation trough pending intent into AlertReveiver.java.
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notifyid, intent, PendingIntent.FLAG_ONE_SHOT);

        //Formatting the Date for AlarmManager
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");

        try {

            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Muistutus Lisätty", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            //We print an error if Try fails.
            e.printStackTrace();

        }


    }
    }


