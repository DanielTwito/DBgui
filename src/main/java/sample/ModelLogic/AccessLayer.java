package sample.ModelLogic;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.util.Pair;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessLayer {

    private Connection connection;

    public AccessLayer() {
        this.connection = null;
    }

    public void connectDB(String db_name){

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + db_name);
        }
        catch(Exception e) {
            System.out.println("could not establish connection");
        }
    }

    //TODO:Twito

    /**
     * this function get list of pairs that the pair key is the field of the tabel
     * and the pair value is the value you want to insert
     * @param data list of pairs
     * @param table the wanted table to insert
     * @return RESULT SUCCESS or FAIL
     */
    public RESULT AddEntry(ArrayList<Pair> data, Tables table){
       int size=data.size();
        if (size==0) {
            return RESULT.Fail;
        }

        StringBuilder paramsFields= new StringBuilder(" (");
        StringBuilder paramsFValues= new StringBuilder(" VALUES (");
        String qry = "INSERT INTO "+table;
        for(int i=0;i<size-1;i++){
            paramsFields.append("'").append(data.get(i).getKey()).append("', ");
            paramsFValues.append("?, ");
        }
        paramsFields.append("'").append(data.get(size - 1).getKey()).append("') ");
        paramsFValues.append("? )");
        qry=qry+paramsFields.toString()+paramsFValues.toString();
        try {
            PreparedStatement stmt = connection.prepareStatement(qry);
            for (int i = 1; i <=size ; i++) {
                stmt.setString(i,(String)data.get(i-1).getValue());
            }
            stmt.execute();
        }catch (Exception e){
            e.printStackTrace();

        }
        return RESULT.Success;
    }
    //TODO:Netanel
    public String[][] ReadEntry(List<String> value, List<Fields> fields, Tables table){return null;}
    //TODO:Netanel
    public RESULT UpdateEntry(Tables table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){return null;}
    //TODO:Twito
    public RESULT DeleteEntry (List<String> value,List<Fields> fields,Tables table){return null;}

    public void discoonetDB(){
        try {
            connection.close();
        }catch (Exception e){
            System.out.println("could not close connection");
        }
    }

}
