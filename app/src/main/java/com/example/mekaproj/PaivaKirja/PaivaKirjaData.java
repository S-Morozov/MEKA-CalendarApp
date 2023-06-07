package com.example.mekaproj.PaivaKirja;

/**Here we send the information to the database that the user has inputed.*/
public class PaivaKirjaData {
    /**The row id of the database(selected data)*/
    private int id; // Database row id
    /**Päiväkirja/Diary Header text*/
    private String otsikko; // Database Header text
    /**The diary Story/Kirje for Päiväkirja*/
    private String kirje; // Database "kirje" text
    /**The DATE when the diary was created*/
    private String paiva; // Database date text

    // Info from the database is sent into here and returned to other classes that call the methods.
    /**Here we fetch the data that is returned to us from other classes,
     * @param otsikko the header for the diary/päiväkirja
     * @param kirje the text/story for the diary/päiväkirja
     * @param Date the Date for the diary/päiväkirja, this is the date the diary was creeated.*/
    public PaivaKirjaData (int id,String otsikko, String kirje,String Date) {
        this.otsikko = otsikko;
        this.kirje = kirje;
        this.id = id;
        this.paiva = Date;
    }

    @Override

    //Saved viewlist rows text.
    /**We use this to write the right data for the Viewlist*/
    public String toString() {
        return "     " +
                otsikko + '\n' +
                paiva + '\n'
                ;
    }

    // Getters
    /**Getting and returning the row id*/
    public Integer getID(){

        return id;
    }
    /**Gets and returns the Header/otsikko for the diary/päiväkirja*/
    public String getOtsikko() {

        return otsikko;
    }
    /**Getting the Text/story of the diary/päiväkirja*/
    public String getKirje() {

        return kirje;
    }
    /**Gets the Date/päivä when the diary was created.*/
    public String getPaiva() {
        return paiva;
    }


    ////////
}
