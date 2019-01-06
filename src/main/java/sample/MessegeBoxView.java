package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.ModelLogic.Messege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *           message types:
 *
 *      2 -  purchase request
 *      0 -  decline (false)
 *      1 -  approve (true)
 *      3 -  trade request
 */
public class MessegeBoxView {
    List<Messege> messeges=null;
    private Controller control;
    public String user;
    public Messege msg;
    public TableView table;
    TableColumn<Messege,String> vacationID;
    TableColumn<Messege,String> SellerID;
    TableColumn<Messege, String> BuyerID;
    TableColumn<Messege, String> mtype;
    TableColumn<Messege, String> buttons;
    TableColumn<Messege, String> vacationToTrade;
    int indexMessege;
    int indexMessege2;

    /**
     * initialize the MessageBox variables
     */
    @FXML
    public void initialize(){
        iniTable();
        indexMessege = 0;
        indexMessege2 = 0;
    }

    /**
     * sets the view class controller
     * @param control
     */
    public void setControl(Controller control) {
        this.control = control;
    }

    /**
     * sets the username for the user currently using the class
     * @param user
     */
    public void setuser(String user){
        this.user=user;
    }

    /**
     * initialize the tableView object with the requierd fields
     */
    private void iniTable(){

        vacationID = new TableColumn<Messege,String>("VacationID");
        SellerID = new TableColumn<Messege,String>("Seller");
        BuyerID = new TableColumn<>("Buyer");
        buttons = new TableColumn<Messege,String>("Action");
        mtype = new TableColumn<Messege, String>("Information");
        vacationToTrade = new TableColumn<>("Offers this");

        vacationID.setPrefWidth(100);
        buttons.setPrefWidth(140);

        vacationID.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
        BuyerID.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        SellerID.setCellValueFactory(new PropertyValueFactory<>("seller"));
        buttons.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
        mtype.setCellValueFactory(new PropertyValueFactory<>("info"));
        vacationToTrade.setCellValueFactory(new PropertyValueFactory<>("vacationToTrade"));
        buttons.setCellFactory(col -> new TableCell<Messege, String>(){
            Button button = new Button("Buy");
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "Approve",//1
                            "Decline");//0
            ChoiceBox<String> cb = new ChoiceBox<String>(options);
            {
                button.setMaxHeight(17);
                button.setMaxWidth(130);
                cb.setMaxHeight(17);
                cb.setMaxWidth(130);
                cb.setTooltip(new Tooltip("Action, Approve or Decline"));
                button.setStyle("-fx-background-color: deepskyblue");
                button.setEffect(new DropShadow());
            }
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if(empty || item == null) {
                    setGraphic(null);}
                else {
                    Messege m = getMsgByID(item);
                    if(m.getBuyer().equals(user))
                    {
                        if(m.getMessege() == 0)
                        {
                            setGraphic(new Label("Request Declined"));
                            return;
                        }
                        //System.out.println("Seller: "+m.getBuyer()+", Buyer: "+user+" message: "+indexMessege2);
//                        button.setOnAction(new EventHandler<ActionEvent>() {
//                            @Override
//                            public void handle(ActionEvent e) {
//                                Parent root;
//                            try {
//                                FXMLLoader fxmlLoader = new FXMLLoader();
//                                root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
//                                Stage stage = new Stage();
//                                stage.setScene(new Scene(root, 650, 400));
//                                PaymentsForm paymentsForm = fxmlLoader.getController();
//                                paymentsForm.setController(control);
//                                stage.show();
//                                ArrayList<Pair> tmp = new ArrayList<>();
//                                tmp.add(new Pair<>(Fields.VacId, item));
//                                paymentsForm.setVacID(Integer.parseInt(item));
//                                button.setDisable(true);
//                            } catch (IOException x) {
//                                x.printStackTrace();
//                            }}
//                    }
//                    );
//                        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                                new EventHandler<MouseEvent>() {
//                                    @Override
//                                    public void handle(MouseEvent e) {
//                                        button.setEffect(new DropShadow());
//                                        button.setStyle("-fx-background-color: cyan");
//                                    }
//                                });
//                        button.addEventHandler(MouseEvent.MOUSE_EXITED,
//                                new EventHandler<MouseEvent>() {
//                                    @Override
//                                    public void handle(MouseEvent e) {
//                                        button.setEffect(null);
//                                        button.setStyle("-fx-background-color: deepskyblue");
//                                    }
//                                });
//                    setGraphic(button);
                        Label l = new Label("Enjoy your flight!");
                        setGraphic(l);
                    }
                else
                    {
                        cb.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
//                                Parent root;
                                RESULT r = RESULT.Fail;
                                try {
//                                    FXMLLoader fxmlLoader = new FXMLLoader();
//                                    root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
//                                    Stage stage = new Stage();
//                                    stage.setScene(new Scene(root, 650, 400));
                                    if (cb.getValue().equals("Approve")) {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacId, item));
                                        //r = control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "1", updating);
                                        r = removeVacations(updating);
                                    } else if (cb.getValue().equals("Decline")) {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacId, item));
                                        r = control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "0", updating);
                                    } else {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacId, item));
                                        r = control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "2", updating);
                                    }
                                } catch (Exception x) {
                                    x.printStackTrace();
                                }
                                Alert a;
                                if(r == RESULT.Fail)
                                {
                                    a = new Alert(Alert.AlertType.ERROR);
                                    a.setTitle("Error while approval");
                                    a.setHeaderText("Approval status not set");
                                    a.setContentText("Could not update your approval choice\nPlease try again.");
                                }
                                else
                                {
                                    a = new Alert(Alert.AlertType.CONFIRMATION);
                                    a.setTitle("Approval recorded");
                                    a.setHeaderText("Updated status success.");
                                    if(cb.getValue().equals("Approve")) {
                                        a.setContentText("Thank you for your reply.\n" +
                                                "A message was sent to the buyer about your choice." +
                                                "\nPlease wait for his payment to complete the transaction");
                                    }
                                    else
                                    {
                                        a.setContentText("Thank you for your reply.\n" +
                                                "A message was sent to the buyer about your choice.");
                                    }
                                }
                                a.show();
                                cb.setDisable(true);
                            }
                        });
                        cb.setStyle("-fx-background-color: #00b286");
                        setGraphic(cb);
                    }
                }}});
        table.getColumns().addAll(BuyerID, SellerID, buttons, vacationID, mtype, vacationToTrade);
    }

    /**
     * finds and retrives the a messages based on the vacationID
     * @param id- the ID number of the required Message
     * @return the required Message
     */
    private Messege getMsgByID(String id)
    {
        for(Messege m : messeges)
        {
            if(m.getVacationID().equals(id)) return m;
        }
        return null;
    }

    private RESULT removeVacations(ArrayList<Pair> updating)
    {
        RESULT r = control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "1", updating);
        if(r == RESULT.Fail)
            return r;
        try {
            ArrayList<Pair> read = new ArrayList<>();
            read.add(new Pair(Fields.VacId, updating.get(0).getValue()));
            read.add(new Pair(Fields.approved, "1"));
            ArrayList<HashMap<String, String>> res = control.ReadEntries(read, Tables.PurchaseRequest);
            for (HashMap<String, String> entery : res) {
                read.clear();
                read.add(new Pair(Fields.VacId, entery.get("VacId")));
                control.DeleteEntry(Tables.ListingVacation, read);
                read.clear();
                read.add(new Pair(Fields.VacId, entery.get("TradedVacID")));
                control.DeleteEntry(Tables.ListingVacation, read);
            }
        }
        catch(Exception e) {return RESULT.Fail;}
        return RESULT.Success;
    }

    /**
     * adds a message to the tableView, adds a row
     * @param messeges the message we want to add
     */
    public void setMesseges(List<Messege> messeges){
        this.messeges = messeges;
        ObservableList<Messege> list = FXCollections.observableArrayList(this.messeges);
        table.setItems(list);
    }


    public void newMessageHandler(ActionEvent actionEvent) {

    }
}
