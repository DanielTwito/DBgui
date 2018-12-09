package sample.ModelLogic;

import javafx.beans.property.StringProperty;

public class Messege {
    private StringProperty messege;
    private StringProperty SID;


    public String getMessege() {
        return messege.get();
    }

    public StringProperty messegeProperty() {
        return messege;
    }

    public void setMessege(String messege) {
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
