package sample;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.ModelLogic.AccessLayer;
import sample.ModelLogic.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table){return  model.AddEntry(fieldsNvalues,table);}
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table){ return model.ReadEntries(fieldsNvalues,table);}

    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues){return model.UpdateEntries(table,fieldToUpdate,newValue,fieldsNvalues);}
    public RESULT DeleteEntry (Tables table, ArrayList<Pair> fieldValues){return model.DeleteEntry(table,fieldValues);}
}
//TODO: WHEN GETTING DATA FROM

