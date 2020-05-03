package com.BusinessLayer;

import com.DataLayer.FileWriterClass;
import com.DataLayer.SerializationClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;

public class Restaurant extends  Observable implements IRestaurantProcessing {
    private ObservableList<MenuItem> menuItemList = FXCollections.observableArrayList();
    private Map<Order, List<MenuItem>> orderMap = new HashMap<>();
    private List<Order> orderList = new ArrayList<>();
    private static Restaurant restaurant;
    private String inputFileName;

    private Restaurant(){
        assertInvariant();
        inputFileName = "default.ser";
    }

    private void assertInvariant() {
        assert menuItemList != null;
        assert orderList != null;
        assert orderMap != null;
    }

    @Override
    public void createBaseProduct(String name, double price) {
        assert name != null;
        assert price > 0;
        int previousSize = menuItemList.size();
        assertInvariant();
        BaseProduct baseProduct = new BaseProduct(name, price);
        if(!containsTheProductName(null, name, true))
             menuItemList.add(baseProduct);
        assert previousSize == menuItemList.size() - 1;
    }


    @Override
    public void createCompositeProduct(String name) {
        assertInvariant();
        assert name != null;
        int previousSize = menuItemList.size();
        CompositeProduct compositeProduct = new CompositeProduct(name);
        if(!containsTheProductName(null, name, true))
             menuItemList.add(compositeProduct);
        assert previousSize == menuItemList.size() - 1;
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        assertInvariant();
        assert menuItem != null;
        int previousSize = menuItemList.size();
        menuItemList.remove(menuItem);
        assert previousSize == menuItemList.size() + 1;
    }

    @Override
    public void editMenuItem(MenuItem menuItem, MenuItem newItem) {
        assertInvariant();
        assert menuItem != null;
        assert newItem != null;
        int index = -1;
        for(MenuItem menuItem1 : menuItemList) {
            if(menuItem1.getName().equals(menuItem.getName())) {
                index = menuItemList.indexOf(menuItem1);
                break;
            }
        }
        if(index != -1) {
            if(menuItem.getSimple()) {
                menuItemList.get(index).setName(newItem.getName());
                ((BaseProduct)menuItemList.get(index)).setPrice(newItem.computePrice());
            }
            else {
                menuItemList.get(index).setName(newItem.getName());
            }

        }
    }

    @Override
    public double computePriceForOrder(Order order) {
        assertInvariant();
        assert order != null;
        double price = 0;
        if(orderMap.get(order) != null && orderMap.get(order).size() != 0) {
            for(MenuItem menuItem : orderMap.get(order)) {
                price += menuItem.computePrice();
            }
        }
        assert price > 0;
       return price;
    }

    @Override
    public void createNewOrder(int tableID, Date date) {
        assertInvariant();
        assert tableID > 0;
        assert date != null;
        int previousSize = orderList.size();
        Order order = new Order(date, tableID);
        orderList.add(order);
        orderMap.put(order, new ArrayList<>());
        assert previousSize == orderList.size() - 1;
    }

    @Override
    public void generateBillForOrder(Order order) {
        assertInvariant();
        assert order != null;
        new FileWriterClass().generateBill(orderMap, order);
        orderList.remove(order);
        orderMap.remove(order);
    }

    public void addItemToOrder (Order order, MenuItem menuItem){
        assertInvariant();
        orderMap.get(order).add(menuItem);
        if(!menuItem.getSimple()) {
            String name = menuItem.getName();
            setChanged();
            notifyObservers(name);
        }
    }

    public static Restaurant getRestaurant() {
        if(restaurant == null) {
            restaurant = new Restaurant();
            return restaurant;
        }
        else
            return restaurant;
    }


    public List<Order> getOrderList() {
        assertInvariant();
        return orderList;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public OrderTableItem getOrderTableItemFromOrder (Order order){
        return new OrderTableItem(order.getOrderID(), order.getTableID(), computePriceForOrder(order), order.getDate());
    }

    public Order getOrderFromOrderTableItem(OrderTableItem orderTableItem) {
        assertInvariant();
        for(Order order: orderList) {
            if(order.getOrderID() == orderTableItem.orderIDProperty().getValue()) {
                return order;
            }
        }
        return null;
    }

    public ObservableList<MenuItem> getMenuItemList() {
        assertInvariant();
        return this.menuItemList;
    }

    public void saveMenuToFile() {
        assertInvariant();
        new SerializationClass<MenuItem>(inputFileName).writeToFile(menuItemList);
    }

    public void loadMenuFromFile() {
        this.menuItemList = new SerializationClass<MenuItem>(inputFileName).readFromFile();
    }

    public void editCompositeProductMenuItems(ProductTableItem compositeProduct, ProductTableItem productToAdd) {
        assertInvariant();
        CompositeProduct compositeProduct1 = new CompositeProduct(compositeProduct.nameProperty().getValue());
        MenuItem secondProduct;
        if(productToAdd.simpleProperty().getValue())
            secondProduct = new BaseProduct(productToAdd.nameProperty().getValue(), productToAdd.priceProperty().getValue());
        else
            secondProduct = new CompositeProduct(productToAdd.nameProperty().getValue());
        for(MenuItem menuItem : menuItemList) {
            if(menuItem.getName().equals(compositeProduct1.getName())) {
                for(MenuItem menuItem1 : menuItemList) {
                    if(menuItem1.getName().equals(secondProduct.getName())) {
                        if(!containsTheProductName(((CompositeProduct) menuItem).getMenuItemList(), menuItem1.getName(), false))
                            ((CompositeProduct) menuItem).addToMenuItemList(menuItem1);
                        break;
                    }
                }
                break;
            }
        }
    }

    public CompositeProduct getCompositeFromTable(ProductTableItem tableItem) {
        assertInvariant();
        CompositeProduct compositeProduct = new CompositeProduct(tableItem.nameProperty().getValue());
        for(MenuItem menuItem : menuItemList) {
            if(menuItem.getName().equals(compositeProduct.getName())) {
                return (CompositeProduct) menuItem;
            }
        }

        return null;
    }

    public BaseProduct getBaseProductFromTable(ProductTableItem tableItem) {
        assertInvariant();
        BaseProduct baseProduct = new BaseProduct(tableItem.nameProperty().getValue(), tableItem.priceProperty().getValue());
        for(MenuItem menuItem : menuItemList) {
            if(menuItem.getName().equals(baseProduct.getName())) {
                return (BaseProduct) menuItem;
            }
        }

        return null;
    }

    private boolean containsTheProductName(List<MenuItem> list, String productName, boolean useGlobalList) {
        assertInvariant();
        if(!useGlobalList) {
            for(MenuItem menuItem : list) {
                if(menuItem.getName().equals(productName)) {
                    return true;
                }
            }
        }
        else {
            for(MenuItem menuItem : menuItemList) {
                if(menuItem.getName().equals(productName)) {
                    return true;
                }
            }
        }
        return false;
    }


}
