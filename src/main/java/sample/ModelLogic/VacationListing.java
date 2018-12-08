package sample.ModelLogic;

import javafx.scene.control.Button;

public class VacationListing {
    private String dest;
    private String date;
    private int price;
    private Boolean isConnection;
    private Button view;

    public VacationListing(String dest, String date, int price, Boolean isConnection) {
        this.dest = dest;
        this.date = date;
        this.price = price;
        this.isConnection = isConnection;
        this.view = new Button("View");
        this.view.setStyle("-fx-background-color: #0865F1");
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int price) {
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

    public int getPrice() {
        return price;
    }

    public Boolean getConnection() {
        return isConnection;
    }
}
