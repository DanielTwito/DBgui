package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller;
import sample.Enums.Fields;
import sample.Enums.Tables;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class RegisterFormView {
    private Controller control;

    //Controls in the javaFX
    public TextField userNameTXT;
    public TextField passwordTXT;
    public TextField confirm_passwordTXT;
    public TextField firstNameTXT;
    public TextField lastNameTXT;
    public TextField emailTXT;
    public TextField cityTXT;
    public DatePicker birthdate;
    public Button uploadImage;
    public TextField errorTxt;
    public String date=null;
    public LocalDate ld;
    ArrayList<TextField> txtList = new ArrayList<>();
    //data for the user
    public Image image = null;


    //final FileChooser fileChooser = new FileChooser();
    public void initiate(final Stage stage) {
        StringBuilder errortext = new StringBuilder();
        stage.setTitle("Register Form");
        txtList.add(userNameTXT);
        txtList.add(passwordTXT);
        txtList.add(confirm_passwordTXT);
        txtList.add(firstNameTXT);
        txtList.add(lastNameTXT);
        txtList.add(emailTXT);
        txtList.add(cityTXT);
        final FileChooser fileChooser = new FileChooser();
        birthdate.setOnAction((ActionEvent event)->{
        uploadImage = new Button("Choose account Image");
        uploadImage.setOnAction((final ActionEvent e) -> {
            ld=birthdate.getValue();});
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                image = new Image(file.toURI().toString());
            }
        });
    }

    public void setControl(Controller control) {
        this.control = control;
    }

    public void SignUp(MouseEvent mouseevent) {
        StringBuilder errortext = new StringBuilder();
        String missing = new String();
        for (TextField tx : txtList) {
            if (tx.getText().trim().isEmpty())
                missing = missing + tx + ", ";
        }
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
        if (!emailTXT.getText().contains("@") || !emailTXT.getText().contains(".") || emailTXT.getText().contains(",")) {
            errortext.append("please enter a real Email address\n");}
        if (errortext.toString().length() == 0)
            control.AddEntry(new String[]{userNameTXT.getText().trim(), passwordTXT.getText().trim(), firstNameTXT.getText().trim(),
                    lastNameTXT.getText().trim(), emailTXT.getText().trim(), cityTXT.getText().trim()}, Tables.Users);
         else
            errorTxt.setText(errortext.toString());
    }
}


