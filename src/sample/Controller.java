package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.xml.transform.Result;

public class Controller {

    private DBfunctionality model = null;                   //it may be more generic to use and interface for the model

    public TextArea query_output;

    // Update widgets and variables:
    public Button update_btn;
    public ComboBox fields_combo;
    public TextField ID_update, value_Update;

    // Create widgets and variables:
    public Button create_btn;
    public StackPane stackpanel;      // the grid cell contains a stack panel for easy iteration through the text fields
    public TextField ID_create, password_create, birthDate_create,
                        firstName_create, lastName_create, homeTown_create;

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
        if(this.model != null) return;                      // model can only be set once
        this.model = model;
    }

    /**
     * this method handles the event of clicking the SEARCH button
     * it will output the result into the output text area
     * @param mouseEvent
     */
    public void SearchHandler(MouseEvent mouseEvent)
    {
        String result;
        try { result = model.readEntry(ID_search.getText(), "USERS" ,Fields.userName); // gets the query results from the model
        }
        catch(NullPointerException e) {                                       // catches an exception connection failed
            query_output.setText("There was a problem connection the database, please try again.");
            return;
        }
        if(result.equals("")) query_output.setText("User not found.");
        else query_output.setText(result);                    // put the result in the output text area
    }

    /**
     * this method handles the event of clicking the UPDATE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void UpdateHandler(MouseEvent mouseEvent)
    {
        RESULT res=RESULT.Fail;
        if(fields_combo.getSelectionModel().isEmpty()) return;  // checks if anything is selected from the combo box
        Fields field = Fields.valueOf(fields_combo.getValue().toString());// gets the field from the combo cox
        try{
            res = model.updateEntry("users", field, value_Update.getText(),Fields.userName,ID_update.getText());
        }catch (NullPointerException e){
            System.out.println("bug");
        }
        query_output.setText("Update "+res.toString());     // output the result in the text area
    }

    /**
     * this method handles the event of clicking the CREATE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void CreateHandler(MouseEvent mouseEvent) {
        String entery = "";
        for (Node n : stackpanel.getChildren())              // Iterate though the fields to collect the data
        {
            if (n instanceof TextField)                      // only collect data from the text fields on the cell
                entery += ((TextField) n).getText().equals("") ? " ," : ((TextField) n).getText() + ",";
        }
        try {// for easier handling, the empty data is converted to " "
            RESULT res = model.addEntry(entery);                // calling the add method at the model and gets the result


        }catch (Exception e){
            query_output.setText(e.toString());// output the result to the output text area
        }
    }

    /**
     * this method handles the event of clicking the REMOVE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void deleteHandler(MouseEvent mouseEvent)
    {
        RESULT res = model.deleteEntry(ID_remove.getText());
        query_output.setText("Delete "+res.toString()+"!");// output the result to the output text area
    }
}

/*
to do list:
TODO: create method CREATE() <fill name of the dude whos going to do it>
TODO: create method UPDATE() <fill name of the dude whos going to do it>
TODO: create method READ() by Elad
TODO: create method REMOVE() <fill name of the dude whos going to do it>
 */