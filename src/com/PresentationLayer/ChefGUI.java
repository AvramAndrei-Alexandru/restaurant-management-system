package com.PresentationLayer;

import com.BusinessLayer.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI implements Observer {
    @FXML
    private TextArea productTextArea;

    public ChefGUI() {
        Restaurant.getRestaurant().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        productTextArea.setText("Chef is cooking the product:");
        productTextArea.setText(productTextArea.getText() + "\n" + arg.toString());
    }

}
