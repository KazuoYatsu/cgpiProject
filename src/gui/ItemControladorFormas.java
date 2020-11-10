package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ItemControladorFormas extends Button {
        ItemControladorFormas(String texto, EventHandler<ActionEvent> value) {
        setText(texto);
        setOnAction(value);
    }
}
