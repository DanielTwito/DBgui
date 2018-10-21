package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    // a boolean variable to enable various in code debug
    static final boolean _DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("SQLInterface.fxml").openStream());

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 780));

        Controller controller = fxmlLoader.getController();     //gettin the controller for the FXML
        controller.setModel(new DBfunctionality("Database/projectdb.db"));//setting a model for it

        primaryStage.show();
    }


    public static void main(String[] args) {
        ValidateDatabase("Database/projectdb.db");
        launch(args);
    }

    /**
     * this method checks if a database exist, creates a new database in the given name if
     * database does not exist.
     * @param db_name - the name of the database we wish to check
     */
    public static void ValidateDatabase(String db_name) {
        Connection c = null;                                    //this object holds the connection to the database

        File db = new File(db_name);                            //a file object to point to the database file
        if (!db.exists()) {                                     //checks if the database exist, if not than creates it
            try {                                               //creates a new database inside the Database directory
                c = DriverManager.getConnection("jdbc:sqlite:" + db_name);
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }

        if (_DEBUG)                                              //for debugging purposes
            System.out.println("Opened database successfully");
    }
}

/*
to do list:
TODO: switch _DEBUG to false at the end of the development
 */
