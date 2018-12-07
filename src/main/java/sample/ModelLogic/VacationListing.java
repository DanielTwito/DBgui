package sample.ModelLogic;

public class VacationListing {
    String dest;
    String date;
    String price;
    Boolean isConnection;

    public VacationListing(String dest, String date, String price, Boolean isConnection) {
        this.dest = dest;
        this.date = date;
        this.price = price;
        this.isConnection = isConnection;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setConnection(Boolean connection) {
        isConnection = connection;
    }

    public String getDest() {
        return dest;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public Boolean getConnection() {
        return isConnection;
    }
}
