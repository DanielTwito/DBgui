package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.util.ArrayList;
import java.util.HashMap;

//TODO: Nethanel, implement this please
public class ViewVacation {

    private Controller control;
    private String VacID;
    private String seller;

    private String buyer;

    @FXML
    AnchorPane backPane;
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
    @FXML
    private Text sellerName;
    @FXML
    private Button sendRequest;

    private ArrayList<Text> txtList = new ArrayList<>();

    public void initialize()
    {
        BackgroundImage myBI= new BackgroundImage(
                new Image(getClass().getClassLoader().getResourceAsStream("View.JPG"),
                        600,500,
                        false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backPane.setBackground(new Background(myBI));
    }

    public void setControl(Controller control)
    {
        this.control = control;
    }

    public void setVacID(String vacID) {
        this.VacID = vacID;
        setTexts();
    }


    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    private void setTexts() {

        ArrayList<Pair> whereCondition = new ArrayList<>();
        whereCondition.add(new Pair<>(Fields.VacId, VacID));
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
        withConnection.setText(vacationDetails.get("Connection"));
        seller =vacationDetails.get("Seller");
        sellerName.setText(seller);
        sendRequest.setStyle("-fx-background-color: deepskyblue");
        sendRequest.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        sendRequest.setEffect(new DropShadow());
                        sendRequest.setStyle("-fx-background-color: cyan");
                    }
                });
        sendRequest.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        sendRequest.setEffect(null);
                        sendRequest.setStyle("-fx-background-color: deepskyblue");
                    }
                });
    }


    //supposed to send a message to the seller
    @FXML
    protected void PurchaseRequest(ActionEvent event) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(buyer == null){
            a.setHeaderText("Please Login or Sign Up");
            a.setContentText("You must to login in order to send a request");
            a.show();
            return;
        }
        if(seller.equals(buyer)){
            a.setHeaderText("Error");
            a.setContentText("You can't buy a vacation from yourself!");
            a.show();
            return;
        }

        ArrayList<Pair> fields= new ArrayList<>();
        fields.add(new Pair<>("Seller",seller));
        fields.add(new Pair<>("Buyer",buyer));
        fields.add(new Pair<>("VacId",VacID));
        fields.add(new Pair<>("approved","2"));
//        if(control.ReadEntries(fields,Tables.PurchaseRequest).size() != 0) {
//            a.setHeaderText("Error!");
//            a.setContentText("You already submitted a purchase  request for this vacation\n Please be patient until the seller will send you a response.");
//        }else{
//            a.setHeaderText("Succeeded! :)");
//            control.AddEntry(fields,Tables.PurchaseRequest);
//            a.setContentText("Your request has been sent");
//        }

        if(control.AddEntry(fields,Tables.PurchaseRequest) == RESULT.Fail)
        {
            a.setHeaderText("Error!");
            a.setContentText("You already submitted a purchase  request for this vacation\n Please be patient until the seller will send you a response.");
        }
        else
        {
            a.setHeaderText("Succeeded! :)");
            a.setContentText("Your request has been sent");
        }
        a.show();
        sendRequest.setDisable(true);
    }
}
