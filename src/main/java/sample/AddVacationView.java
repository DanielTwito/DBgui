package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    private Controller control;
    private LocalDate startD;
    private LocalDate endD;
    public void setControl(Controller control) {
        this.control = control;
    }
    public void addVacation(ActionEvent event) {

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
        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    Price.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        if(Price.getText().trim().isEmpty()){
            msg.append("please enter a price\n");
        }
        if(!Price.getText().trim().isEmpty() && Integer.parseInt(Price.getText().trim())<=0){msg.append("please enter a price higher then zero\n");}
        if (startD == null || endD == null) {
            msg.append("please add vacation dates\n");
        }
        if (startD != null && endD != null && Period.between(endD, startD).getDays() < 0) {
            msg.append("vacation return date must be later then departure date \n");
        }
        if (msg.length() == 0) {
            ArrayList<Pair> vac = new ArrayList<>();
            vac.add(new Pair<>(Fields.vacationType, vacationTypeTXT.getText().trim()));
            vac.add(new Pair<>(Fields.airport, AirLineTXT.getText().trim()));
            vac.add(new Pair<>(Fields.destination, destinationTXT.getText().trim()));
            vac.add(new Pair<>(Fields.Flydate, startD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim()));
            vac.add(new Pair<>(Fields.Returndate, endD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim()));
            vac.add(new Pair<>(Fields.adultTickets, adultAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.childTickets, ChildAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.babyTickets, Babyamount.getValue().toString()));
            vac.add(new Pair<>(Fields.baggage, LuggageType.getValue()));
            vac.add(new Pair<>(Fields.placeRank, roomRank.getValue()));
            vac.add(new Pair<>(Fields.includeRoom, withStuff.isSelected()));
            vac.add(new Pair<>(Fields.includeReturn, withReturn.isSelected()));
            vac.add(new Pair<>(Fields.price,Price.getText().trim()));
            System.out.println("adding vacation DONE");
            //control.AddEntry(vac,Tables.ListingVacations);Username
        } else {
            errorBoard.setText(msg.toString());
        }
    }

    public void addStart(ActionEvent event) { startD = startDate.getValue();}

    public void addEnd(ActionEvent event) {endD = endDate.getValue();}
    }
