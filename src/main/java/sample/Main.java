package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.ModelLogic.AccessLayer;
import sample.ModelLogic.Model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    // a boolean variable to enable various in code debug
    static final boolean _DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control=new Controller();
        AccessLayer accessLayer = new AccessLayer();
        accessLayer.connectDB("D:\\DBgui\\Database\\projectdb.db");
        control.setAc(accessLayer);

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../ViewVacation.fxml").openStream());

        primaryStage.setTitle("ViewVacation");
        Scene scene = new Scene(root, 600, 500);
      //  scene.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
        primaryStage.setScene(scene);

        ViewVacation viewvac = fxmlLoader.getController();     //getting the controller for the FXML

        viewvac.setControl(control);
        viewvac.setVacID("1");
        control.setModel(new Model("Database/projectdb.db"));//setting a Model for it

        primaryStage.show();
    }


//    public static void main(String[] args) {
//        ValidateDatabase("Database/projectdb.db");
//        launch(args);
//    }
//
//    /**
//     * this method checks if a database exist, creates a new database in the given name if
//     * database does not exist.
//     * @param db_name - the name of the database we wish to check
//     */
//    public static void ValidateDatabase(String db_name) {
//        Connection c = null;                                    //this object holds the connection to the database
//
//        File db = new File(db_name);                            //a file object to point to the database file
//        if (!db.exists()) {                                     //checks if the database exist, if not than creates it
//            try {                                               //creates a new database inside the Database directory
//                c = DriverManager.getConnection("jdbc:sqlite:" + db_name);
//                Statement stmt = c.createStatement();
//                String sql = "CREATE TABLE USERS " +            //creates a users table
//                        "(userName VARCHAR(20) PRIMARY KEY     NOT NULL," +
//                        " password         TEXT(20)    NOT NULL, " +
//                        " birthDate        DATE        NOT NULL, " +
//                        " firstName        VARCHAR(20) NOT NULL, " +
//                        " lastName         VARCHAR(20) NOT NULL," +
//                        " homeTown         VARCHAR(20) NOT NULL";
//                stmt.executeUpdate(sql);
//                stmt.close();
//                c.close();
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
//            }
//        }
//        if (_DEBUG)                                              //for debugging purposes
//            System.out.println("Opened database successfully");
//    }
//}
//



    public static void main(String[] args) {
//        ValidateDatabase("Database/projectdb.db");
        launch(args);
//        AccessLayer al=new AccessLayer();
//        al.connectDB("D:\\DBgui\\Database\\projectdb.db");
//        ArrayList<Pair> whereCondition = new ArrayList<>();
//        whereCondition.add(new Pair<>(Fields.VacID, "1"));
//        HashMap<String,String> vacationDetails= al.ReadEntries(whereCondition, Tables.ListingVacation).get(0);
//        String v=null;
//        for (String key:
//             vacationDetails.keySet()) {
//                v = vacationDetails.get(key);
        }

}



