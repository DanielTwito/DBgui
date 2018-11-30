package sample;

import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.ModelLogic.Model;

import java.sql.SQLException;

public class Controller {

    private Model model = null;                   //it may be more generic to use and interface for the Model

    /**
     * this method set the Model for the controller, the Model can only be set once
     * @param model
     */
    public void setModel(Model model)
    {
        if(this.model != null) return;                      // Model can only be set once
        this.model = model;
    }
    public RESULT AddEntry(String[] data, Tables table){return  null;}
    public String ReadEntry(String Key, Tables table){return null;}
    public RESULT UpdateEntry(Tables table, Fields fieldToUpdate, String newValue, Fields wantedField, String data){return null;}
    public RESULT DeleteEntry (String key,Tables table){return null;}

}

