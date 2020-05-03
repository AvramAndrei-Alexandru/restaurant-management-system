package com.PresentationLayer;

import com.BusinessLayer.MenuItem;
import com.BusinessLayer.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Date;

public class AdministratorGUI implements IRestaurantProcessing {
    private Restaurant restaurant;
    private ProductTableItem selectedTableItem;
    private ProductTableItem compositeTableItem;

    public AdministratorGUI() {
        restaurant = Restaurant.getRestaurant();
    }

    @FXML
    private TextField newPriceTextField;
    @FXML
    private TextField newNameTextField;
    @FXML
    private TextArea moreItemsTextField;
    @FXML
    private  CheckBox compositeCBox;
    @FXML
    private TableColumn<ProductTableItem, Boolean> moreProductsColumn;
    @FXML
    private TableColumn<ProductTableItem, String> nameColumn;
    @FXML
    private TableColumn<ProductTableItem, Number> priceColumn;
    @FXML
    private TableView<ProductTableItem> menuItemsTable;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productNameTextField;

    @Override
    public void createBaseProduct(String name, double price) {
        restaurant.createBaseProduct(name, price);
    }

    @Override
    public void createCompositeProduct(String name) {
        restaurant.createCompositeProduct(name);
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        restaurant.deleteMenuItem(menuItem);
    }

    @Override
    public void editMenuItem(MenuItem menuItem, MenuItem newItem) {
        restaurant.editMenuItem(menuItem, newItem);
    }

    @Override
    public double computePriceForOrder(Order order) {
        return 0;
    }

    @Override
    public void createNewOrder(int tableID, Date date) {
    }

    @Override
    public void generateBillForOrder(Order order) {
    }

    @FXML
    private void clearTextButtonPressed() {
        productNameTextField.setText("");
        productPriceTextField.setText("");
        newNameTextField.setText("");
        newPriceTextField.setText("");
        compositeCBox.setSelected(false);
    }

    @FXML
    private void addSimpleProductButtonPressed() {
        boolean productCanBeAdded = true;
        String productName = productNameTextField.getText();
        String productPriceString = productPriceTextField.getText();
        if(!productName.equals("")) {
            double price = 0;
            if(!compositeCBox.isSelected()) {
                try {
                    price = Double.parseDouble(productPriceString);
                }catch (Exception e) {
                    productCanBeAdded = false;
                }
                if(price <= 0) {
                    productCanBeAdded = false;
                }
            }
            if(productCanBeAdded){
                if(!compositeCBox.isSelected())
                    createBaseProduct(productName, price);
                else
                    createCompositeProduct(productName);
            }
        }
        listProductsButtonPressed();
        productNameTextField.setText("");
        productPriceTextField.setText("");
        compositeCBox.setSelected(false);
    }

    @FXML
    private void listProductsButtonPressed() {
        menuItemsTable = new GenerateProductTable(menuItemsTable, moreProductsColumn, nameColumn, priceColumn, moreItemsTextField).displayAndGetTable();
    }

    @FXML
    private void tableMouseClickedFunction() {
        selectedTableItem = menuItemsTable.getSelectionModel().getSelectedItem();
        moreItemsTextField = new GenerateProductTable(menuItemsTable, moreProductsColumn, nameColumn, priceColumn, moreItemsTextField).displayProductContents();
    }

    @FXML
    private void loadButtonPressed() {
        restaurant.loadMenuFromFile();
        menuItemsTable.getItems().clear();
        listProductsButtonPressed();
    }

    @FXML
    private void saveItemsMouseClicked() {
        if(restaurant.getMenuItemList() != null && restaurant.getMenuItemList().size() != 0)
            restaurant.saveMenuToFile();
    }

    @FXML
    private void addProductsToCompositeButtonPressed() {
        if(compositeTableItem != null && !compositeTableItem.simpleProperty().getValue()) {
            restaurant.editCompositeProductMenuItems(compositeTableItem, selectedTableItem);
            listProductsButtonPressed();
        }
    }

    @FXML
    private void selectCompositeProductMouseClicked() {
        if(selectedTableItem != null && !selectedTableItem.simpleProperty().getValue()) {
            compositeTableItem = selectedTableItem;
        }
    }

    @FXML
    private void removeSelectedButtonPressed() {
        if(selectedTableItem != null) {
            MenuItem menuItem;
            if(!selectedTableItem.simpleProperty().getValue())
                menuItem = restaurant.getCompositeFromTable(selectedTableItem);
            else
                menuItem = restaurant.getBaseProductFromTable(selectedTableItem);
            deleteMenuItem(menuItem);
            listProductsButtonPressed();
        }
    }

    @FXML
    private void editButtonPressed() {
        if(!newNameTextField.getText().equals("")) {
            if(selectedTableItem != null) {
                if(!selectedTableItem.simpleProperty().getValue()) {
                    editMenuItem(restaurant.getCompositeFromTable(selectedTableItem), new CompositeProduct(newNameTextField.getText()));
                }
                else {
                    double price = 0;
                    try {
                        price = Double.parseDouble(newPriceTextField.getText());
                    } catch (NumberFormatException ignored) {
                    }
                    if(price > 0)
                        editMenuItem(restaurant.getBaseProductFromTable(selectedTableItem), new BaseProduct(newNameTextField.getText(), price));
                }
            }
        }
        listProductsButtonPressed();
    }
}
