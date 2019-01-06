package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    ComboBox vacs;
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
    @FXML
    private  Button tradebtn;

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
        tradebtn.setDisable(true);
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
        if(vacationDetails.get("Tradeable").equals(("1")))
            tradebtn.setDisable(false);
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
        if(warnings() == RESULT.Fail) return;

        ArrayList<Pair> fields= new ArrayList<>();
        fields.add(new Pair<>("Seller",seller));
        fields.add(new Pair<>("Buyer",buyer));
        fields.add(new Pair<>("VacId",VacID));
        fields.add(new Pair<>("approved","2"));
        fields.add(new Pair<>("Trade", "0"));
        fields.add(new Pair<>("TradedVacID", "0"));
        addRequest(fields);
        sendRequest.setDisable(true);
    }

    private RESULT warnings()
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(buyer == null){
            a.setHeaderText("Please Login or Sign Up");
            a.setContentText("You must to login in order to send a request");
            a.show();
            return RESULT.Fail;
        }
        if(seller.equals(buyer)){
            a.setHeaderText("Error");
            a.setContentText("You can't buy a vacation from yourself!");
            a.show();
            return RESULT.Fail;
        }
        return RESULT.Success;
    }

    private RESULT addRequest(ArrayList<Pair> fields)
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        RESULT r = RESULT.Fail;
        if(control.AddEntry(fields,Tables.PurchaseRequest) == RESULT.Fail)
        {
            a.setHeaderText("Error!");
            a.setContentText("You already submitted a purchase  request for this vacation\n Please be patient until the seller will send you a response.");
        }
        else
        {
            a.setHeaderText("Succeeded! :)");
            a.setContentText("Your request has been sent");
            r = RESULT.Success;
        }
        a.show();
        return r;
    }

    public void tradeClick(ActionEvent actionEvent) {

        if(warnings() == RESULT.Fail) return;
        //String vac = vacs.getValue().toString();
        if(vacs.getValue() == null)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No vacation selected");
            a.setHeaderText("No Vacation selected to trade");
            a.setContentText("No vacation ID was selected to trade.\n" +
                    "Please select a Vacation you would like to trade with this seller.");
            a.show();
            return;
        }
        String s = vacs.getValue().toString();
        ArrayList<Pair> fields= new ArrayList<>();
        fields.add(new Pair<>("Seller",seller));
        fields.add(new Pair<>("Buyer",buyer));
        fields.add(new Pair<>("VacId",VacID));
        fields.add(new Pair<>("approved","2"));
        fields.add(new Pair<>("Trade", "1"));
        fields.add(new Pair<>("TradedVacID", vacs.getValue()));
        addRequest(fields);

        tradebtn.setDisable(true);
    }

    public void showVacs(MouseEvent mouseEvent) {
        if(vacs.getItems().size() > 0) return;
        ArrayList<Pair> fields = new ArrayList<>();
        fields.add(new Pair<>(Fields.Seller, buyer));
        ArrayList<HashMap<String, String>> res = control.ReadEntries(fields,Tables.ListingVacation);
        for (HashMap<String, String> entery : res)
        {
            String s = entery.get("VacID");
            vacs.getItems().add(s);
        }
    }
}
