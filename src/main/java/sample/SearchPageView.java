/**
 * @author: Elad Cohen
 * @helped: Avi Elly
 */
package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;
import sample.ModelLogic.LoggedUser;
import sample.ModelLogic.Messege;
import sample.ModelLogic.VacationListing;

import java.io.IOException;
import java.util.*;

public class SearchPageView {
    public Label warning;
    public Label recTitle;
    public AnchorPane backPane;
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

    private String toSreach;
    private LoggedUser user = null;
    private static double vacid = 0;

    TableColumn<VacationListing, String> logos;
    TableColumn<VacationListing, String> dests;
    TableColumn<VacationListing, String> dates;
    TableColumn<VacationListing, Boolean> connections;
    TableColumn<VacationListing, String> prices;
    TableColumn<VacationListing, String> buttons;

    Thread t = null;
    boolean messagesService = false;
    @FXML
    public void initialize(){
        toSreach = "";
        Tooltip t = new Tooltip();
        t.setText("You can search by Destination or Vacation ID!");
        simpleSearch.setTooltip(t);
        logo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("vacation_logo.png")));
        BackgroundImage myBI= new BackgroundImage(
                new Image(getClass().getClassLoader().getResourceAsStream("background.JPG"),
                        1000,780,
                        false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backPane.setBackground(new Background(myBI));
        simpleSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                toSreach = newValue;
                OnTextChanged();
            }
        });
        iniTable();
    }//end initialize

    /**
     * This method gets the recommended vacations listings and
     * fills the result table with them
     */
    public void setRecommendedListings() {
        ObservableList<VacationListing> list = FXCollections.observableArrayList(getRecommendedVacations());
        table.setItems(list);
    }

    /**
     * this method gets the recommended vacation listings
     * and return a list of listings
     * @return List<VacationListing>
     */
    private List<VacationListing> getRecommendedVacations() {
        ArrayList<HashMap<String, String>> ResList = control.ReadEntries(new ArrayList<Pair>(),
                Tables.ListingVacation);
        return getVacationList(ResList, true);
    }

    /**
     * a general method that converts the request output to a list of vacations.
     * since we did not really implemented a recommendation system, if ToRandom is set to 'true'
     * only random listings will be added to the output list, if ToRandom is set to 'false'
     * all listings will be added to the list
     * @param ResList - output list from a request
     * @param ToRandom - boolean to random or not to random listings
     * @return a List of VacatiobListing
     */
    private List<VacationListing> getVacationList(ArrayList<HashMap<String, String>> ResList, boolean ToRandom) {
        List<VacationListing> l = new LinkedList<>();
        for(HashMap<String, String> paired : ResList)
        {
            if(Double.parseDouble(paired.get("VacID")) > vacid) vacid = Double.parseDouble(paired.get("VacID"));
            if(ToRandom && new Random().nextInt(10) < 5) continue;
            l.add(new VacationListing(new SimpleStringProperty(paired.get("destination").toString().toUpperCase(Locale.US)),
                    new SimpleStringProperty(paired.get("FlightDate")),
                    new SimpleIntegerProperty(Integer.parseInt(paired.get("price"))),
                    new SimpleBooleanProperty(paired.get("Tradeable").equals("1")?true:false),
                    new SimpleStringProperty(paired.get("VacID"))));
        }
        //vacid = Double.parseDouble(ResList.get(ResList.size()-1).get("VacID"));
        return l;
    }

    /**
     * this method is run at the background by a dedicated thread.
     * every 4 seconds it calls CheckMessahes method to refresh messages in the current logged user
     * message box at real time
     */
    private void AutoMessageCheck() {
        while(messagesService) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(user == null) continue;
            CheckMessages();
        }
    }

    /**
     * this method initiate the result table with columns and cells
     */
    private void iniTable() {
        logged.setText("guest user");
        logos = new TableColumn<VacationListing, String>("ID");

        dests = new TableColumn<VacationListing, String>("Destination");

        dates = new TableColumn<VacationListing, String>("Date");

        connections = new TableColumn<VacationListing, Boolean>("Accepts Trades");

        prices = new TableColumn<VacationListing, String>("Price");

        buttons = new TableColumn<VacationListing, String>("View Offer");

        logos.setPrefWidth(100);
        logos.setCellValueFactory(new PropertyValueFactory<>("VacID"));
        dests.setCellValueFactory(new PropertyValueFactory<>("dest"));
        dests.setPrefWidth(100);
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
        dates.setPrefWidth(100);
        connections.setCellValueFactory(new PropertyValueFactory<>("Tradeable"));
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
                                viewvacation.setBuyer(user == null? null: user.getUserName());
//                                viewvacation.setBuyer();
                            } catch (IOException x) {
//                                x.printStackTrace();
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setTitle("Vacation not found");
                                a.setContentText("The selected vacation was not found");
                                a.setHeaderText("Vacation not found");
                                a.show();
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

    /**
     * sets a control to this View class, the control is the logic of our app
     * @param control Controller
     */
    public void setControl(Controller control){this.control=control;}

    /**
     * not implemented yet.
     * this method opens the advanced search option to enable searching by custom
     * parameters as the user chooses.
     * @param actionEvent click action
     */
    public void AdvancedSearchHandler(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Not implemented");
        a.setHeaderText("Not implemented");
        a.setContentText("This proccess is not part of the prototype, thus, it has not been implemented yet.");
        a.show();
    }

    /**
     * this method is called by the onTextChange even handler for the simple search line
     * it updates the table accordingly to the user's input at real time.
     */
    public void OnTextChanged() {
        ArrayList<Pair> searchqry = new ArrayList<>();
        boolean isNum = true;
        try
        {
            Integer.parseInt(toSreach);
        }
        catch(Exception e)
        {
            isNum = false;
        }
        if(!isNum) {
            toSreach = toSreach.toLowerCase();
            searchqry.add(new Pair(Fields.destination, toSreach));
        }
        else { searchqry.add(new Pair(Fields.VacId, toSreach)); }
        ArrayList<HashMap<String, String>> ResList = control.ReadEntries(searchqry, Tables.ListingVacation);
        ObservableList<VacationListing> list = FXCollections.observableArrayList(getVacationList(ResList, false));
        table.setItems(list);
    }

    /**
     * this method creates and opens the Sign Up form window.
     * @param mouseEvent click action
     */
    public void OpenSignupForm(ActionEvent mouseEvent) {
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

    /**
     * this method creates and opens the Add Vacation form window.
     * @param actionEvent click action
     */
    public void openAddVacationForm(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("../AddVacation.fxml").openStream());
            Stage stage = new Stage();
            stage.setTitle("sell vacation");
            stage.setScene(new Scene(root, 650, 400));
            AddVacationView rfv = fxmlLoader.getController();
            rfv.setControl(control);
            rfv.setUser(user.getUserName());
            rfv.setVacID(vacid);
            stage.show();
            //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method connects a user to the system, connected users are able to do various
     * things and many features are opened to them
     * @param event click action
     */
    public void Login(ActionEvent event) {
        errortext=new StringBuilder();
        warning.setText("");
        if(logPassword.getText().trim().isEmpty() || logUsername.getText().trim().isEmpty() ){
            errortext.append("please fill user name and password\n");
         return;}
        ArrayList<Pair> tmp = new ArrayList<>();
        tmp.add(new Pair<>(Fields.userName,logUsername.getText().trim()));
        ArrayList<HashMap<String, String>> userCheck = control.ReadEntries(tmp, Tables.Users);
        if (userCheck == null || userCheck.size() == 0){
            errortext.append("user not register, please register \n");
            warning.setText("*Username or Password does not exist");
            return;}
        if(!userCheck.get(0).get("password").equals(logPassword.getText().trim())){
            errortext.append("wrong password, try again\n");
            warning.setText("*Username or Password does not exist");
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
            messagesService = true;
            t = new Thread( () -> AutoMessageCheck());
            t.start();
            recTitle.setText("Listing Offers");
        }
    }

    /**
     * this method checks if the user have messages, reads them
     * and fill the current logged user's message box with the relevant messages
     */
    private void CheckMessages() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                user.getMessages().clear();
            getMessages(Fields.Seller);
            getMessages(Fields.Buyer);

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
                //System.out.println("checking messages "+"("+user.MessagesCount()+")");
        }
        });
    }

    /**
     * this method reads all messages where the user is either the buyer or the seller
     * according to the 'field' argument, and adds the messages to his mail box.
     * @param field Fields of the field you wish to match your messages to
     */
    private void getMessages(Fields field) {
        ArrayList<Pair> searchMessages = new ArrayList<>();
        searchMessages.add(new Pair(field, user.getUserName()));
        ArrayList<HashMap<String, String>> res = control.ReadEntries(searchMessages, Tables.PurchaseRequest);
        for(HashMap<String, String> resEntry : res)
        {
            if(field.equals(Fields.Seller) && !resEntry.get("approved").equals("2")) continue;
            if(field.equals(Fields.Buyer) && resEntry.get("approved").equals("2")) continue;
            //String msg = resEntry.get("Seller")+","+resEntry.get("Buyer")+","+resEntry.get("VacID")+","+resEntry.get("approved");
            user.addToMailBox(new Messege(new SimpleIntegerProperty(Integer.parseInt(resEntry.get("approved"))),
                    new SimpleStringProperty(resEntry.get("VacId")),
                    new SimpleStringProperty(resEntry.get("Buyer")),
                    new SimpleStringProperty(resEntry.get("Seller")),
                    new SimpleBooleanProperty(resEntry.get("Trade").equals("1")?true:false),
                    new SimpleIntegerProperty(Integer.parseInt(resEntry.get("TradedVacID")))));
        }
    }

    /**
     * this method disconnects the current logged user from the system.
     * stops the auto message service.
     * @param actionEvent click action
     */
    public void DisconnectUser(ActionEvent actionEvent) {
        messagesService = false;
        t = null;
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
        recTitle.setText("RECOMMENDED VACATIONS!");
        setRecommendedListings();
    }

    /**
     * this method handles the message box button, it creates
     * and opens the mail box window
     * @param actionEvent click action
     */
    public void MessagesHandler(ActionEvent actionEvent) {
        CheckMessages();
        if(user.isMailboxEmpty())
        {
            CheckMessages();
        }
        else
        {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                root = fxmlLoader.load(getClass().getResource("../MessegeBox.fxml").openStream());
                Stage stage = new Stage();
                stage.setTitle("MailBox");
                stage.setScene(new Scene(root, 600, 400));
                MessegeBoxView msgbox = fxmlLoader.getController();
                msgbox.setControl(control);
                msgbox.setMesseges(user.getMessages());
                msgbox.setuser(user.getUserName());
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }

    /**
     * this method is called when the exit button is called to close the window.
     * since its the main window it must handle the safe termination of the
     * message service thread
     */
    public void exit() {
        System.out.println("MailBox Service Stopped, please wait for the service to safely terminate");
        messagesService = false;
    }

}
