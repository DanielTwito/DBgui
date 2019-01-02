package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentsForm {


    private Paypal paypal;
    private Controller controller ;
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
    @FXML
    private Label approved;
    @FXML
    ImageView payp;

    private double vacPrice;
    private int vacId;
    private String seller;
    private String buyer;

    @FXML
    public void initialize()
    {
        payp.setImage(new Image(getClass().getClassLoader().getResourceAsStream("Paypal-logo.png")));
        cardId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardId.setText(newValue.replaceAll("[^\\d]", ""));
                }}});
        BackgroundImage myBI= new BackgroundImage(
                new Image(getClass().getClassLoader().getResourceAsStream("addToListingBackground.png"),
                        800,550,
                        false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
//        mainSignUp.setBackground(new Background(myBI));
    }

    public void setVacID(int vacId) {
        this.vacId = vacId;
        ArrayList<Pair> data = new ArrayList<>();
        data.add(new Pair<String, String>(Fields.VacId + "", vacId + ""));
        ArrayList<HashMap<String, String>> ans = controller.ReadEntries(data, Tables.ListingVacation);
        totalPrice.setText("Total Price: " + ans.get(0).get(Fields.price + ""));
        this.vacPrice= Double.parseDouble(ans.get(0).get(Fields.price+""));
        this.seller = ans.get(0).get(Fields.Seller + "");
        data = new ArrayList<>();
        data.add(new Pair<String, String>(Fields.VacId + "", vacId + ""));
        data.add(new Pair<String, String>(Fields.Seller + "", seller + ""));
        data.add(new Pair<String, String>(Fields.approved + "", "1"));
        ans.clear();
        ans = controller.ReadEntries(data, Tables.PurchaseRequest);
        this.buyer = ans.get(0).get("Buyer");

    }

    public void setController(Controller controller) {

        this.controller = controller;
        this.paypal=new Paypal(controller);
    }

    public void payAction(ActionEvent actionEvent) {
        String date = "";
        try {
            date = exp.getValue().getDayOfMonth() + "/" + exp.getValue().getMonthValue() + "/" + exp.getValue().getYear();
        } catch (Exception e) {
            errorText.setText("Expiration date is a must!");
            return;
        }
        errorText.setText("");
        StringBuilder message = new StringBuilder("missing:\n");
        boolean missingFlag = false;
        if (cardId.getText().trim().isEmpty()) {
            message.append("Credit Card\n");
            missingFlag = true;
        }

        if (date.equals("")) {
            message.append("Experation Date\n");
            missingFlag = true;
        }

        if (security.getText().trim().isEmpty()) {
            message.append("Security Number card\n");
            missingFlag = true;
        } else {
            if (security.getText().length() != 3) {
                message.append("security Must be 3 digit long");
                missingFlag = true;
            } else {
                String s = security.getText().trim();
                for (int i = 0; i < security.getText().length(); i++) {
                    if (s.charAt(i) < '0' && s.charAt(i) > '9') {
                        message.append("security Must be 3 digit long");
                    }

                }
            }

        }

        if (!missingFlag) {
                boolean resualt = this.paypal.exsecuteTrans(seller,buyer,vacId,vacPrice);
                if (resualt){
                    documentTransaction();
                    deleteVactionFromTheBoard();
                    showApprove();
                }else{
                    errorText.setText("cant complete the deal!");
                }



        } else {
            errorText.setText(message.toString());
        }


    }

    private void showApprove() {
        this.cardId.setVisible(false);
        this.exp.setVisible(false);
        this.security.setVisible(false);
        this.totalPrice.setVisible(false);
        this.pay.setVisible(false);
        this.approved.setVisible(true);
    }

    private void documentTransaction() {
        ArrayList<Pair> toAdd = new ArrayList<>();
        toAdd.add(new Pair<String,String>(Fields.Seller+"",seller));
        toAdd.add(new Pair<String,String>(Fields.Buyer+"",buyer));
        toAdd.add(new Pair<String,String>(Fields.price+"",vacPrice+""));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateIncludeTime=dtf.format(now);
        String[] splited = dateIncludeTime.split("\\s+");
        String date = splited[0];
        String time = splited[1];
        toAdd.add(new Pair<String,String>(Fields.transactionDate+"",date));
        toAdd.add(new Pair<String,String>(Fields.transactionTime+"",time));
        controller.AddEntry(toAdd,Tables.Transactions);
    }

    private void deleteVactionFromTheBoard() {
        ArrayList<Pair> toDelete = new ArrayList<>();
        toDelete.add(new Pair<String,String>(Fields.VacId+"",vacId+""));
        controller.DeleteEntry(Tables.PurchaseRequest,toDelete);
        controller.DeleteEntry(Tables.ListingVacation,toDelete);

    }

}





