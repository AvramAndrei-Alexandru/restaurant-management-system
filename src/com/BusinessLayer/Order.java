package com.BusinessLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Order {
    private int OrderID;
    private Date date;
    private int tableID;
    private static int internalOrderID = 0;

    public Order(Date date, int tableID) {
        this.OrderID = ++internalOrderID;
        this.date = date;
        this.tableID = tableID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public int getTableID() {
        return tableID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return OrderID == order.OrderID &&
                tableID == order.tableID &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderID, date, tableID);
    }
}
