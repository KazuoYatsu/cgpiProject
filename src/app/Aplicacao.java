package app;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.PontoComMouseGui2;

public class Aplicacao extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) { //aqui para criar tela
        PontoComMouseGui2 gui = new PontoComMouseGui2(palco);
        //retas

        //diagonal decrescente p1 p2
        gui.primeiroClick(15,15);
        gui.segundoClickLinha(100,150);

        // diagonal decrescente p2 p1
        gui.primeiroClick(200,150);
        gui.segundoClickLinha(115,15);

        //diagonal crescente p1 p2
        gui.primeiroClick(315,150);
        gui.segundoClickLinha(400,15);

        // diagonal crescente p2 p1
        gui.primeiroClick(500,15);
        gui.segundoClickLinha(415,150);

        //horizontal p1 p2
        gui.primeiroClick(15,215);
        gui.segundoClickLinha(200,215);

        // horizontal p2 p1
        gui.primeiroClick(200,315);
        gui.segundoClickLinha(15,315);

        //vertical p1 p2
        gui.primeiroClick(300,215);
        gui.segundoClickLinha(300,350);

        // vertical p2 p1
        gui.primeiroClick(400,350);
        gui.segundoClickLinha(400,215);

        //circulos
        int y = 550;
        int centro = 200;
        int r = 40;
        gui.primeiroClick(centro, y);
        gui.segundoClickCirculo(centro + r, y);

        r = 60;
        gui.primeiroClick(centro, y);
        gui.segundoClickCirculo(centro + r, y);

        r = 80;
        gui.primeiroClick(centro, y);
        gui.segundoClickCirculo(centro + r, y);

        r = 100;
        gui.primeiroClick(centro, y);
        gui.segundoClickCirculo(centro + r, y);

        r = 120;
        gui.primeiroClick(centro, y);
        gui.segundoClickCirculo(centro + r, y);
    }
}

