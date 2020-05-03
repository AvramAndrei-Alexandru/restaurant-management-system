package com.PresentationLayer;

import com.BusinessLayer.CompositeProduct;
import com.BusinessLayer.MenuItem;
import com.BusinessLayer.ProductTableItem;
import com.BusinessLayer.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.List;

class GenerateProductTable {

    private Restaurant restaurant;
    private TableView<ProductTableItem> menuItemsTable;
    private TableColumn<ProductTableItem, Boolean> moreProductsColumn;
    private TableColumn<ProductTableItem, String> nameColumn;
    private TableColumn<ProductTableItem, Number> priceColumn;
    private TextArea moreItemsTextField;
    private ObservableList<ProductTableItem> tableItems = FXCollections.observableArrayList();

    GenerateProductTable(TableView<ProductTableItem> menuItemsTable, TableColumn<ProductTableItem, Boolean> moreProductsColumn, TableColumn<ProductTableItem, String> nameColumn, TableColumn<ProductTableItem, Number> priceColumn, TextArea moreItemsTextField) {
        this.menuItemsTable = menuItemsTable;
        this.moreProductsColumn = moreProductsColumn;
        this.nameColumn = nameColumn;
        this.priceColumn = priceColumn;
        this.moreItemsTextField = moreItemsTextField;
        restaurant = Restaurant.getRestaurant();
    }

    TableView<ProductTableItem> displayAndGetTable() {
        ObservableList<MenuItem> menuItems = restaurant.getMenuItemList();
        if(menuItems != null && menuItems.size() != 0) {
            tableItems.clear();
            menuItemsTable.getItems().clear();
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
            moreProductsColumn.setCellValueFactory(cellData -> cellData.getValue().simpleProperty());
            for(MenuItem menuItem : menuItems) {
                tableItems.add(new ProductTableItem(menuItem.getName(), menuItem.computePrice(), menuItem.getSimple()));
            }
            menuItemsTable.setItems(tableItems);
            return menuItemsTable;
        }
        return menuItemsTable;
    }

    TextArea displayProductContents() {
        moreItemsTextField.setText("");
        ProductTableItem selectedTableItem = menuItemsTable.getSelectionModel().getSelectedItem();
        if(selectedTableItem != null && !selectedTableItem.simpleProperty().getValue()) {
            CompositeProduct compositeProduct = restaurant.getCompositeFromTable(selectedTableItem);
            if(compositeProduct != null) {
                List<MenuItem> productsContained = compositeProduct.getMenuItemList();
                if(productsContained == null || productsContained.size() == 0) {
                    moreItemsTextField.setText("This product does not contain more items");
                }else {
                    for(MenuItem menuItem : productsContained) {
                        moreItemsTextField.setText(moreItemsTextField.getText() + "\nName = " + menuItem.getName() + " Price = " + menuItem.computePrice());
                    }
                    return moreItemsTextField;
                }
            }
        }
        return moreItemsTextField;
    }
}
