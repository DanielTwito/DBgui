package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main extends Application {
    // a boolean variable to enable various in code debug
    static final boolean _DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control=new Controller();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("SQLInterface.fxml").openStream());

        primaryStage.setTitle("Vacation4u");
        Scene scene = new Scene(root, 1000, 780);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

        View view = fxmlLoader.getController();     //getting the controller for the FXML

        view.setControl(control);
        control.setModel(new DBfunctionality("Database/projectdb.db"));//setting a model for it

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
                Statement stmt = c.createStatement();
                String sql = "CREATE TABLE USERS " +            //creates a users table
                        "(userName VARCHAR(20) PRIMARY KEY     NOT NULL," +
                        " password         TEXT(20)    NOT NULL, " +
                        " birthDate        DATE        NOT NULL, " +
                        " firstName        VARCHAR(20) NOT NULL, " +
                        " lastName         VARCHAR(20) NOT NULL," +
                        " homeTown         VARCHAR(20) NOT NULL";
                stmt.executeUpdate(sql);
                stmt.close();
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

