package com.example.mekaproj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mekaproj.Muistutus.MuistutusData;
import com.example.mekaproj.PaivaKirja.PaivaKirjaData;

import java.util.ArrayList;
import java.util.List;

/**This is Where we store/Delete/Send/Get all of our data*/
public class MekaDataBase extends SQLiteOpenHelper{

    // Paivakirja database finals
    /**The name of the SQLite database*/
    static final String DB_NAME = "DATABASE.db"; // NAME OF DataBase FILE
    /**The name of the SQLite Data table of Päiväkirja*/
    public static final String PAIVAKIRJA_TABLE = "PAIVAKIRJA_TABLE"; // DATABASE TABLE (WHERE COLUMNS GO INTO)
    /**This is the Header text in the SQLite Database Päiväkirja*/
    public static final String COLUMN_PAIVAKIRJA_OTSIKKO = "PAIVAKIRJA_OTSIKKO"; // TITLE FOR PAIVAKIRJA
    /**The Story/text of Päiväkirja in SQLite Database*/
    public static final String COLUMN_PAIVAKIRJA_KIRJE = "PAIVAKIRJA_KIRJE"; // TEXT/STORY FOR PAIVAKIRJA
   /**The Date/Päivä in SQLite Päiväkirja Database (Creation date for the diary)*/
    public static final String COLUMN_PAIVAKIRJA_PAIVA = "PAIVAKIRJA_PAIVA"; // DATE FOR PAIVAKIRJA
   /**The Row ID in SQLite database*/
    public static final String COLUMN_ID = "ID"; //COLUMN ID

    // Muistutus database finals
    /**The Reminder/Muistutus Table in SQLite Database*/
    public static final String MUISTUTUS_TABLE = "MUISTUTUS_TABLE"; // DATABASE TABLE (WHERE COLUMNS GO INTO)
    /**The Reminder Date In SQLite Database Muistutus*/
    public static final String COLUMN_MUISTUTUS_SPAIVA = "MUISTUTUS_SPAIVA"; // START DAY
    /**The Reminder Time/Aika (Clocktime) in SQLite Database*/
    public static final String COLUMN_MUISTUTUS_AIKA = "MUISTUTUS_AIKA"; // TIME
    /**The Name of the reminder in SQLite database*/
    public static final String COLUMN_MUISTUTUS_NIMI = "MUISTUTUS_NIMI"; /* NAME */
    /**The ROW Id of Muistutus SQLite Database*/
    public static final String COLUMN_ID_MUISTUTUS = "ID"; //COLUMN ID
    /**The Notification ID of Muistutus for AlarmManager (this id is generated randomly by the program to use for AlarmManager notification ids.*/
    public static final String COLUMN_NOTIFYID = "NOTIFYID"; // RANDOM GENERATED ID FOR ALARM MANAGER.

/////////////////////////////////////////////////////////////////////////////

        ///MAINDATABASE SQLite////
    public MekaDataBase(@Nullable Context context) {

        super(context, DB_NAME, null, 1);
    }

    //First time Muistutus database is created.
    //First time paivakirja database is created.
    @Override
    /**Here we create the Databases needed to save user input Data and Dates + Time*/
    public void onCreate(SQLiteDatabase db) {

        // Creates values and database with sql code. for MUISTUTUS
        String createTableStatementMuistutus = "CREATE TABLE " + MUISTUTUS_TABLE + " (" + COLUMN_ID_MUISTUTUS + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MUISTUTUS_NIMI + " TEXT," + COLUMN_MUISTUTUS_SPAIVA + " DATE," + COLUMN_MUISTUTUS_AIKA + " TIME,"+ COLUMN_NOTIFYID + " INTEGER)";
        db.execSQL(createTableStatementMuistutus);

        // Creates values and database with sql code. for PAIVAKIRJA
        String createTableStatementPaivakirja = "CREATE TABLE " + PAIVAKIRJA_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PAIVAKIRJA_OTSIKKO + " TEXT," + COLUMN_PAIVAKIRJA_KIRJE + " TEXT," + COLUMN_PAIVAKIRJA_PAIVA + " DATE)";
        db.execSQL(createTableStatementPaivakirja);
    }

    //this is called if the database version is updated so old users dont lose data.
    @Override
    /**If the Database is updated in the program this will drop the existing tables and create new ones*/
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + MUISTUTUS_TABLE;  // MUISTUTUS sql query to check table with the same name or not
        String query2 = "DROP TABLE IF EXISTS " + PAIVAKIRJA_TABLE;  //PAIVAKIRJA sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2); //executes the sql command
        onCreate(sqLiteDatabase);
    }

    /**Here we add new data into Muistutus/Reminder the database with given info from MuistutusData.java*/
    public boolean addOneMUIS (MuistutusData muistutusData){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MUISTUTUS_SPAIVA,muistutusData.getStartDate());
        cv.put(COLUMN_MUISTUTUS_AIKA,muistutusData.getTime());
        cv.put(COLUMN_MUISTUTUS_NIMI,muistutusData.getMedName());
        cv.put(COLUMN_NOTIFYID,muistutusData.getNotifyid());

        long insert = db.insert(MUISTUTUS_TABLE, null, cv);

        // if insert is -1 it returns a false if its something else it returns true.

        if (insert == -1)
        {return false;}

        else

        { return true; }

    }



    // Adds written data into the database
    /**Here we add new data into Päiväkirja Database with given infromation from PäiväkirjaData*/
    public boolean addOnePK(PaivaKirjaData paivaKirjaData){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PAIVAKIRJA_OTSIKKO,paivaKirjaData.getOtsikko()); // Here were writing column_paivakirjaOtsikko that we will get with getOtsikko from paivakirjadata
        cv.put(COLUMN_PAIVAKIRJA_KIRJE,paivaKirjaData.getKirje()); // Here were writing column_paivakirjaKirje that we will get with getKirje from paivakirjadata
        cv.put(COLUMN_PAIVAKIRJA_PAIVA,paivaKirjaData.getPaiva());
        long insert = db.insert(PAIVAKIRJA_TABLE, null, cv);

        // if insert is -1 it returns a false if its something else it returns true.

        if (insert == -1)
        {return false;}

        else

        { return true; }

    }

    ///REMOVES PAIVAKIRJA IF DELETE PRESSED
    /**Removes the given row from Päiväkirja Database*/
    public boolean deleteOne (PaivaKirjaData paivaKirjaData){
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "DELETE FROM " + PAIVAKIRJA_TABLE + " WHERE " + COLUMN_ID + " = " + paivaKirjaData.getID();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
    ///REMOVES MUISTUTUS IF DELETE PRESSED
    /**Removes the given row from Muistutus Database*/
    public boolean deleteOneM (MuistutusData muistutusData){
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "DELETE FROM " + MUISTUTUS_TABLE + " WHERE " + COLUMN_ID_MUISTUTUS + " = " + muistutusData.getIdM();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }


    //Geteverything returns everything from the database.
    /**With this we get Everything from the Päiväkirja Database(Table)*/
    public List<PaivaKirjaData> getEverything() {

        List<PaivaKirjaData> returnList = new ArrayList<>();

        // Get data from database
        String queryString = "SELECT * FROM " + PAIVAKIRJA_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {

                Integer paivakirjaid = cursor.getInt(0);
                String paivakirjaOtsikko = cursor.getString(1);

                String paivakirjaKirje = cursor.getString(2);

                String paivakirjaPaiva = cursor.getString(3);

                PaivaKirjaData paivaKirjaData = new PaivaKirjaData(paivakirjaid,paivakirjaOtsikko,paivakirjaKirje,paivakirjaPaiva);

                returnList.add(paivaKirjaData);

            }while (cursor.moveToNext());


        }else {
            //fail dont add anything
        }

        // close the database and cursor. then return everything from the database.
        cursor.close();

        db.close();

        return returnList;
    }
    /**Gets All the data from Muistutus Database (Table)*/
    public List<MuistutusData> getMuitsAll() {

        List<MuistutusData> returnListm = new ArrayList<>();

        // Get data from database
        String queryString = "SELECT * FROM " + MUISTUTUS_TABLE + " ORDER BY " + COLUMN_ID_MUISTUTUS + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {

                Integer muistutusId = cursor.getInt(0);
                String medicineName = cursor.getString(1);
                String sDate = cursor.getString(2);
                String mtime = cursor.getString(3);
                Integer notifyid = cursor.getInt(4);

                MuistutusData muistutusData = new MuistutusData(muistutusId,medicineName,sDate,mtime,notifyid);

                returnListm.add(muistutusData);

            }while (cursor.moveToNext());


        }else {
            //fail dont add anything
        }

        // close the database and cursor. then return everything from the database.
        cursor.close();

        db.close();

        return returnListm;
    }



}
