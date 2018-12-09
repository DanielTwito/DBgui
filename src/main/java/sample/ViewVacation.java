package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;

import java.util.ArrayList;
import java.util.HashMap;

//TODO: Nethanel, implement this please
public class ViewVacation {

    private Controller control;
    private String VacID;
    private String seller;

    @FXML
    private Text airline;
    @FXML
    private Text flightDate;
    @FXML
    private Text returnDate;
    @FXML
    private Text baggage;
    @FXML
    private Text adultTickets;
    @FXML
    private Text childTickets;
    @FXML
    private Text babyTickets;
    @FXML
    private Text destination;
    @FXML
    private Text includeReturn;
    @FXML
    private Text vacationType;
    @FXML
    private Text includeRoom;
    @FXML
    private Text placeRank;
    @FXML
    private Text withConnection;
    @FXML
    private Text price;

    private ArrayList<Text> txtList = new ArrayList<>();
    public void setControl(Controller control)
    {
        this.control = control;
    }

    public void setVacID(String vacID) {
        this.VacID = vacID;
        setTexts();
    }



    private void setTexts() {

        ArrayList<Pair> whereCondition = new ArrayList<>();
        whereCondition.add(new Pair<>(Fields.VacID, VacID));
        HashMap<String,String> vacationDetails= control.ReadEntries(whereCondition, Tables.ListingVacation).get(0);

        airline.setText(vacationDetails.get("airline"));
        flightDate.setText(vacationDetails.get("FlightDate"));
        returnDate.setText(vacationDetails.get("ReturnDate"));
        baggage.setText(vacationDetails.get("Baggage"));
        adultTickets.setText(vacationDetails.get("adultTickets"));
        childTickets.setText(vacationDetails.get("childTickets"));
        babyTickets.setText(vacationDetails.get("babyTickets"));
        destination.setText(vacationDetails.get("destination"));
        includeReturn.setText(vacationDetails.get("includeReturn"));
        vacationType.setText(vacationDetails.get("vacationType"));
        includeRoom.setText(vacationDetails.get("includeRoom"));
        placeRank.setText(vacationDetails.get("placeRank"));
        price.setText(vacationDetails.get("price"));
        withConnection.setText(vacationDetails.get("withConnection"));
        seller =vacationDetails.get("seller");

    }


    //supposed to send a message to the seller
    public void PurchaseRequest(ActionEvent event) {

//        control.sendPurchaseRequest(seller,buyer);

    }
}
