package com.DataLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationClass <T> implements Serializable {
    private String fileName;

    public SerializationClass(String fileName) {
        this.fileName = fileName;
    }

    public void writeToFile(ObservableList<T> object)
    {
        try {
            List<T> object2 = new ArrayList<>(object);
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object2);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<T> readFromFile()
    {
        ObservableList<T> result;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            ArrayList<T> result2 = (ArrayList<T>) objectInputStream.readObject();
             result = FXCollections.observableArrayList(result2);
            objectInputStream.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file does not exist.");
        }
        return null;
    }
}
