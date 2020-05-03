package com.BusinessLayer;

import javafx.beans.property.*;

public class ProductTableItem {
    private StringProperty name;
    private DoubleProperty price;
    private BooleanProperty simple;

    public ProductTableItem(String name, double price, boolean simple) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.simple = new SimpleBooleanProperty(simple);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public BooleanProperty simpleProperty() {
        return simple;
    }
}
