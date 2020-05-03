package com.BusinessLayer;

public class BaseProduct extends MenuItem {

    private String name;
    private double price;
    private boolean simple;

    public BaseProduct(String name, double price) {
        this.name = name;
        this.price = price;
        this.simple = true;
    }

    @Override
    public double computePrice() {
        return this.price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getSimple() {
        return simple;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }


    @Override
    public String toString() {
        return "BaseProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
