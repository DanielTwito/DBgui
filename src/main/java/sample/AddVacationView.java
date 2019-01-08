package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddVacationView {
    //Controls in the javaFX
    @FXML
    AnchorPane mainSignUp;
    @FXML
    CheckBox isConnection;
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
    @FXML
    CheckBox isTradeable;

    public String retunD;
    private Controller control;
    private LocalDate startD;
    private LocalDate endD;
    private String user;

    public static double VacID = 10000;
    @FXML
    public void initialize() {
        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    Price.setText(newValue.replaceAll("[^\\d]", ""));
                }}});
        BackgroundImage myBI= new BackgroundImage(
                new Image(getClass().getClassLoader().getResourceAsStream("addToListingBackground.png"),
                        800,550,
                        false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainSignUp.setBackground(new Background(myBI));

    }

    /**
     * setter for the vacationID
     * @param id
     */
    public void setVacID(double id)
    {
        if(VacID < id + 1) VacID = id + 1;
    }
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
        int x = Period.between(LocalDate.now(), startD).getDays();
        int y = Period.between(LocalDate.now(), endD).getDays();
        if(startD != null && endD != null && (Period.between(LocalDate.now(), startD).getDays() < 0 || Period.between(LocalDate.now(), endD).getDays() < 0)){
            msg.append("vacation return be later then today \n");
        }
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
            vac.add(new Pair(Fields.Connection, isConnection.isSelected()?"1":"0"));
            vac.add(new Pair(Fields.VacId, (VacID)+""));
            vac.add(new Pair<>(Fields.Seller, user));
            vac.add(new Pair<>(Fields.vacationType+"", vacationTypeTXT.getText().trim()));
            vac.add(new Pair<>(Fields.airline+"", AirLineTXT.getText().trim()));
            vac.add(new Pair<>(Fields.destination+"", destinationTXT.getText().trim().toLowerCase()));
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
            vac.add(new Pair<>(Fields.Tradeable+"", isTradeable.isSelected()?"1":"0"));
            if(control.AddEntry(vac, Tables.ListingVacation) == RESULT.Success)
            {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Success");
                a.setHeaderText("Successful!");
                a.setContentText("Vacation added Successfully!\nListing ID is: "+VacID);
                a.show();
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Failed");
                a.setHeaderText("Failures!");
                a.setContentText("Failed listing your vacation, please try again.");
                a.show();
            }
        } else {
            errorBoard.setText(msg.toString());
        }
        VacID++;
    }

    public void setUser(String username)
    {
        user = username;
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
