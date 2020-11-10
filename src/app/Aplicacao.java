package app;

import gui.TelaPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

public class Aplicacao extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) {
        final Stage viewport = new Stage();
        viewport.setX(0);
        viewport.setY(0);
        final TelaPrincipal mapeamento = new TelaPrincipal(viewport, 0.5);
        new TelaPrincipal(palco, 1, mapeamento);
    }
}
