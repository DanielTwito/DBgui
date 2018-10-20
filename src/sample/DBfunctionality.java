package sample;
import java.sql.*;
import java.io.*;
public class DBfunctionality {
    /**
     * This Function is in charge of adding a new Entry to the Database
     * by the following order (userName, password, birthDate, firstName, lastName, homeTown)
     * @param newUser String that represents the new fields of the user
     * @return Boolean value whether the Entry was added or not
     */
    public boolean addEntry(String newUser){
        String[] userInfo = newUser.split(",");
        return true;
    }

    /**
     * This Function is in charge of reading(searching) the Database for a specific entry
     * @param username String that represents the userName of the specific user
     * @return A string that represents the User in the following order (userName, password, birthDate, firstName, lastName, homeTown)
     */
    public String readEntry(String username){
        return null;
    }

    /**
     * This Function is in charge of updating(changing) a specific entry in the database
     * @param username String that represents the userName of the specific user
     * @param field Enum from the field to Change
     * @param newValue The new value of the field
     * @return Boolean value whether the Entry was changed or not
     */
    public boolean updateEntry(String username, Fields field, String newValue){
        return true;
    }

    /**
     * This Function is in charge of deleting an entry in the Database
     * @param username String that represents the userName of the specific user
     * @return Boolean value whether the Entry was deleted or not
     */
    public boolean deleteEntry(String username){
        return true;
    }

    @Override
    public String toString() {
        return "DBfunctionality";
    }
}
