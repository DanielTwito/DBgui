package sample.ModelLogic;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class VacationListing {
    private StringProperty dest;
    private StringProperty date;
    private IntegerProperty price;
    private BooleanProperty Tradeable;

    public void setVacID(String vacID) {
        this.VacID.set(vacID);
    }

    public String getVacID() {
        return VacID.get();
    }

    public StringProperty vacIDProperty() {
        return VacID;
    }

    private StringProperty VacID;

    public VacationListing(StringProperty dest, StringProperty date, IntegerProperty price, BooleanProperty Tradeable, StringProperty VacID) {
        this.dest = dest;
        this.date = date;
        this.price = price;
        this.Tradeable = Tradeable;
        this.VacID = VacID;
    }

    @Override
    public String toString() {
        return "VacationListing{" +
                "dest=" + dest +
                ", date=" + date +
                ", price=" + price +
                ", Tradeable=" + Tradeable +
                ", VacID="+VacID+'}';
    }
    public void setDest(String dest) {
        this.dest.set(dest);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setIsConnection(boolean isConnection) {
        this.Tradeable.set(isConnection);
    }

    public String getDest() {
        return dest.get();
    }

    public StringProperty destProperty() {
        return dest;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public boolean getTradeable() {
        return Tradeable.get();
    }

    public BooleanProperty TradeableProperty() {
        return Tradeable;
    }
}
