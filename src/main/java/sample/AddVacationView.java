package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;

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
    TextArea errorBoard;
    @FXML
    Button submit;

    private Controller control;
    private LocalDate startD;
    private LocalDate endD;

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddVacation.fxml"));
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        LuggageType.getItems().removeAll(LuggageType.getItems());
        LuggageType.getItems().addAll("no luggage","hand luggage","Large suitcase","Large suitcase and hand luggage");
        LuggageType.getSelectionModel().select("Option B");
        startDate.setOnAction((ActionEvent event) -> startD = startDate.getValue());
        endDate.setOnAction((ActionEvent event) -> endD = endDate.getValue());
        // scene.getStylesheets().add(getClass().getResource("RegisterForm.css").toExternalForm());
        stage.setTitle("Add vacation");
        stage.show();
    }
    public void setControl(Controller control) {
        this.control = control;
    }
    public void addVcation(ActionEvent event) {
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
        if (startD == null || endD == null) {
            msg.append("please add vacation dates\n");
        }
        if (startD != null && endD != null && Period.between(endD, startD).getDays() < 0) {
            msg.append("vacation return date must be later then departure date \n");
        }
        if (msg.length() == 0) {
            ArrayList<Pair> vac = new ArrayList<>();
            vac.add(new Pair<>(Fields.vacationType, vacationTypeTXT.getText().trim()));
            vac.add(new Pair<>(Fields.airline, AirLineTXT.getText().trim()));
            vac.add(new Pair<>(Fields.destination, destinationTXT.getText().trim()));
            vac.add(new Pair<>(Fields.FlightDate, startD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim()));
            vac.add(new Pair<>(Fields.ReturnDate, endD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim()));
            vac.add(new Pair<>(Fields.adultTickets, adultAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.childTickets, ChildAmount.getValue().toString()));
            vac.add(new Pair<>(Fields.babyTickets, Babyamount.getValue().toString()));
            vac.add(new Pair<>(Fields.Baggage, LuggageType.getValue()));
            vac.add(new Pair<>(Fields.placeRank, roomRank.getValue()));
            vac.add(new Pair<>(Fields.includeRoom, withStuff.isSelected()));
            vac.add(new Pair<>(Fields.includeReturn, withReturn.isSelected()));
        } else {
            errorBoard.setText(msg.toString());
        }
    }
}
