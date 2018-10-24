package sample;

import java.sql.*;

public class DBfunctionality {
    private final String db_name;

    /**
     * c'tor, gets the name of the database
     * @param db_name - the name of the database
     */
    public DBfunctionality(String db_name) {
        this.db_name = db_name;
    }

    /**
     * This Function is in charge of adding a new Entry to the Database
     * by the following order (userName, password, birthDate, firstName, lastName, homeTown)
     * @param newUser String that represents the new fields of the user
     * @return RESULT value whether the Entry was added or not
     */
    public RESULT addEntry(String newUser)throws Exception {
        String[] userInfo = newUser.split(",");

        for (int i = 0; i <userInfo.length ; i++) {
            userInfo[i]="'"+userInfo[i]+"'";
            System.out.println(userInfo[i]);
        }
        Connection c = openConnection();
        String qry= "INSERT INTO users("+       Fields.userName+','+
                Fields.password+ ',' +
                Fields.birthDate+','+
                Fields.firstName+','+
                Fields.lastName+','+
                Fields.homeTown+')'+
                "VALUES("+ userInfo[0]+','+
                userInfo[1]+','+
                userInfo[2]+','+
                userInfo[3]+','+
                userInfo[4]+','+
                userInfo[5]+
                ");";


        System.out.println(qry);

            Statement query = c.createStatement();                  // create a statement and execute the query
            query.executeUpdate(qry);
            c.close();

        return RESULT.Success;
    }

    /**
     * This Function is in charge of reading(searching) the Database for a specific entry
     * @param data String that represents the userName of the specific user
     * @param field the Field to search in
     * @param table
     * @return A string that represents the User in the following order (userName, password, birthDate, firstName, lastName, homeTown)
     */
    public String readEntry(String data,  String table, Fields field)
    {
        Connection c = openConnection();
        String out = "";
        if(c == null) throw new NullPointerException();
        try
        {
            Statement query = c.createStatement();                  // create a statement and execute the query
            ResultSet result = query.executeQuery("SELECT * FROM "+table+" WHERE "+field.toString()+"='"+data+"';");
            while(result.next())                                    // collection all data received
            {
                out += result.getString("userName")+",";
                out += result.getString("password")+",";
                out += result.getString("birthDate")+",";
                out += result.getString("firstName")+",";
                out += result.getString("lastName")+",";
                out += result.getString("homeTown")+"\n";
            }
            c.close();
        }
        catch(Exception e) { throw new NullPointerException(); }
        return out;
    }//TODO: this method is untested, must test it before publish

    /**
     * This Function is in charge of updating(changing) a specific entry in the database
     * @param table String that represents the table to update
     * @param fieldToUpdate Enum from the field to Change
     * @param newValue String that represents the new value to update
     * @param wantedField which field check in the condition
     * @param data records that are equals to given data in the wanted field will be update
     * @return RESULT value whether the Entry was changed or not
     */
    public RESULT updateEntry(String table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){
        Connection c = openConnection();
        RESULT out=RESULT.Success;
        if(c == null) throw new NullPointerException();
        try
        {
            Statement query = c.createStatement();                  // create a statement and execute the query
            int result = query.executeUpdate("UPDATE "+table+" SET "+fieldToUpdate.toString()+" = '"+ newValue +"' WHERE "+ wantedField+" = '"+ data+"';");
            if(result==0) {
                out = RESULT.Fail;

            }
            c.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw new NullPointerException();
        }

        return out;
    }

    /**
     * This Function is in charge of deleting an entry in the Database
     * @param username String that represents the userName of the specific user
     * @return Boolean value whether the Entry was deleted or not
     */
    public RESULT deleteEntry(String username){
        Connection c = openConnection();
        if(c == null) throw new NullPointerException();
        try {
            Statement query = c.createStatement();                             // create a statement
            String sql = "DELETE FROM USERS WHERE userName = '"+username+"';"; //the sql query to delete
            int works = query.executeUpdate(sql);                                          //execute the sql query
        c.close();
        if(works==1){                    //checks whether the entry was deleted
            return RESULT.Success;
        }
        else return RESULT.Fail;
    }
        catch(Exception e) { throw new NullPointerException(); }
    }
    @Override
    public String toString() {
        return "DBfunctionality";
    }

    /**
     * this method creates and returns a connection object, to avoid code duplicates.
     * @return Connection object
     */
    private Connection openConnection()
    {
        Connection c = null;
        try { c = DriverManager.getConnection("jdbc:sqlite:" + db_name); } // open connection
        catch(Exception e) { System.out.println("could not establish connection"); }
        return c;
    }
}
