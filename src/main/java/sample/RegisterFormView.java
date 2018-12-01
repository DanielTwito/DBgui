package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Controller;
import sample.Enums.Fields;
import sample.Enums.Tables;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterFormView extends Application {
    private Controller control;

    //Controls in the javaFX
    @FXML
    private TextField userNameTXT;
    @FXML
    private PasswordField passwordTXT;
    @FXML
    private PasswordField confirm_passwordTXT;
    @FXML
    private TextField firstNameTXT;
    @FXML
    private TextField lastNameTXT;
    @FXML
    private TextField emailTXT;
    @FXML
    private TextField cityTXT;
    @FXML
    private DatePicker birthdate;
    @FXML
    private Button submit;
    @FXML
    private Button uploadImage;
    @FXML
    private TextField errorTxt;
    public String date=null;

    public LocalDate ld;
    ArrayList<TextField> txtList = new ArrayList<>();
    //data for the user
    public String imageURL = null;


    //final FileChooser fileChooser = new FileChooser();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("RegisterForm.fxml"));
        stage.setTitle("Registration Form FXML Application");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
        StringBuilder errortext = new StringBuilder();
        txtList.add(userNameTXT);
        txtList.add(passwordTXT);
        txtList.add(confirm_passwordTXT);
        txtList.add(firstNameTXT);
        txtList.add(lastNameTXT);
        txtList.add(emailTXT);
        txtList.add(cityTXT);
        final FileChooser fileChooser = new FileChooser();
        birthdate.setOnAction((ActionEvent event)->{
            ld=birthdate.getValue();});
        uploadImage.setOnAction((final ActionEvent e) -> {
            File file = fileChooser.showOpenDialog(stage);
            uploadImage = new Button("Choose account Image");
            if (file != null) {imageURL = file.toURI().toString();}});

    }

    public void setControl(Controller control) {
        this.control = control;
    }
    @FXML
    protected void SignUp(ActionEvent event) {
        errorTxt.clear();
        StringBuilder errortext = new StringBuilder();
        String missing = new String();
        for (TextField tx : txtList) {
            if (tx.getText().trim().isEmpty())
                missing = missing + tx + ", ";}
        if(ld==null)
            errortext.append("please fill your date of birth \n");
        if (!missing.isEmpty())
            errortext.append("Please fill all the required fieids in the form. missing: " + missing + ".\n");
        if(ld!=null && Period.between(LocalDate.now(),ld).getYears()<18)
            errortext.append("all users must be over 18 years old. \n");
        String[][] userResult = control.ReadEntry(new String[]{userNameTXT.getText().trim()}, new Fields[]{Fields.Username}, Tables.Users);
        if (userResult == null)
            errortext.append("user name already in the system please choose a different one.\n");
        String[][] EmailResult = control.ReadEntry(new String[]{emailTXT.getText().trim()}, new Fields[]{Fields.Email}, Tables.Users);
        if (EmailResult == null)
            errortext.append("email address already in the system please choose a different one.\n");
        if (!passwordTXT.getText().trim().equals(confirm_passwordTXT.getText().trim())) {
            errortext.append("Password and confirm password are not the same\n");}
            //this part check the email according to a regex
        String regexMail="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        pattern=Pattern.compile(regexMail, Pattern.CASE_INSENSITIVE);
        if(!pattern.matcher(emailTXT.getText().trim()).matches())
            errortext.append("please enter a real Email address\n");

            //if the length of the error messege is zero then it addes the user, else it prints the error messege on the screen
        if (errortext.toString().length() == 0)
            control.AddEntry(new String[]{userNameTXT.getText().trim(), passwordTXT.getText().trim(), firstNameTXT.getText().trim(),
                    lastNameTXT.getText().trim(), emailTXT.getText().trim(), cityTXT.getText().trim(),imageURL}, Tables.Users);
         else {
            errorTxt.setText(errortext.toString());
            return;

         }
    }
}


