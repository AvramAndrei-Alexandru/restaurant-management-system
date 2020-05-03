package com.BusinessLayer;
import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    public abstract double computePrice();
    public abstract String getName();
    public abstract void setName(String newName);
    public abstract boolean getSimple();
    public abstract String toString();

}
