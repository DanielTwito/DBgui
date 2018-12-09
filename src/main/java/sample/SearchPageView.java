package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;
import sample.ModelLogic.LoggedUser;
import sample.ModelLogic.VacationListing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SearchPageView {
    public TextField logUsername;
    public PasswordField logPassword;
    public Button AddVacation;
    public Hyperlink signup;
    public Hyperlink login;
    public ButtonBar buttonbar;
    private Controller control;
    StringBuilder errortext;
    public ImageView logo;
    public TableView table;
    public TextField simpleSearch;
    public Label logged;
    public Hyperlink messages;
    public Hyperlink disconnect;

    private LoggedUser user;

    TableColumn<VacationListing, String> logos;
    TableColumn<VacationListing, String> dests;
    TableColumn<VacationListing, String> dates;
    TableColumn<VacationListing, Boolean> connections;
    TableColumn<VacationListing, String> prices;
    TableColumn<VacationListing, String> buttons;

    int r;
    @FXML
    public void initialize(){
        logo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("vacation_logo.JPG")));
        r = 1;
        iniTable();
    }//end initialize

    private void iniTable()
    {
        logged.setText("guest user");
        logos = new TableColumn<VacationListing, String>("ID");

        dests = new TableColumn<VacationListing, String>("Destination");

        dates = new TableColumn<VacationListing, String>("Date");

        connections = new TableColumn<VacationListing, Boolean>("Connection");

        prices = new TableColumn<VacationListing, String>("Price");

        buttons = new TableColumn<VacationListing, String>("View Offer");

        logos.setPrefWidth(100);
        logos.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        dests.setCellValueFactory(new PropertyValueFactory<>("dest"));
        dests.setPrefWidth(100);
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
        dates.setPrefWidth(100);
        connections.setCellValueFactory(new PropertyValueFactory<>("isConnection"));
        connections.setCellFactory(col -> new TableCell<VacationListing, Boolean>() {
            ImageView im = new ImageView();
            {
                // initialize ImageView + set as graphic
                im.setFitWidth(20);
                im.setFitHeight(20);
                setGraphic(im);
                //im.setImage(new Image(getClass().getClassLoader().getResourceAsStream("yes.JPG")));
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (empty || item == null) {
                    // no image for empty cells
                    im.setImage(null);
                } else {
                    // set image for non-empty cell
                    im.setImage(item ? new Image(getClass().getClassLoader().getResourceAsStream("yes.JPG")) :
                            new Image(getClass().getClassLoader().getResourceAsStream("no.JPG")));
                }
            }
        });
        connections.setPrefWidth(100);
        prices.setCellValueFactory(new PropertyValueFactory<>("price"));
        prices.setPrefWidth(100);
        buttons.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        buttons.setCellFactory(col -> new TableCell<VacationListing, String>(){
            Button button = new Button("View");
            {
                button.setMaxHeight(17);
                button.setMaxWidth(130);
                button.setStyle("-fx-background-color: deepskyblue");
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
                                root = fxmlLoader.load(getClass().getResource("../ViewVacation.fxml").openStream());
                                Stage stage = new Stage();
                                stage.setTitle("View Vacation Offer");
                                stage.setScene(new Scene(root, 600, 500));
                                ViewVacation viewvacation = fxmlLoader.getController();
                                viewvacation.setControl(control);
                                stage.show();
                                // Hide this current window (if this is what you want)
                                //((Node) (e.getSource())).getScene().getWindow().hide();
                                viewvacation.setVacID(item);
//                                viewvacation.setBuyer();
                            } catch (IOException x) {
                                x.printStackTrace();
                            }
                        }
                    });
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
            }
        });
        buttons.setPrefWidth(140);

        prices.setSortType(TableColumn.SortType.DESCENDING);
        dates.setSortType(TableColumn.SortType.DESCENDING);

        table.setMaxWidth(650);
        table.setPrefWidth(650);
        table.getColumns().addAll(logos, dests, dates, connections, prices, buttons);
    }
    public void setControl(Controller control){this.control=control;}

    public void AdvancedSearchHandler(ActionEvent actionEvent) {

    }


    public void OnTextChanged(KeyEvent keyEvent) {
        String toSreach = simpleSearch.getText()+keyEvent.getCharacter();
        ArrayList<Pair> searchqry = new ArrayList<>();
        searchqry.add(new Pair(Fields.destination, toSreach));
        ArrayList<HashMap<String, String>> ResList = control.ReadEntries(searchqry, Tables.ListingVacation);
        List<VacationListing> l = new LinkedList<>();
        for(HashMap<String, String> paired : ResList)
        {
            l.add(new VacationListing(new SimpleStringProperty(paired.get("destination")),
                    new SimpleStringProperty(paired.get("FlightDate")),
                    new SimpleIntegerProperty(Integer.parseInt(paired.get("price"))),
                    new SimpleBooleanProperty(paired.get("Connection").equals("1")?true:false),
                    new SimpleStringProperty(paired.get("VacID"))));
        }
        ObservableList<VacationListing> list = FXCollections.observableArrayList(l);
        table.setItems(list);
    }

    public void OpenSignupForm(ActionEvent mouseEvent)
    {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("../RegisterForm.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, 650, 400));
            RegisterFormView rfv = fxmlLoader.getController();
            rfv.setControl(control);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddVacationForm(ActionEvent actionEvent)
    {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("../AddVacation.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("sell vacation");
            stage.setScene(new Scene(root, 650, 400));
            AddVacationView rfv = fxmlLoader.getController();
            rfv.setControl(control);
            stage.show();
            //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Login(ActionEvent event) {
        errortext=new StringBuilder();
        if(logPassword.getText().trim().isEmpty() || logUsername.getText().trim().isEmpty() ){
            errortext.append("please fill user name and password\n");
         return;}
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair<>(Fields.userName,logUsername.getText().trim()));
        ArrayList<HashMap<String, String>> userCheck = control.ReadEntries(tmp, Tables.Users);
        if (userCheck == null || userCheck.size() == 0){
            errortext.append("user not register, please register \n");
            return;}
        if(!userCheck.get(0).get("password").equals(logPassword.getText().trim())){
            errortext.append("wrong password, try again\n");
            return;}
        else{AddVacation.setDisable(false);
            signup.setDisable(true);
            login.setDisable(true);
            login.setVisible(false);
            signup.setVisible(false);
            logUsername.setVisible(false);
            logPassword.setVisible(false);
            user = new LoggedUser(userCheck.get(0).get("firstName"), userCheck.get(0).get("userName"));
            disconnect.setVisible(true);
            logged.setText("Welcome "+user.getName());
            messages.setText("(No Messages)");
            messages.setVisible(true);
            CheckMessages();
        }
    }

    private void CheckMessages()
    {
        ArrayList<HashMap<String, String>> messagesList;
        ArrayList<Pair> searchMessages = new ArrayList<>();
        searchMessages.add(new Pair(Fields.Seller, user.getUserName()));
        ArrayList<HashMap<String, String>> res = control.ReadEntries(searchMessages, Tables.PurchaseRequest);
        writeMessages(res);
        searchMessages = new ArrayList<>();
        searchMessages.add(new Pair(Fields.Buyer, user.getUserName()));
        res = control.ReadEntries(searchMessages, Tables.PurchaseRequest);
        writeMessages(res);

        if(user.isMailboxEmpty())
        {
            messages.setText("(No Messages)");
            messages.setStyle("-fx-text-fill: #004eff");
        }
        else
        {
            messages.setText("("+user.MessagesCount()+") New Messages");
            messages.setStyle("-fx-text-fill: crimson");
        }
    }

    private void writeMessages(ArrayList<HashMap<String, String>> res)
    {
        for(HashMap<String, String> resEntry : res)
        {
            String msg = resEntry.get("Seller")+","+resEntry.get("Buyer")+","+resEntry.get("VacID")+","+resEntry.get("approved");
            user.addToMailBox(msg);
        }
    }

    public void DisconnectUser(ActionEvent actionEvent) {
        user = null;
        AddVacation.setDisable(true);
        signup.setDisable(false);
        login.setDisable(false);
        login.setVisible(true);
        signup.setVisible(true);
        logUsername.setVisible(true);
        logPassword.setVisible(true);
        disconnect.setVisible(false);
        messages.setVisible(false);
        messages.setVisible(false);
        logged.setText("guest user");
        logPassword.clear();
        logUsername.clear();
    }

    public void MessagesHandler(ActionEvent actionEvent)
    {
        if(user.isMailboxEmpty())
        {
            CheckMessages();
        }
        else
        {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                root = fxmlLoader.load(getClass().getResource("../ViewVacation.fxml").openStream());
                Stage stage = new Stage();
                stage.setTitle("MailBox");
                stage.setScene(new Scene(root, 600, 500));
                ViewVacation viewvacation = fxmlLoader.getController();
                viewvacation.setControl(control);
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }

    public void OnEnter(MouseDragEvent mouseDragEvent) {
    }
}
