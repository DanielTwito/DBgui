package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.sqlite.util.StringUtils;
import sample.Enums.Tables;
import sample.Enums.Fields;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class PaymentsForm {


    private Controller controller =new Controller();
    //Controls in the javaFX
    @FXML
    private DatePicker exp;
    @FXML
    private TextField security;
    @FXML
    private TextField cardId;
    @FXML
    private Button pay;
    @FXML
    private Text errorText;
    @FXML
    private Text totalPrice;
    private double vacPrice;

    public void setVacPrice(double vacPrice) {
        this.vacPrice = vacPrice;
        totalPrice.setText("Total Price: "+this.vacPrice);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void payAction(ActionEvent actionEvent) {
        String date="";
        try {
            date = exp.getValue().getDayOfMonth()+"/"+exp.getValue().getMonthValue()+"/"+exp.getValue().getYear();
        }catch (Exception e){
            errorText.setText("Expiration date is a must!");
            return;
        }
        errorText.setText("");
        StringBuilder message = new StringBuilder("missing:\n");
        boolean missingFlag=false;
        if (cardId.getText().trim().isEmpty()){
            message.append("Credit Card\n");
            missingFlag=true;
        }

        if (date.equals("")){
            message.append("Experation Date\n");
            missingFlag=true;
        }

        if (security.getText().trim().isEmpty()){
            message.append("cerdit card\n");
            missingFlag=true;
        }else{
            if(security.getText().length()!=3) {
                message.append("security Must be 3 digit long");
                missingFlag = true;
            }else {
                String s = security.getText().trim();
                for (int i = 0; i < security.getText().length() ; i++) {
                    if(s.charAt(i)<'0' && s.charAt(i)>'9' ){
                        message.append("security Must be 3 digit long");
                    }

                }
            }

        }

        if(!missingFlag){
            //check credit card
            ArrayList<Pair> data = new ArrayList<>();
            data.add(new Pair<String,String>(Fields.creditCard+"",cardId.getText()));
            data.add(new Pair<String,String>(Fields.expirationDate+"",date));
            data.add(new Pair<String,String>(Fields.security+"",security.getText()));
            ArrayList<HashMap<String, String>> ans= controller.ReadEntries(data,Tables.PayPal);
            if(ans.size()>0){
                double b=Double.parseDouble(ans.get(0).get("balance"));
                if(b-this.vacPrice>0){
                    System.out.println("you can buy");
                    //take money from the account
                    ArrayList<Pair> dat = new ArrayList<>();
                    Pair<String,String> x = new Pair<>(Fields.creditCard+"",cardId.getText());
                    dat.add(x);
                    controller.UpdateEntries(Tables.PayPal,Fields.balance,(b-this.vacPrice)+"",dat);
                }else{
                    errorText.setText("you dont have enough money in your account");
                }
            }else {
                errorText.setText("credit card detail are wrong");
            }
        }else{
            errorText.setText(message.toString());
        }


    }


    }

