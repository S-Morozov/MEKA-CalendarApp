package com.example.mekaproj.Muistutus;

/**Here we got the methods for the Reminder,where we GET the ids and names for listview and other database saving/getting stuff.
 * "Here we got GETTERS for Muistutus*/
public class MuistutusData {

    /**Database medicine name row text*/
    private String medName;

    /**Database date row text*/
    private String startDate;

    /** Database time row text*/
    private String time;

    /** the database row ID*/
    private int id;

    /**Notification id (For alarmmanager and database) it goes into data base and is taken into alarmmanager
     *do keep track of the current alarm in progress, so it could be canceled.*/
    private int notifyid;


    /**
     * info from the database is sent into here and returned to other classes that call the methods.
     */
    public MuistutusData (int id,String medName, String startDate,String time,int notifyid) {
        this.medName = medName;
        this.startDate = startDate;
        this.time = time;
        this.id = id;
        this.notifyid = notifyid;
    }

    /**
     * Viewlist text for "Muistutus" list
     * @return text for viewlist
     */
    @Override
    public String toString() {
        return
                "Lääke: " + medName + '\n' +
                "Päivä: " + startDate + '\n' +
                "Aika: " + time + '\n' ;
    }

    /** Gets the row id from Muistutus Data, used for saving row Id into database or for getting the id.
     * @return gets the row id for/from database*/
    public int getIdM() {
        return id;
    }

    /** Gets the  medicine name from Muistutus Data, used for saving medicine name into database or for getting the medicine name.
     * @return gets the medicine name for/from database*/
    public String getMedName() {
        return medName;
    }

    /** Gets the Date from Muistutus Data, used for saving time into database,
     * used for saving Date into the database or for getting the Date from the database
     * @return gets the Date for/from database*/
    public String getStartDate() {
        return startDate;
    }

    /** Gets the Time from Muistutus Data, used for saving time into database,
     * used for saving Time into the database or for getting the Time from database.
     * @return gets the Time for/from database*/
    public String getTime() {
        return time;
    }

    /** Gets the Notification id from Muistutus Data, its used for AlarmManager,
     * used for saving Notification Id into database or getting it for AlarmManager.
     * @return gets the Notification ID for/from database*/
    public int getNotifyid() {
        return notifyid;
    }
}

