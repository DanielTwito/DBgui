package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddVacationView {
    //Controls in the javaFX
    @FXML
    TextField vacationTypeTXT;
    @FXML
    TextField AirLineTXT;
    @FXML
    TextField destinationTXT;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    Spinner adultAmount;
    @FXML
    ComboBox LuggageType;
    @FXML
    Spinner ChildAmount;
    @FXML
    Spinner Babyamount;
    @FXML
    Slider roomRank;
    @FXML
    CheckBox withReturn;
    @FXML
    CheckBox withStuff;
    @FXML
    TextArea errorBoard;
    @FXML
    Button submit;

    private Controller control;


    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddVacation.fxml"));
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        LuggageType.getItems().addAll("no luggage","hand luggage","Large suitcase","Large suitcase and hand luggage");
        // scene.getStylesheets().add(getClass().getResource("RegisterForm.css").toExternalForm());
        stage.setTitle("Add vacation");
        stage.show();
    }
    public void setControl(Controller control) {
        this.control = control;
    }
public void addVcation(ActionEvent event){
        StringBuilder msg = new StringBuilder();
        if(vacationTypeTXT.getText().trim().isEmpty()){msg.append("please add vacation type\n");}
        if(destinationTXT.getText().trim().isEmpty()){msg.append("please add destination\n");}
        if(AirLineTXT.getText().trim().isEmpty()){msg.append("please add airline\n");}

}
}
