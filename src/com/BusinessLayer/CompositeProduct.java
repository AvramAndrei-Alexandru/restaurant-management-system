package com.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    private String name;

    private List<MenuItem> menuItemList = new ArrayList<>();

    public CompositeProduct(String name) {
        this.name = name;
    }

    @Override
    public double computePrice() {
        double price = 0;
        if(menuItemList != null && menuItemList.size() != 0) {
            for(MenuItem menuItem : menuItemList){
                price += menuItem.computePrice();
            }
        }
        return price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }


    @Override
    public boolean getSimple() {
        return false;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    void addToMenuItemList(MenuItem newItem) {
        menuItemList.add(newItem);
    }


    @Override
    public String toString() {
        return "CompositeProduct{" +
                "name='" + name + '\'' +
                ", menuItemList=" + menuItemList +
                '}';
    }
}
