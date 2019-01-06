package sample.ModelLogic;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Messege {
    private IntegerProperty messege;

    public String getBuyer() {
        return buyer.get();
    }

    public StringProperty buyerProperty() {
        return buyer;
    }

    public String getSeller() {
        return seller.get();
    }

    public StringProperty sellerProperty() {
        return seller;
    }

    private StringProperty VacationID;
    private StringProperty buyer;
    private StringProperty seller;
    private StringProperty VacIDnSeller;
    private StringProperty info;
    private IntegerProperty vacationToTrade;

    /**
     * returns the VacIDnSeller variable as String
     * @return a string representation of VacIDnSeller
     */
    public String getVacIDnSeller() {
        return VacIDnSeller.get();
    }

    /**
     * returns the VacIDnSeller variable as StringProperty
     * @return the value of VacIDnSeller
     */
    public StringProperty vacIDnSellerProperty() {
        return VacIDnSeller;
    }

    public Messege() { }

    public Messege(IntegerProperty messege, StringProperty VacationID, StringProperty buyer, StringProperty seller, BooleanProperty trade, IntegerProperty vacTT) {
        this.messege = messege;
        this.VacationID = VacationID;
        this.buyer = buyer;
        this.seller = seller;
        this.vacationToTrade = vacTT;
        this.VacIDnSeller = new SimpleStringProperty(VacationID.toString()+","+seller.toString());
        if(trade.getValue())
            info = new SimpleStringProperty("New Trade Request");
        else
            info = new SimpleStringProperty("New Purchase Request");
    }

    /**
     * returns a message variable as int IntegerProperty
     * @return a int representation if message
     */
    public int getMessege() {
        return messege.get();
    }

    /**
     *  returns a message variable as IntegerProperty
     * @return the value of message
     */
    public IntegerProperty messegeProperty() {
        return messege;
    }

    /**
     * sets the value of message
     * @param messege - the new value of message that will be inserted
     */
    public void setMessege(int messege) {
        this.messege.set(messege);
    }
    /**
     * returns the VacationID variable as String
     * @return a string representation of VacationID
     */
    public String getVacationID() {return VacationID.get();}
    /**
     * returns the SIDProperty variable as StringProperty
     * @return the value of SIDProperty variable
     */
   public StringProperty SIDProperty() {
        return VacationID;
    }

    /**
     * sets the value of  VacationID variable
     * @param VacationID - the new VacationID that will be inserted
     */
    public void setVacationID(String VacationID) {
        this.VacationID.set(VacationID);
    }

    public String getInfo() {
        return info.get();
    }

    public StringProperty infoProperty() {
        return info;
    }

    public void setInfo(String info) {
        this.info.set(info);
    }

    public int getVacationToTrade() {
        return vacationToTrade.get();
    }

    public IntegerProperty vacationToTradeProperty() {
        return vacationToTrade;
    }

    public void setVacationToTrade(int vacationToTrade) {
        this.vacationToTrade.set(vacationToTrade);
    }
}
