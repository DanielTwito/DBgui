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
    public RESULT addEntry(String newUser){
        String[] userInfo = newUser.split(",");
        return RESULT.Success;
    }

    /**
     * This Function is in charge of reading(searching) the Database for a specific entry
     * @param data String that represents the userName of the specific user
     * @param field the Field to search in
     * @return A string that represents the User in the following order (userName, password, birthDate, firstName, lastName, homeTown)
     */
    public String readEntry(String data, Fields field)
    {
        Connection c = openConnection();
        String out = "";
        if(c == null) throw new NullPointerException();
        try
        {
            Statement query = c.createStatement();                  // create a statement and execute the query
            ResultSet result = query.executeQuery("SELECT * FROM USERS WHERE "+field.toString()+"="+data);
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
     * @param username String that represents the userName of the specific user
     * @param field Enum from the field to Change
     * @param newValue The new value of the field
     * @return RESULT value whether the Entry was changed or not
     */
    public RESULT updateEntry(String username, Fields field, String newValue){
        return RESULT.Success;
    }

    /**
     * This Function is in charge of deleting an entry in the Database
     * @param username String that represents the userName of the specific user
     * @return Boolean value whether the Entry was deleted or not
     */
    public RESULT deleteEntry(String username){
        return RESULT.Success;
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
