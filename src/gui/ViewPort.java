package gui;

import controladores.ControladorDeEventos;
import controladores.TipoDesenho;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import primitivos.*;
import utils.AlertaPersonalizado;
import utils.Figura;
import utils.XMLParser;

import java.io.File;

public class ViewPort {

    private final Stage palco;

    private Canvas canvas;
    private ControladorDeEventos controladorDeEventos;

    public static int LARGURA_CANVAS = 1000;
    public static int ALTURA_CANVAS = 700;


    public ViewPort(Stage palco, double proporcao) {
        this.palco = palco;
        desenharTela(proporcao);
    }

    public void desenharTela(double proporcao) {
        palco.setWidth(LARGURA_CANVAS * proporcao);
        palco.setHeight(ALTURA_CANVAS * proporcao);
        palco.setX(0);
        palco.setY(0);
        palco.setResizable(false);

        //criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
        controladorDeEventos = new ControladorDeEventos(canvas, proporcao);

        // Painel para os componentes
        BorderPane pane = new BorderPane();

        // atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho


        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
    }

    public ControladorDeEventos getControladorDeEventos() {
        return controladorDeEventos;
    }
}
