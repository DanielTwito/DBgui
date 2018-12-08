package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ModelLogic.VacationListing;

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

    int r;
    @FXML
    public void initialize(){
        logo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("vacation_logo.JPG")));
        r = 1;
        TableColumn<VacationListing, String> logos
                = new TableColumn<VacationListing, String>("");

        TableColumn<VacationListing, String> dests
                = new TableColumn<VacationListing, String>("Destination");

        TableColumn<VacationListing, String> dates
                = new TableColumn<VacationListing, String>("Date");

        TableColumn<VacationListing, Boolean> connections
                = new TableColumn<VacationListing, Boolean>("Connection");

        TableColumn<VacationListing, String> prices
                = new TableColumn<VacationListing, String>("Price");

        TableColumn<VacationListing, String> buttons
                = new TableColumn<VacationListing, String>("");

        logos.setPrefWidth(114);
        dests.setCellValueFactory(new PropertyValueFactory<>("dest"));
        dests.setPrefWidth(114);
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
        dates.setPrefWidth(114);
        connections.setCellValueFactory(new PropertyValueFactory<>("isConnection"));
        connections.setPrefWidth(114);
        prices.setCellValueFactory(new PropertyValueFactory<>("price"));
        prices.setPrefWidth(114);
        buttons.setCellValueFactory(new PropertyValueFactory<>("view"));
        buttons.setPrefWidth(114);

        prices.setSortType(TableColumn.SortType.DESCENDING);
        dates.setSortType(TableColumn.SortType.DESCENDING);

        table.getColumns().addAll(logos, dests, dates, connections, prices, buttons);

    }//end initialize
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
//        ArrayList<Pair> read = new ArrayList<>();
//        read.add(new Pair(Fields.destination, simpleSearch.getText()));
//        ArrayList<HashMap<String, String>> res = control.ReadEntries(read, Tables.ListingVacations);
//        ObservableList<VacationListing> list;
//        List<VacationListing> l = new LinkedList<>();
//        table.getItems().clear();
//
//        for(int i = 0; i < r; i++)
//            l.add(new VacationListing(i+"",i+"",i+"",true));
//        list = FXCollections.observableArrayList(l);
//        r++;
//        table.setItems(list);

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Database/projectdb.db");
        }
        catch(Exception e) {
            System.out.println("could not establish a connection to database");
            return;
        }
        String sql = "SELECT * FROM ListingVacation";
        PreparedStatement st = null;
        List<VacationListing> l = new LinkedList<>();
        try
        {
            st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                l.add(new VacationListing(rs.getString("destination"),
                        rs.getString("FlightDate"),
                        rs.getInt("price"),
                        rs.getBoolean("Connection")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        ObservableList<VacationListing> list = FXCollections.observableArrayList(l);
        table.setItems(list);
    }

    public void OpenSignupForm(MouseEvent mouseEvent)
    {
//        Parent root;
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            root = fxmlLoader.load(getClass().getResource("../RegisterForm.fxml").openStream());
//            Stage stage = new Stage();
//            stage.setTitle("Sign Up");
//            stage.setScene(new Scene(root, 400, 600));
//            RegisterFormView rfv = fxmlLoader.getController();
//
//            stage.show();
//            // Hide this current window (if this is what you want)
//            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        Stage s = new Stage();
        RegisterFormView rfv = new RegisterFormView();
        try {
            rfv.start(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAddVacationForm(ActionEvent actionEvent)
    {

    }
}
