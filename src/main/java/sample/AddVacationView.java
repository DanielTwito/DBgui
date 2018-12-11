package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddVacationView {
    //Controls in the javaFX
    @FXML
    TextField vacationTypeTXT;
    @FXML
    TextField AirLineTXT;
    @FXML
    TextField destinationTXT;
    @FXML
    TextField Price;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    Spinner<Integer> adultAmount;
    @FXML
    Spinner<Integer> ChildAmount;
    @FXML
    Spinner<Integer> Babyamount;
    @FXML
    ComboBox<String> LuggageType;
    @FXML
    Slider roomRank;
    @FXML
    CheckBox withReturn;
    @FXML
    CheckBox withStuff;
    @FXML
    Text errorBoard;
    @FXML
    Button submit;
    public String retunD;
    private Controller control;
    private LocalDate startD;
    private LocalDate endD;

    @FXML
    /**
     * initialize the View related variables
     */
    public void initialize() {
        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    Price.setText(newValue.replaceAll("[^\\d]", ""));
                }}});}

    /**
     * sets the controller for the class
     * @param control
     */
    public void setControl(Controller control) {
        this.control = control;
    }

    /**
     * Reaction to the "submit" button action event.
     * gets all the parameters from the data fields,
     * checks the input, react if is not correct, if is correct
     * cascade the data up to controller
     * @param event
     */
    public void addVacation(ActionEvent event) {
        errorBoard.setText("");
        StringBuilder msg = new StringBuilder();
        if (vacationTypeTXT.getText().trim().isEmpty()) {
            msg.append("please add vacation type\n");
        }
        if (destinationTXT.getText().trim().isEmpty()) {
            msg.append("please add destination\n");
        }
        if (AirLineTXT.getText().trim().isEmpty()) {
            msg.append("please add airline\n");
        }
        if(LuggageType.getValue()==null){
            msg.append("please choose included baggage\n");
        }
        if(Price.getText().trim().isEmpty()){
            msg.append("please enter a price\n");
        }
        if(!Price.getText().trim().isEmpty() && Integer.parseInt(Price.getText().trim())<=0){msg.append("please enter a price higher then zero\n");}
        if (startD == null ) {
            msg.append("please add flight date\n");
        }
        if(withReturn.isSelected())
            if(endD == null)
                msg.append("please add return date\n");

        if (startD != null && endD != null && Period.between(endD, startD).getDays() > 0) {
            msg.append("vacation return date must be later then departure date \n");}
        if(Babyamount.getValue()+ChildAmount.getValue()+adultAmount.getValue()==0){
            msg.append("there must be at least 1 passanger \n");}

        if(!withReturn.isSelected()){
            retunD=" ";
        }
        int stuff; int toreturn;
        if(withStuff.isSelected()){stuff=1;}else{stuff=0;}
        if(withReturn.isSelected()){toreturn=1;}else{toreturn=0;}
        if(withReturn.isSelected()){retunD=endD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim();}
        if (msg.length() == 0) {
            ArrayList<Pair> vac = new ArrayList<>();
            vac.add(new Pair<>(Fields.vacationType+"", vacationTypeTXT.getText().trim()));
            vac.add(new Pair<>(Fields.airline+"", AirLineTXT.getText().trim()));
            vac.add(new Pair<>(Fields.destination+"", destinationTXT.getText().trim()));
            vac.add(new Pair<>(Fields.FlightDate+"", startD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim()));
            vac.add(new Pair<>(Fields.Returndate+"", retunD));
            vac.add(new Pair<>(Fields.adultTickets+"", adultAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.childTickets+"", ChildAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.babyTickets+"", Babyamount.getValue().toString()));
            vac.add(new Pair<>(Fields.baggage+"", LuggageType.getValue()+""));
            vac.add(new Pair<>(Fields.placeRank+"", roomRank.getValue()+""));
            vac.add(new Pair<>(Fields.includeRoom+"", stuff+""));
            vac.add(new Pair<>(Fields.includeReturn+"", toreturn+""));
            vac.add(new Pair<>(Fields.price+"",Price.getText().trim()));
            control.AddEntry(vac, Tables.ListingVacation);
        } else {
            errorBoard.setText(msg.toString());
        }
    }

    /**
     * reacts to the flight date field fill event
     * sets the data as string
     * @param event
     */
    public void addStart(ActionEvent event) { startD = startDate.getValue();}

    /**
     * reacts to the flight return date field fill event
     * sets the data as string
     * @param event
     */
    public void addEnd(ActionEvent event) {endD = endDate.getValue();}
    }
