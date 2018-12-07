package sample.ModelLogic;

import com.sun.xml.internal.bind.v2.TODO;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessLayer {

    private Connection connection;

    public void connectDB(String db_name){

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + db_name);
        }
        catch(Exception e) {
            System.out.println("could not establish a connection to database");
        }
    }

    //TODO:Twito
    public RESULT AddEntry(List<String> data, Tables table){return  null;}


    /**
     * This function reads an entries from DB according the given parameters.
     * Actually it executes a SELECT query command.
     * The function selects the all columns of the given table
     * @param values- list of values that corresponds to fields list
     * @param fields- list of fields to put in WHERE condition
     * @param table- "FROM" which table to read
     * @return
     */
    public HashMap<String, String> ReadEntries(ArrayList<String> values, ArrayList<String> fields, Tables table){

        HashMap<String, String> map = new HashMap<String, String>();
        StringBuilder where = new StringBuilder();
        where.append(fields.get(0)).append("=").append(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            where.append("and").append(fields.get(i)).append("=").append(values.get(i));
        }
        try {
            Statement query = connection.createStatement();
            ResultSet result=query.executeQuery("SELECT * FROM "+table+" WHERE "+where+";");
            ResultSetMetaData meta=result.getMetaData();
            int columnCount = meta.getColumnCount();

            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                String name = meta.getColumnName(i);
                map.put(name,result.getString(name));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return map;
    }

    /**
     *
     * @param table which table to update
     * @param fieldToUpdate- given field to update
     * @param newValue-
     * @param wantedField
     * @param data
     * @return
     */
    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){

        RESULT out=RESULT.Success;
        try
        {
            Statement query = connection.createStatement();                  // create a statement and execute the query
            int result = query.executeUpdate("UPDATE "+table+" SET "+fieldToUpdate.toString()+" = '"+ newValue +"' WHERE "+ wantedField+" = '"+ data+"';");
            if(result==0) {
                out = RESULT.Fail;

            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw new NullPointerException();
        }

        return out;
    }

    //TODO:Twito
    public RESULT DeleteEntry (List<String> value,List<Fields> fields,Tables table){return null;}

    public void discoonetDB(){
        try {
            connection.close();
        }catch (Exception e){
            System.out.println("could not establish connection");
        }
    }

}
