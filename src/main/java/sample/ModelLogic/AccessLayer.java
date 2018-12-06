package sample.ModelLogic;

import com.sun.xml.internal.bind.v2.TODO;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.sql.Connection;
import java.util.List;

public class AccessLayer {

    private Connection connection;

    public void connectDB(){}

    //TODO:Twito
    public RESULT AddEntry(List<String> data, Tables table){return  null;}
    //TODO:Netanel
    public String[][] ReadEntry(List<String> value, List<Fields> fields, Tables table){return null;}
    //TODO:Netanel
    public RESULT UpdateEntry(Tables table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){return null;}
    //TODO:Twito
    public RESULT DeleteEntry (List<String> value,List<Fields> fields,Tables table){return null;}

    public void discoonetDB(){

    }

}
