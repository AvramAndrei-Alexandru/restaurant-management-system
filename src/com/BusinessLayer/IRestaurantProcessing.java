package com.BusinessLayer;


import java.util.Date;

/**
 * @inv menuItemList != null
 * @inv orderMap != null
 * @inv orderList != null
 */
public interface IRestaurantProcessing {

    /**
     * @param name Product name
     * @pre name != null
     * @param price Product price
     * @pre price != null
     * menuItemList.size@pre = menuItemList.size-1@post
     */
    void createBaseProduct(String name, double price);

    /**
     *
     * @param name Product name
     * @pre name != null
     * menuItemList.size@pre = menuItemList.size()-1@post
     */
    void createCompositeProduct(String name);

    /**
     *
     * @param menuItem Item to delete
     * @pre menuItem != null
     * menuItemList.size@pre = menuItemList.size+1@post
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     *
     * @param menuItem Old menuItem
     * @param newItem New menuItem
     * @pre menuItem != null
     * @pre newItem != null
     */
    void editMenuItem(MenuItem menuItem, MenuItem newItem);

    /**
     *
     * @param order Order to compute the price
     * @pre order != null
     * @post price > 0
     * @return Order price
     */
    double computePriceForOrder(Order order);

    /**
     *
     * @param tableID The table ID
     * @pre tableID > 0
     * @param date The order date
     * @pre date != null
     * orderList.size@pre = orderList.size-1@post
     */
    void createNewOrder(int tableID, Date date);

    /**
     * @param order Order to generate bill
     * @pre order != null
     */
    void generateBillForOrder(Order order);

}
