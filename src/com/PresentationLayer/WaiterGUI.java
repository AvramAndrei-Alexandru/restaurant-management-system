package com.PresentationLayer;

import com.BusinessLayer.*;
import com.BusinessLayer.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Date;
import java.util.List;

public class WaiterGUI implements IRestaurantProcessing {

    private Restaurant restaurant;
    private ProductTableItem selectedTableItem;
    private OrderTableItem selectedOrderTableItem;
    private OrderTableItem fixedOrderTableItem;
    private ObservableList<OrderTableItem> orderTableItems = FXCollections.observableArrayList();

    public WaiterGUI() {
        restaurant = Restaurant.getRestaurant();
    }

    @FXML
    private TableColumn<OrderTableItem, Number> orderIDColumn;
    @FXML
    private TableColumn<OrderTableItem, Number> tableIDColumn;
    @FXML
    private TableColumn<OrderTableItem, Number> priceOrderColumn;
    @FXML
    private TableColumn<OrderTableItem, String>  dateColumn;
    @FXML
    private TextField tableIDTextField;
    @FXML
    private TableView<OrderTableItem> orderTable;
    @FXML
    private TextArea moreItemsTextField;
    @FXML
    private TableColumn<ProductTableItem, Boolean> moreProductsColumn;
    @FXML
    private TableColumn<ProductTableItem, String> nameColumn;
    @FXML
    private TableColumn<ProductTableItem, Number> priceColumn;
    @FXML
    private TableView<ProductTableItem> menuItemsTable;

    @Override
    public void createBaseProduct(String name, double price) {
    }

    @Override
    public void createCompositeProduct(String name) {
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
    }

    @Override
    public void editMenuItem(MenuItem menuItem, MenuItem newItem) {
    }

    @Override
    public double computePriceForOrder(Order order) {
        return restaurant.computePriceForOrder(order);
    }

    @Override
    public void createNewOrder(int tableID, Date date) {
        restaurant.createNewOrder(tableID, date);
    }

    @Override
    public void generateBillForOrder(Order order) {
        restaurant.generateBillForOrder(order);
    }

    @FXML
    private void tableMouseClickedFunction() {
        selectedTableItem = menuItemsTable.getSelectionModel().getSelectedItem();
        moreItemsTextField = new GenerateProductTable(menuItemsTable, moreProductsColumn, nameColumn, priceColumn, moreItemsTextField).displayProductContents();
    }

    @FXML
    private void listProductsButtonPressed() {
        menuItemsTable = new GenerateProductTable(menuItemsTable, moreProductsColumn, nameColumn, priceColumn, moreItemsTextField).displayAndGetTable();
    }

    @FXML
    private void orderTableMouseClicked() {
        selectedOrderTableItem = orderTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void listOrderButtonPressed() {
        List<Order> orderList = restaurant.getOrderList();
        orderTable.getItems().clear();
        if(orderList != null && orderList.size() != 0) {
            orderTableItems.clear();
            orderIDColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty());
            tableIDColumn.setCellValueFactory(cellData -> cellData.getValue().tableIDProperty());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            priceOrderColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
            for(Order order : orderList) {
                orderTableItems.add(restaurant.getOrderTableItemFromOrder(order));
            }
            orderTable.setItems(orderTableItems);
        }
    }

    @FXML
    private void createOrderButtonPressed() {
        if(!tableIDTextField.getText().equals("")) {
            try{
                int tableID = Integer.parseInt(tableIDTextField.getText());
                createNewOrder(tableID, new Date());
                listOrderButtonPressed();

            }catch (Exception e) {
                System.out.println("Not a number in tableID text field");
            }
        }
    }

    @FXML
    private void selectOrderFromTableMousePressed() {
        fixedOrderTableItem = selectedOrderTableItem;
    }

    @FXML
    private void addSelectedProductToOrder() {
        if(selectedTableItem != null && fixedOrderTableItem != null) {
            Order order = restaurant.getOrderFromOrderTableItem(selectedOrderTableItem);
            if(order != null) {
                MenuItem menuItem;
                if(selectedTableItem.simpleProperty().getValue())
                    menuItem = restaurant.getBaseProductFromTable(selectedTableItem);
                else
                    menuItem = restaurant.getCompositeFromTable(selectedTableItem);
                if(menuItem != null) {
                    restaurant.addItemToOrder(order, menuItem);
                    listOrderButtonPressed();
                }
            }
        }
    }

    @FXML
    private void computeBillForSelectedOrderButtonPressed() {
        if(selectedOrderTableItem != null && selectedOrderTableItem.priceProperty().getValue() != 0) {
            generateBillForOrder(restaurant.getOrderFromOrderTableItem(selectedOrderTableItem));
            listOrderButtonPressed();
        }
    }
}
