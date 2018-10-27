package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class View {
    private Controller control;

    public TextArea query_output;

    // Update widgets and variables:
    public Button update_btn;
    public ComboBox fields_combo;
    public TextField ID_update, value_Update;

    // Create widgets and variables :
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
    public void setControl(Controller control){this.control=control;}
    /**
     * this method handles the event of clicking the SEARCH button
     * it will output the result into the output text area
     * @param mouseEvent
     */
    public void SearchHandler(MouseEvent mouseEvent)
    {
        String result;
        try {
            result = control.readEntry(ID_search.getText(), "USERS" ,Fields.userName); // gets the query results from the model
            if(result!="")
                clearFields();
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
        RESULT res=RESULT.Success;
        if(fields_combo.getSelectionModel().isEmpty()) return;  // checks if anything is selected from the combo box
        Fields field = Fields.valueOf(fields_combo.getValue().toString());// gets the field from the combo cox
        try{
            res = control.updateEntry("USERS", field, value_Update.getText(),Fields.userName,ID_update.getText());
            if(res==RESULT.Success)
                clearFields();
        }catch (NullPointerException e){ }
        query_output.setText("Update "+res.toString());     // output the result in the text area
    }

    /**
     * this method handles the event of clicking the CREATE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void CreateHandler(MouseEvent mouseEvent)
    {
        String entery = "";
        RESULT res=RESULT.Success;
        for (Node n : stackpanel.getChildren())              // Iterate though the fields to collect the data
        {
            if(n instanceof TextField)                      // only collect data from the text fields on the cell
                entery += ((TextField) n).getText().equals("") ? " ," : ((TextField) n).getText() + ",";
        }
        try {// for easier handling, the empty data is converted to " "
            res = control.addEntry(entery, "USERS");
            if(res==RESULT.Success)// calling the add method at the model and gets the result
                    clearFields();
            query_output.setText("Create "+res.toString());
        }catch (Exception e){
            query_output.setText("user already exist");// output the result to the output text area
        }
    }

    private void clearFields() {

        for (Node n : stackpanel.getChildren())              // Iterate though the fields to collect the data
        {
            if(n instanceof TextField)                      // only collect data from the text fields on the cell
                ((TextField) n).setText("");
        }

        ID_remove.setText("");
        value_Update.setText("");
        ID_search.setText("");
        ID_update.setText("");
    }

    /**
     * this method handles the event of clicking the REMOVE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void deleteHandler(MouseEvent mouseEvent)
    {
        RESULT res = control.deleteEntry(ID_remove.getText());
        if(res==RESULT.Success)
            clearFields();
        query_output.setText("Delete "+res.toString()+"!");// output the result to the output text area
    }
}
