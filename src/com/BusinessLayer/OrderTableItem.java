package com.BusinessLayer;

import javafx.beans.property.*;

public class OrderTableItem {
    private IntegerProperty orderID;
    private IntegerProperty tableID;
    private DoubleProperty price;
    private StringProperty date;

    OrderTableItem(int orderID, int tableID, double price, String date) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.tableID = new SimpleIntegerProperty(tableID);
        this.price = new SimpleDoubleProperty(price);
        this.date = new SimpleStringProperty(date);
    }

    public IntegerProperty orderIDProperty() {
        return orderID;
    }

    public IntegerProperty tableIDProperty() {
        return tableID;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty dateProperty() {
        return date;
    }
}
