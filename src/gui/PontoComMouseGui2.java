package gui;

import java.util.ArrayList;
import java.util.List;

import calculadores.CirculoCalculador;
import calculadores.RetaCalculador;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import primitivos.Circulo;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;

public class PontoComMouseGui2 {
    // componente para desenhar graficos , contexto grafico
    GraphicsContext gc;
    boolean circulo = false; // controle da forma geometrica

    Ponto pontoSelecionado = null;

    public PontoComMouseGui2(Stage palco) {

        // define titulo da janela
        palco.setTitle("Paint");

        // define largura e altura da janela
        palco.setWidth(600);
        palco.setHeight(800);

        // Painel para os componentes
        BorderPane pane = new BorderPane();

        // componente para desenho
        Canvas canvas = new Canvas(500, 800);

        gc = canvas.getGraphicsContext2D();

        // toda vez que o mouse mexer, chama esse evento (larguraXaltura) posicao nos eixos
        canvas.setOnMouseMoved(event -> {
            palco.setTitle("Paint" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
        });

        //evento quando clica com o mouse.
        canvas.setOnMousePressed(event -> {
            int x, y;

            if (event.getButton() == MouseButton.PRIMARY) {    //Pega o botao q ta no evento e compara ao click direito
                x = (int) event.getX(); // pega o valor de x
                y = (int) event.getY(); // pega o valor de Y
                if (pontoSelecionado == null) { // se o ponto selecionado for vazio (primeiro click)
                    primeiroClick(x, y);
                } else if(circulo) { //se circulo for true ele desenha
                    segundoClickCirculo(x, y);
                } else { // segundo clic  - se nao desenha a reta
                    segundoClickLinha(x,y);
                }
            }
        });

        // atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho

        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
    }

    public void primeiroClick(int x, int y) {
        pontoSelecionado = new Ponto(x, y); // cria um novo ponto (atribui valor a variavel ponto selecionad)
    }

    public void segundoClickCirculo(int x, int y) {
        Ponto pontoFinal = new Ponto(x,y); // cria ponto final (a partir do 2 click)
        Ponto pontoMedio = pontoSelecionado; // pega o primeiro click e usa como centro do circulo
        CirculoCalculador calc = new CirculoCalculador(); // instancia calc (para fazer o calculo da circ.)
        int calcRaio = calc.obterRaio(pontoMedio, pontoFinal); // calcula o raio com ponto medio e final.
        Circulo circulo = new Circulo(calcRaio, pontoMedio); // instancia um circulo com raio e o centro (estilo compasso)
        List<Ponto> pontos = calc.obterPontos(circulo); //todos os pontos necessarios para desenhar na tela.
        this.pontoSelecionado = null; // nulo para reconhecer o novo primeiro ponto
        desenharPontos(gc, pontos); // Desenha os pontos utilizando o contexto grafico.
    }

    public void segundoClickLinha(int x, int y) {
        Reta reta = new Reta(pontoSelecionado, new Ponto(x, y)); //instanciando (primeiro clic e segundo para saber como montar a reta
        desenharPontos(gc, RetaCalculador.obterPontos(reta)); //desenhar os pontos no contexto grafico usando os pontos q elee obteve da funcao obterpontos
        pontoSelecionado = null; // zera de novo
    }

    private Ponto obterPontoMedio(Ponto pontoFinal) {
        Ponto pontoInicial = this.pontoSelecionado;
        int xMedio = (int) ((pontoFinal.getx() + pontoInicial.getx()) / 2);
        int yMedio = (int) ((pontoFinal.gety() + pontoInicial.gety()) / 2);
        Ponto pontoMedio = new Ponto(xMedio, yMedio);
        return pontoMedio;
    }

    private void desenharPontos(GraphicsContext gc, List<Ponto> pontos) {
        for (Ponto p : pontos) {
            desenharPonto(gc, (int) Math.floor(p.getx()), (int) Math.floor(p.gety()), 6, "", Color.BLACK);
        }
    }

    /**
     * Desenha um ponto grafico
     *
     * @param g        contexto grafico
     * @param x        posicao x
     * @param y        posicao y
     * @param diametro diametro do ponto
     * @param nome     nome do ponto
     * @param cor      cor do ponto
     */

    //
    public void desenharPonto(GraphicsContext g, int x, int y, int diametro, String nome, Color cor) {
        PontoGr p;

        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);

        // Desenha o ponto
        p.desenharPonto(g);
    }
}
