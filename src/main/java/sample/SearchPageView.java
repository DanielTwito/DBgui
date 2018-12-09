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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ModelLogic.VacationListing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class SearchPageView {
    private Controller control;

    public ImageView logo;
    public TableView table;
    public TextField simpleSearch;

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
//        connections.setCellFactory(tc -> new TableCell<VacationListing, Boolean>() {
//            @Override
//            protected void updateItem(Boolean item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty ? null :
//                        item.booleanValue() ? "Yes" : "No");
//            }
//        });
//        connections.setCellValueFactory(new PropertyValueFactory<>("isConnection"));
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
                                stage.setScene(new Scene(root, 650, 400));
                                ViewVacation viewvacation = fxmlLoader.getController();
                                viewvacation.setControl(control);
                                stage.show();
                                // Hide this current window (if this is what you want)
                                ((Node) (e.getSource())).getScene().getWindow().hide();
                                viewvacation.setVacID(item);
                            } catch (IOException x) {
                                x.printStackTrace();
                            }
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

    /**
     * this method handles the event of clicking the CREATE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void CreateHandler(MouseEvent mouseEvent)
    {
//        String entery = "";
//        RESULT res=RESULT.Success;
//        for (Node n : stackpanel.getChildren())              // Iterate though the fields to collect the data
//        {
//            if(n instanceof TextField)                      // only collect data from the text fields on the cell
//                entery += ((TextField) n).getText().equals("") ? " ," : ((TextField) n).getText() + ",";
//        }
//        try {// for easier handling, the empty data is converted to " "
//            res = control.addEntry(entery, "USERS");
//            if(res==RESULT.Success)// calling the add method at the Model and gets the result
//                    clearFields();
//            query_output.setText("Create "+res.toString());
//        }catch (SQLException e){
//            query_output.setText("user already exist");// output the result to the output text area
//        }
//        catch (NullPointerException e){
//            query_output.setText("need to fill all fields to create new user");
//        }
    }

    private void clearFields() {
//
//        for (Node n : stackpanel.getChildren())              // Iterate though the fields to collect the data
//        {
//            if(n instanceof TextField)                      // only collect data from the text fields on the cell
//                ((TextField) n).setText("");
//        }
//
//        ID_remove.setText("");
//        value_Update.setText("");
//        ID_search.setText("");
//        ID_update.setText("");
    }

    /**
     * this method handles the event of clicking the REMOVE button
     * it will show a success of failure in the output text area
     * @param mouseEvent
     */
    public void deleteHandler(MouseEvent mouseEvent)
    {
//        RESULT res = control.deleteEntry(ID_remove.getText());
//        if(res==RESULT.Success)
//            clearFields();
//        query_output.setText("Delete "+res.toString()+"!");// output the result to the output text area
    }

    public void AdvancedSearchHandler(ActionEvent actionEvent) {

    }


    public void OnTextChanged(KeyEvent keyEvent) {
        String toSreach = simpleSearch.getText()+keyEvent.getCharacter();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Database/projectdb.db");
        }
        catch(Exception e) {
            System.out.println("could not establish a connection to database");
            return;
        }
        String sql = "SELECT * FROM ListingVacation WHERE destination=\'"+toSreach+"\'";
        PreparedStatement st = null;
        List<VacationListing> l = new LinkedList<>();
        try
        {
            st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                l.add(new VacationListing(new SimpleStringProperty(rs.getString("destination")),
                        new SimpleStringProperty(rs.getString("FlightDate")),
                        new SimpleIntegerProperty(rs.getInt("price")),
                        new SimpleBooleanProperty(rs.getBoolean("Connection")),
                        new SimpleStringProperty(rs.getString("VacID"))));
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        ObservableList<VacationListing> list = FXCollections.observableArrayList(l);
        //table.getColumns().clear();
        //table.getColumns().addAll(logos, dests, dates, connections, prices, buttons);
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
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
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
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
