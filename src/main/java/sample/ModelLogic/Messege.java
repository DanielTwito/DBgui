package sample.ModelLogic;

import javafx.beans.property.IntegerProperty;
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

    private StringProperty SID;
    private StringProperty buyer;
    private StringProperty seller;

    public Messege() { }

    public Messege(IntegerProperty messege, StringProperty SID, StringProperty buyer, StringProperty seller) {
        this.messege = messege;
        this.SID = SID;
        this.buyer = buyer;
        this.seller = seller;
    }

    public int getMessege() {
        return messege.get();
    }

    public IntegerProperty messegeProperty() {
        return messege;
    }

    public void setMessege(int messege) {
        this.messege.set(messege);
    }

    public String getSID() {
        return SID.get();
    }

    public StringProperty SIDProperty() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID.set(SID);
    }
}
