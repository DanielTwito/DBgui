package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterFormView {
    private Controller control;

    //Controls in the javaFX
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField city;
    @FXML
    private DatePicker birthdate;
    @FXML
    private Button submit;
    @FXML
    private Button uploadImage;
    @FXML
    private TextField errorTxt;
    public String date = null;
    private Stage s;
    StringBuilder errortext;
    public LocalDate ld;
    ArrayList<TextField> txtList = new ArrayList<>();
    //data for the user
    public String imageURL = null;

    @FXML
    public void initialize()
    {
        txtList.add(userName);
        txtList.add(password);
        txtList.add(confirm_password);
        txtList.add(firstName);
        txtList.add(lastName);
        txtList.add(email);
        txtList.add(city);
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
        errortext = new StringBuilder();
        String missing = new String();
        for (TextField tx : txtList) {
            if (tx.getText().trim().isEmpty())
                missing = missing + tx.getId() + ", ";
        }
        missing=missing.substring(0,missing.length()-2);
        if (ld == null)// checks if no birth date was added
            errortext.append("please fill your date of birth \n");
        if (!missing.isEmpty())//checks if text fields are empty
            errortext.append("Please fill all the required fieids in the form. missing: " + missing + ".\n");
        if (ld != null && Period.between(LocalDate.now(), ld).getYears() < 18)//checks if user age in null
            errortext.append("all users must be over 18 years old. \n");
        // checks if username already in db
        ArrayList<Pair> tmp1 = new ArrayList<>();
        tmp1.add(new Pair<>(Fields.Username, userName.getText()));
        ArrayList<HashMap<String, String>> ContainsUser = control.ReadEntries(tmp1, Tables.Users);
        if (ContainsUser != null && ContainsUser.size() == 0)
            errortext.append("user name already in the system please choose a different one.\n");
        // checks if Email already in db
        ArrayList<Pair> tmp2 = new ArrayList<>();
        tmp1.add(new Pair<>(Fields.Email, email.getText().trim()));
        ArrayList<HashMap<String, String>> ContainsEmail = control.ReadEntries(tmp2, Tables.Users);
        if (ContainsEmail != null && ContainsEmail.size() == 0)
            errortext.append("email address already in the system please choose a different one.\n");
        // check if password confirm is legit
        if (!password.getText().trim().equals(confirm_password.getText().trim())) {errortext.append("Password and confirm password are not the same\n");}

        if(password.getText().trim().length()<6){errortext.append("Password must be over 6 characters long\n");}
        //check the email according to a regex
        String regexMail = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        pattern = Pattern.compile(regexMail, Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(email.getText().trim()).matches())
            errortext.append("please enter a real Email address\n");

        //turns the image url to a byte array
        BufferedImage userImage= ImageIO.read(new File(imageURL));
        ByteArrayOutputStream imageStream= new ByteArrayOutputStream();
        ImageIO.write(userImage, "jpg", imageStream);
        byte [] bytePhoto = imageStream.toByteArray();
        String strImage = new String(bytePhoto);
        if (errortext.toString().length() == 0) {
            ArrayList<Pair> user = new ArrayList<>();
            user.add(new Pair<>(Fields.Username, userName.getText().trim()));
            user.add(new Pair<>(Fields.Password, password.getText().trim()));
            user.add(new Pair<>(Fields.FirstName, firstName.getText().trim()));
            user.add(new Pair<>(Fields.LastName, lastName.getText().trim()));
            user.add(new Pair<>(Fields.Email, email.getText().trim()));
            user.add(new Pair<>(Fields.city, city.getText().trim()));
            user.add(new Pair<>(Fields.image, strImage));
            user.add(new Pair<>(Fields.Password, password.getText().trim()));
            System.out.println("adding user DONE");
            //control.AddEntry(user,Tables.Users);
        }
        else {errorTxt.setText(errortext.toString());}
    }


                    //this is to get image from byte array to image
        /**
        ByteArrayInputStream input_stream= new ByteArrayInputStream(byte_array);
        BufferedImage final_buffered_image = ImageIO.read(input_stream);
        ImageIO.write(final_buffered_image , "jpg", new File("final_file.jpg") );
        System.out.println("Converted Successfully!");
        **/




    public void OnAction(ActionEvent actionEvent)
    {
        ld = birthdate.getValue();
    }

    public void onUploadAction(ActionEvent actionEvent)
    {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(s);
        uploadImage = new Button("Choose user Image");
        if (file != null) {
            imageURL = file.toURI().toString();
        }
    }

}