package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;
import sample.ModelLogic.Messege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessegeBoxView {
    List<Messege> messeges=null;
    private Controller control;
    public String user;
    public Messege msg;
    public TableView table;
    TableColumn<Messege,String> vacationID;
    TableColumn<Messege,String> SellerID;
    TableColumn<Messege, String> BuyerID;
    TableColumn<Messege, String> choicebox;
    TableColumn<Messege, String> buttons;
    int indexMessege;
    int indexMessege2;

    /**
     * initialize the MessageBox variables
     */
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
        vacationID.setPrefWidth(100);
        buttons.setPrefWidth(140);

        vacationID.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
        BuyerID.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        SellerID.setCellValueFactory(new PropertyValueFactory<>("seller"));
        buttons.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
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
                        //System.out.println("Seller: "+m.getBuyer()+", Buyer: "+user+" message: "+indexMessege2);
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                Parent root;
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 650, 400));
                                PaymentsForm paymentsForm = fxmlLoader.getController();
                                paymentsForm.setController(control);
                                stage.show();
                                ArrayList<Pair> tmp = new ArrayList<>();
                                tmp.add(new Pair<>(Fields.VacID, item));
                                paymentsForm.setVacID(Integer.parseInt(item));
                            } catch (IOException x) {
                                x.printStackTrace();
                            }}
                    }
                    );
                        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent e) {
                                        button.setEffect(new DropShadow());
                                        button.setStyle("-fx-background-color: cyan");
                                    }
                                });
                        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent e) {
                                        button.setEffect(null);
                                        button.setStyle("-fx-background-color: deepskyblue");
                                    }
                                });
                    setGraphic(button);
                    }
                else
                    {
                        cb.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                Parent root;
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader();
                                    root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root, 650, 400));
                                    if (cb.getValue().equals("Approve")) {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacID, item));
                                        control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "1", updating);
                                    } else if (cb.getValue().equals("Decline")) {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacID, item));
                                        control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "0", updating);
                                    } else {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacID, item));
                                        control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "2", updating);
                                    }
                                } catch (IOException x) {
                                    x.printStackTrace();
                                }
                            }
                        });
                        cb.setStyle("-fx-background-color: #00b286");
                        setGraphic(cb);
                    }
                }}});
        table.getColumns().addAll(BuyerID, SellerID, buttons, vacationID);
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

    /**
     * adds a message to the tableView, adds a row
     * @param messeges the message we want to add
     */
    public void setMesseges(List<Messege> messeges){
        this.messeges = messeges;
        ObservableList<Messege> list = FXCollections.observableArrayList(this.messeges);
        table.setItems(list);
    }
}
