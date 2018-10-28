package main.java.sample;

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

    /**
     * this method set the model for the controller, the model can only be set once
     * @param model
     */
    public void setModel(DBfunctionality model)
    {
        if(this.model != null) return;                      // model can only be set once
        this.model = model;
    }


    public String readEntry(String data,  String table, Fields field){return model.readEntry(data, table, field);}public RESULT addEntry(String newUser,String table)throws Exception{return model.addEntry(newUser, table);}
    public RESULT updateEntry(String table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){return model.updateEntry(table, fieldToUpdate, newValue, wantedField, data);}
    public RESULT deleteEntry(String username){return model.deleteEntry(username);}

}

