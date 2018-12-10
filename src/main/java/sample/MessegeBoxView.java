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
   // TableColumn<Messege,String> seller;
    TableColumn<Messege,String> buyer;
    TableColumn<Messege, String> choicebox;
    TableColumn<Messege, String> buttons;

    public void initialize(){
        iniTable();
    }
    public void setControl(Controller control) {
        this.control = control;
    }
    public void setuser(String user){
        this.user=user;
    }

    private void iniTable(){

        vacationID=new TableColumn<Messege,String>("VacationID");
      //  seller=new TableColumn<Messege,String>("seller");
        buyer=new TableColumn<Messege,String>("buyer");
        choicebox=new TableColumn<Messege,String>("approve/decline");
        buttons=new TableColumn<Messege,String>("purchase Now");
        vacationID.setPrefWidth(100);
        buyer.setPrefWidth(100);
      //  seller.setPrefWidth(100);
        choicebox.setPrefWidth(140);
        buttons.setPrefWidth(140);

        vacationID.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
        buyer.setCellValueFactory(new PropertyValueFactory<>("buyer"));
      //  seller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        buttons.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
        choicebox.setCellValueFactory(new PropertyValueFactory<>("VacationID"));
      //  if(user.equals(msg.getBuyer())){
        buttons.setCellFactory(col -> new TableCell<Messege, String>(){
            Button button = new Button("Buy");
            {
                button.setMaxHeight(17);
                button.setMaxWidth(130);
               // button.setStyle("-fx-background-color: firebrick");
                setGraphic(button);
            }
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if(empty || item == null) {
                    setGraphic(null);}
                else {
                    for (Messege msg : messeges) {
                        if (msg.getBuyer().equals(user)) {
                            choicebox.setEditable(false);choicebox.setVisible(false);
                        } else {
                            button.setDisable(true);button.setVisible(false);
                        }
                    }
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
                                tmp.add(new Pair<>(Fields.VacId, item));
                                paymentsForm.setVacID(Integer.parseInt(item));
                            } catch (IOException x) {
                                x.printStackTrace();
                            }}});setGraphic(button);}}});
            choicebox.setCellFactory(col -> new TableCell<Messege, String>() {
                ObservableList<String> options =
                        FXCollections.observableArrayList(
                                "Approve",//1
                                "Decline");//0
                ChoiceBox<String> cb = new ChoiceBox<String>(options);

                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
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
                                        updating.add(new Pair<>(Fields.VacId, item));
                                        control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "1", updating);
                                    } else if (cb.getValue().equals("Decline")) {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacId, item));
                                        control.UpdateEntries(Tables.PurchaseRequest, Fields.approved, "0", updating);
                                    } else {
                                        ArrayList<Pair> updating = new ArrayList<>();
                                        updating.add(new Pair<>(Fields.VacId, item));
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
                }
            });
        buttons.setEditable(false);buttons.setEditable(false);

        table.getColumns().addAll(vacationID, buttons, choicebox,buyer);
    }

    public void setMesseges(List<Messege> messeges){
        this.messeges = messeges;
        ObservableList<Messege> list = FXCollections.observableArrayList(this.messeges);
        table.setItems(list);
    }
}
