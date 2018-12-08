package sample;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
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
    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table){return  null;}
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table)
    {
        return  null;
    }
    public RESULT UpdateEntries(Tables table, String fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues){return null;}
    public RESULT DeleteEntry (List<String> value,List<Fields> fields,Tables table){return null;}
}
//TODO: WHEN GETTING DATA FROM
