package sample.ModelLogic;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.util.Pair;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
     * this function add entries on table in the DB according to the given parameters
     * @param table which table to add
     * @param data list of fields and thie values to put in the table
     * @return RESULT SUCCESS or FAIL
     *
     * note: you must give the fields as they written in the data base!
     */
    public RESULT AddEntry(Tables table,ArrayList<Pair> data){
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

    /**
     * this function delete entries from tabel on the DB acoording to the given params
     * @param table from which table to delete
     * @param fieldValues list of fields and their values to put on the WHERE condition
     * @return RESULT whether the delete succeeded
     */
    public RESULT DeleteEntry (Tables table, ArrayList<Pair> fieldValues){
        int size= fieldValues.size();
        String qry="DELETE FROM "+table+" WHERE ";
        StringBuilder whereParam=new StringBuilder();
        for (int i = 0; i < size-1 ; i++) {
            whereParam.append(fieldValues.get(i).getKey()).append(" = ? and ");
        }
        whereParam.append(fieldValues.get(size - 1).getKey()).append(" = ? ;");
        qry = qry + whereParam.toString();
        try {
            PreparedStatement stmt = connection.prepareStatement(qry);
            for (int i = 1; i <=size ; i++) {
              stmt.setString(i,fieldValues.get(i-1).getValue().toString());
            }
            stmt.execute();
        }catch (Exception e){
            return RESULT.Fail;

        }
        return RESULT.Success;

    }

    public void discoonetDB(){
        try {
            connection.close();
        }catch (Exception e){
            System.out.println("could not close connection");
        }
    }

}
