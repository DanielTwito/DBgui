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
import javafx.util.Pair;
import sample.Controller;
import sample.Enums.Fields;
import sample.Enums.Tables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static sample.Enums.Tables.Users;

public class RegisterFormView  extends Application {
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
    public String date = null;
    StringBuilder errortext;
    public LocalDate ld;
    ArrayList<TextField> txtList = new ArrayList<>();
    //data for the user
    public String imageURL = null;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterForm.fxml"));
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("RegisterForm.css").toExternalForm());
        stage.setTitle("Registration Form");
        stage.show();
        //list of all text fields
        txtList.add(userNameTXT);
        txtList.add(passwordTXT);
        txtList.add(confirm_passwordTXT);
        txtList.add(firstNameTXT);
        txtList.add(lastNameTXT);
        txtList.add(emailTXT);
        txtList.add(cityTXT);
        final FileChooser fileChooser = new FileChooser();
        birthdate.setOnAction((ActionEvent event) -> ld = birthdate.getValue());
        uploadImage.setOnAction((final ActionEvent e) -> {
            File file = fileChooser.showOpenDialog(stage);
            uploadImage = new Button("Choose user Image");
            if (file != null) {
                imageURL = file.toURI().toString();
            }
        });
    }

    /**
     * setter for the controller
     *
     * @param control
     */
    public void setControl(Controller control) {
        this.control = control;
    }

    /**
     * react to the submit button
     *
     * @param event
     */
    @FXML
    protected void SignUp(ActionEvent event)  throws IOException {
        errorTxt.clear();
        errortext = new StringBuilder();
        String missing = new String();
        for (TextField tx : txtList) {
            if (tx.getText().trim().isEmpty())
                missing = missing + tx + ", ";
        }
        if (ld == null)// checks if no birth date was added
            errortext.append("please fill your date of birth \n");
        if (!missing.isEmpty())//checks if text fields are empty
            errortext.append("Please fill all the required fieids in the form. missing: " + missing + ".\n");
        if (ld != null && Period.between(LocalDate.now(), ld).getYears() < 18)//checks if user age in null
            errortext.append("all users must be over 18 years old. \n");
        // checks if username already in db
        ArrayList<Pair> tmp1 = new ArrayList<>();
        tmp1.add(new Pair<>(Fields.Username, userNameTXT.getText()));
        ArrayList<HashMap<String, String>> ContainsUser = control.ReadEntries(tmp1, Tables.Users);
        if (ContainsUser != null && ContainsUser.size() == 0)
            errortext.append("user name already in the system please choose a different one.\n");
        // checks if Email already in db
        ArrayList<HashMap<String, String>> ContainsEmail = control.ReadEntries(new ArrayList<Pair<Fields, String>>().add(new Pair<>(Fields.Email, emailTXT.getText())), Tables.Users);
        if (ContainsEmail != null && ContainsEmail.size() == 0)
            errortext.append("email address already in the system please choose a different one.\n");
        // check if password confirm is legit
        if (!passwordTXT.getText().trim().equals(confirm_passwordTXT.getText().trim())) {
            errortext.append("Password and confirm password are not the same\n");
        }
        if(passwordTXT.getText().trim().length()<6){
            errortext.append("Password must be over 6 characters long\n");
        }
        //check the email according to a regex
        String regexMail = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        pattern = Pattern.compile(regexMail, Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(emailTXT.getText().trim()).matches())
            errortext.append("please enter a real Email address\n");

        //turns the image url to a byte array
        BufferedImage userImage= ImageIO.read(new File(imageURL));
        ByteArrayOutputStream imageStream= new ByteArrayOutputStream();
        ImageIO.write(userImage, "jpg", imageStream);
        byte [] bytePhoto = imageStream.toByteArray();

                    //this is to get image from byte array to image
        /**
        ByteArrayInputStream input_stream= new ByteArrayInputStream(byte_array);
        BufferedImage final_buffered_image = ImageIO.read(input_stream);
        ImageIO.write(final_buffered_image , "jpg", new File("final_file.jpg") );
        System.out.println("Converted Successfully!");
        **/

        //if the length of the error messege is zero then it addes the user, else it prints the error messege on the screen
        if (errortext.toString().length() == 0)
            control.AddEntry(new ArrayList<String>(Arrays.asList(userNameTXT.getText().trim(), passwordTXT.getText().trim(), firstNameTXT.getText().trim(),
                    lastNameTXT.getText().trim(), emailTXT.getText().trim(), cityTXT.getText().trim(), imageURL)), Tables.Users);
        else {
            errorTxt.setText(errortext.toString());
            return;

        }
    }
}