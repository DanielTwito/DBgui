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
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;
import sample.ModelLogic.Messege;
import sample.ModelLogic.VacationListing;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessegeBoxView {
    List<Messege> messeges=null;
    private Controller control;
    public TableView table;
    TableColumn<Messege,String> vacationID;
    TableColumn<Messege,String> messege;
    TableColumn<Messege, String> choicebox;
    TableColumn<Messege, String> buttons;


    public void initialize(){
        iniTable();
    }
    public void setControl(Controller control) {
        this.control = control;}
    private void iniTable(){

        vacationID=new TableColumn<Messege,String>("IDS");
        choicebox=new TableColumn<Messege,String>("approve/decline");
        buttons=new TableColumn<Messege,String>("purchase Now");
        vacationID.setPrefWidth(100);
        messege.setPrefWidth(100);
        choicebox.setPrefWidth(140);
        buttons.setPrefWidth(140);
        vacationID.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        messege.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        buttons.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        choicebox.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        buttons.setCellFactory(col -> new TableCell<Messege, String>(){
            Button button = new Button("Buy");
            {
                button.setMaxHeight(17);
                button.setMaxWidth(130);
                button.setStyle("-fx-background-color: firebrick");
                setGraphic(button);
        }
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if(empty || item == null) {
                    setGraphic(null);
                }
                else {
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Parent root;
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
                                Stage stage = new Stage();
                                //stage.setTitle("we accept all major cards");
                                stage.setScene(new Scene(root, 650, 400));
                                PaymentsForm paymentsForm = fxmlLoader.getController();
                                paymentsForm.setController(control);
                                stage.show();
                                ArrayList<Pair> tmp = new ArrayList<>();
                                tmp.add(new Pair<>(Fields.VacID, item));
                                paymentsForm.setVacPrice(Double.parseDouble(item));
                            } catch (IOException x) {
                                x.printStackTrace();
                            }}});setGraphic(button);}}});
        choicebox.setCellFactory(col -> new TableCell<Messege, String>(){
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                                "Approve",//1
                            "Decline");//0
            ChoiceBox<String> choiceBox= new ChoiceBox<String>(options);
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if(empty || item == null) {
                    setGraphic(null);
                }
                else {
                    choiceBox.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Parent root;
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                root = fxmlLoader.load(getClass().getResource("../PaymentsForm.fxml").openStream());
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 650, 400));
                                if(choiceBox.getValue().equals("Approve")) {
                                    ArrayList<Pair> updating = new ArrayList<>();
                                    updating.add(new Pair<>(Fields.VacID, item));
                                    control.UpdateEntries(Tables.PurchaseRequest,Fields.approved,"1",updating);
                                }
                                else if(choiceBox.getValue().equals("Decline")){
                                    ArrayList<Pair> updating = new ArrayList<>();
                                    updating.add(new Pair<>(Fields.VacID, item));
                                    control.UpdateEntries(Tables.PurchaseRequest,Fields.approved,"0",updating);
                                }
                                else{
                                    ArrayList<Pair> updating = new ArrayList<>();
                                    updating.add(new Pair<>(Fields.VacID, item));
                                    control.UpdateEntries(Tables.PurchaseRequest,Fields.approved,"2",updating);
                                }
                                PaymentsForm paymentsForm = fxmlLoader.getController();
                                paymentsForm.setController(control);
                                stage.show();
                                ArrayList<Pair> tmp = new ArrayList<>();
                                tmp.add(new Pair<>(Fields.VacID, item));
                                paymentsForm.setVacPrice(Double.parseDouble(item));
                            } catch (IOException x) {
                                x.printStackTrace();
                            }}});setGraphic(choiceBox);}}});

        ObservableList<Messege> list = FXCollections.observableArrayList(messeges);
        table.setItems(list);
        }

    public void getMesseges(List<Messege> messeges){
        this.messeges=messeges;
    }
}
