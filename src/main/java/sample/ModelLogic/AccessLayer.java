package sample.ModelLogic;

import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

public class AccessLayer {

    public RESULT AddEntry(String[] data, Tables table){return  null;}
    public String[][] ReadEntry(String[] Key,Fields[]fields, Tables table){return null;}
    public RESULT UpdateEntry(Tables table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){return null;}
    public RESULT DeleteEntry (String[] key,Fields[]fields,Tables table){return null;}

}
