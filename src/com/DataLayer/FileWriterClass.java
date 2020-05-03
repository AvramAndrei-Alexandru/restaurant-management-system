package com.DataLayer;

import com.BusinessLayer.MenuItem;
import com.BusinessLayer.Order;
import com.BusinessLayer.Restaurant;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileWriterClass {
    public void generateBill(Map<Order, List<MenuItem>> orderMap, Order order) {
        if(order != null && orderMap.get(order) != null && orderMap.get(order).size() != 0) {
            StringBuilder bill = new StringBuilder("Order ID = " + order.getOrderID());
            bill.append("\nTable ID = ").append(order.getTableID());
            bill.append("\nDate = ").append(order.getDate());
            bill.append("\nItems ordered: ");
            double price = Restaurant.getRestaurant().computePriceForOrder(order);
            for(MenuItem menuItem : orderMap.get(order)) {
                bill.append("\nName = ").append(menuItem.getName()).append(" Price = ").append(menuItem.computePrice());
            }
            bill.append("\nTotal price = ").append(price);
            try {
                FileWriter fileWriter = new FileWriter("Order" + order.getDate().replace(":", "-") + ".txt");
                fileWriter.write(bill.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
