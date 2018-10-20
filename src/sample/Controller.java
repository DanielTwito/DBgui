package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class Controller {

    private Connection database;                            //connection object to hold the connection to the database
    private DBfunctionality model = null;

    public TextArea query_output;

    // Update widgets and variables:
    public Button update_btn;
    public ComboBox fields_combo;
    public TextField ID_update, value_Update;

    // Create widgets and variables:
    public Button create_btn;
    public TextField ID_create, password_create, birthDate_create,
                        firstName_create, lastName_create, hometown_create;

    // Read widgets and variables:
    public Button search_btn;
    public TextField ID_search;

    // Remove widgets and variables:
    public Button remove_btn;
    public TextField ID_remove;

    /**
     * this method is called by the FXMLLoader, it will initialize the controls in the FXML file
     * if you want to initialize any component in the FXML before hand, do it here.
     */
    @FXML
    public void initialize(){
        // ComboBox initialize
        for (Fields f: Fields.values())                     // Iterates though the ENUM
        {
            if(Main._DEBUG)
                System.out.println(f.toString());
            fields_combo.getItems().addAll(f.toString());   // each ENUM vale become an item for the combo box
        }//end foreach
    }//end initialize

    /**
     * this method set the model for the controller, the model can only be set once
     * @param model
     */
    public void setModel(DBfunctionality model)
    {
        if(this.model != null) return;
        this.model = model;
    }
}

/*
to do list:
TODO: create method CREATE() <fill name of the dude whos going to do it>
TODO: create method UPDATE() <fill name of the dude whos going to do it>
TODO: create method READ() <fill name of the dude whos going to do it>
TODO: create method REMOVE() <fill name of the dude whos going to do it>
 */