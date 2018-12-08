package sample.ModelLogic;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table){return  null;}


    /**
     * This function reads entries from DB according the given parameters.
     * Actually it executes a SELECT query command.
     * The function selects the all columns of the given table
     * @param table- "FROM" which table to read
     * @param fieldsNvalues- list of fields and their values to put in "WHERE" condition
     * @return Table - the result of the query
     */
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table){

        ArrayList<HashMap<String, String>> ans = new ArrayList<>();
        StringBuilder fields = new StringBuilder();
        for (Pair fieldsNvalue : fieldsNvalues) fields.append(fieldsNvalue.getKey()).append("= ? and ");
        String sql = "SELECT * FROM "+table+" WHERE "+fields;
        sql=sql.substring(sql.length()-5);

        try (PreparedStatement pstmt  = connection.prepareStatement(sql)){

            for (int i = 1; i < fieldsNvalues.size(); i++)
                pstmt.setString(1, (String) fieldsNvalues.get(i).getValue());

            ResultSet result  = pstmt.executeQuery();
            ResultSetMetaData meta=result.getMetaData();
            int columnCount = meta.getColumnCount();


            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                HashMap<String, String> map = new HashMap<String, String>();
                String name = meta.getColumnName(i);
                map.put(name,result.getString(name));
                ans.add(map);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


//        StringBuilder where = new StringBuilder();
//        where.append(fieldsNvalues.get(0).getKey()).append("=").append(fieldsNvalues.get(0).getValue());
//        for (int i = 1; i < fieldsNvalues.size(); i++) {
//            where.append("and").append(fields.toString().get(i)).append("=").append(values.get(i));
//        }
//        try {
//            Statement query = connection.createStatement();
//            ResultSet result=query.executeQuery("SELECT * FROM "+table+" WHERE "+where+";");
//            ResultSetMetaData meta=result.getMetaData();
//            int columnCount = meta.getColumnCount();
//
//            // The column count starts from 1
//            for (int i = 1; i <= columnCount; i++ ) {
//                String name = meta.getColumnName(i);
//                map.put(name,result.getString(name));
//            }
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }

        return ans;
    }

    /**
     * This function updates entries on DB according the given parameters
     * @param table which table to update
     * @param fieldToUpdate- given field to update
     * @param newValue- the new value to assign to given field
     * @param fieldsNvalues- list of fields and their values to put in "WHERE" condition
     * @return RESULT whether the update succeeded
     */
    public RESULT UpdateEntries(Tables table, String fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues){

        RESULT out=RESULT.Success;
        StringBuilder fields = new StringBuilder();
        for (Pair fieldsNvalue : fieldsNvalues) fields.append(fieldsNvalue.getKey()).append(" = ? and ");
        String sql = "UPDATE "+table+" SET "+fieldToUpdate+" = "+newValue+" WHERE "+fields;
        sql=sql.substring(sql.length()-5);

        try (PreparedStatement pstmt  = connection.prepareStatement(sql)){

            for (int i = 1; i < fieldsNvalues.size(); i++)
                pstmt.setString(1, (String) fieldsNvalues.get(i).getValue());

            int res=pstmt.executeUpdate();
            if(res==0)
                out = RESULT.Fail;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }


    public RESULT DeleteEntry (List<String> value,List<Fields> fields,Tables table){return null;}

    public void discoonetDB(){
        try {
            connection.close();
        }catch (Exception e){
            System.out.println("could not establish connection");
        }
    }

    //    DDL FOR ListingVacation TABLE:
    //
    //    CREATE TABLE ListingVacation (
    //            VacID      INT (16) PRIMARY KEY,
    //    airline    STRING(24),
    //    FlightDate    STRING(16) NOT NULL,
    //    ReturnDate    STRING(16),
    //    Baggage    STRING(16),
    //    adultTickets    INT(10),
    //    childTickets    INT(10),
    //    babyTickets    INT(10),
    //    destination    STRING(24) NOT NULL,
    //    includeReturn    BOOLEAN NOT NULL,
    //    vacationType    STRING(24),
    //    includeRoom    BOOLEAN,
    //    placeRank    INT(3)
    //);

    // ADD RECORD TO ListingVacation query
//     INSERT INTO ListingVacation (VacID, airline, FlightDate, ReturnDate, Baggage, adultTickets, childTickets, babyTickets, destination, includeReturn, vacationType, includeRoom, placeRank)
//          VALUES (4,"Air1", "1/1/2091", "1/1/2092", "Hand", 5, 2, 2, "HAIFA", true, "idk", 1, 3);
//         INSERT INTO ListingVacation (VacID, airline, FlightDate, ReturnDate, Baggage, adultTickets, childTickets, babyTickets, destination, includeReturn, vacationType, includeRoom, placeRank)
//          VALUES (4,\"Air1\", \"1/1/2091\", \"1/1/2092\", \"Hand\", 5, 2, 2, \"HAIFA\", true, \"idk\", 1, 3);
}
