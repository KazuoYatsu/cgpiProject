package gui;
// imports necessarios
import java.util.List;

import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
import calculadores.RetaCalculador;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.primitivos.Circulo;
import model.primitivos.Ponto;
import model.primitivos.PontoGr;
import model.primitivos.Reta;

/**
 * Classe responsavel por controlar todos os eventos da interface de usuario.
 */
public class ControladorDeEventos {
	// Declaracao das variaveis de estado
	private int iteracoesCurvaDragao;
	private final Canvas canvas;
    private TipoDesenho tipoDesenho;
	private Ponto pontoAtual;
    private Color cor;
    private int diametro;
    private final double proporcao;

    //metodo construtor da classe
	public ControladorDeEventos(Canvas canvas, double proporcao) { // O canvas é objeto onde é colocado os desenhos
		super(); //setando os valores iniciais das variaveis de estado
		this.canvas = canvas;
		this.iteracoesCurvaDragao = 0;
		this.diametro = 3;
		this.proporcao = proporcao;
	}

	//funcao que lida com o clic do mouse
	public void onCanvasMousePressed(MouseEvent event) {
		//se for o click primario do botao e o tipo de desenho nao for nulo
		if (event.getButton() == MouseButton.PRIMARY && tipoDesenho != null) {
			if (tipoDesenho.equals(TipoDesenho.CURVA_DO_DRAGAO)) { //se o ripo do desenho for curva do dragao
				if (iteracoesCurvaDragao <= 15) { // se a qtd de iteracoes for menor ou = a 15
					preencherCanvasCurvaDoDragao(); // redesenha a tela  c a proxima iteracao
					this.iteracoesCurvaDragao += 1; // incrementa
				} else { //se n mostra mensagem de erro
					Alert alerta = new Alert(AlertType.WARNING, "A aplicação atingiu o máximo de iterações possíveis.", ButtonType.FINISH);
					alerta.show();
				}
			} else if (tipoDesenho.equals(TipoDesenho.PONTO)) { // se o tipo for ponto
				desenharPonto((int) Math.floor(event.getX() * proporcao), (int) Math.floor(event.getY() * proporcao), "");// desenha o ponto d acordo com os valores inteiros do x e y
			} else if (tipoDesenho.equals(TipoDesenho.RETAS_CIRCULOS)) { //se for retas e circulos
				desenharRetasCirculos(); // desenho automatico
			} else {// se nao desenha os tipos model.primitivos
				preencherCanvasPrimitivosBasicos(new Ponto(event.getX() * proporcao, event.getY() * proporcao));
			}
		}
	}

	public void limparCanvas() { // limpa tela
		limparCanvas(true);
	}
	// limpa o canvas e dependendo da parametrizacao  reseta o contador da curva do dragao
	public void limparCanvas(boolean resetCurvaDragao) {
		this.canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
		if(resetCurvaDragao) {
			this.iteracoesCurvaDragao = 0;
		}
	}
	// desenha os desenhos na tela
	private void desenharRetasCirculos() {
		limparCanvas();
		int centro = (int) (325 * proporcao);
		int raio = (int) (150 * proporcao);
		double calculoCirculos = 1.15;
		double calculoReta = 1.75;
		double calculoRetaDiagonalX = 1.5;
		double calculoRetaDiagonalY = 0.9;
		Color color = this.cor;

		// desenha os circulos
		setCor(Color.GREEN);
		desenharCirculo(new Ponto(centro, centro));
		desenharCirculo(new Ponto(centro + raio, centro));

		desenharCirculo(new Ponto(centro + raio, centro));
		desenharCirculo(new Ponto(centro, centro));

		desenharCirculo(new Ponto(centro - raio, centro));
		desenharCirculo(new Ponto(centro, centro));

		desenharCirculo(new Ponto(centro + raio / 2, centro + raio / calculoCirculos));
		desenharCirculo(new Ponto(centro, centro));

		desenharCirculo(new Ponto(centro + raio / 2, centro - raio / calculoCirculos));
		desenharCirculo(new Ponto(centro, centro));

		desenharCirculo(new Ponto(centro - raio / 2, centro + raio / calculoCirculos));
		desenharCirculo(new Ponto(centro, centro));

		desenharCirculo(new Ponto(centro - raio / 2, centro - raio / calculoCirculos));
		desenharCirculo(new Ponto(centro, centro));

		//cria os pontos que vao ser utilizados para os desenhos das retas
		setCor(Color.RED);
		Ponto inferior = new Ponto(centro, centro + raio * calculoReta);
		Ponto superior = new Ponto(centro, centro - raio * calculoReta);
		Ponto superiorDireito = new Ponto(centro + raio * calculoRetaDiagonalX, centro - raio * calculoRetaDiagonalY);
		Ponto inferiorDireito = new Ponto(centro + raio * calculoRetaDiagonalX, centro + raio * calculoRetaDiagonalY);
		Ponto superiorEsquerdo = new Ponto(centro - raio * calculoRetaDiagonalX, centro - raio * calculoRetaDiagonalY);
		Ponto inferiorEsquerdo = new Ponto(centro - raio * calculoRetaDiagonalX, centro + raio * calculoRetaDiagonalY);

		// desenha as retas
		desenharReta(superior);
		desenharReta(inferior);

		desenharReta(inferiorEsquerdo);
		desenharReta(superiorDireito);

		desenharReta(inferiorDireito);
		desenharReta(superiorEsquerdo);

		// --------
		desenharReta(superior);
		desenharReta(superiorDireito);

		desenharReta(superior);
		desenharReta(superiorEsquerdo);

		desenharReta(superior);
		desenharReta(inferiorDireito);

		desenharReta(superior);
		desenharReta(inferiorEsquerdo);


		// --------
		desenharReta(inferior);
		desenharReta(superiorDireito);

		desenharReta(inferior);
		desenharReta(superiorEsquerdo);

		desenharReta(inferior);
		desenharReta(inferiorDireito);

		desenharReta(inferior);
		desenharReta(inferiorEsquerdo);


		// --------

		desenharReta(inferiorDireito);
		desenharReta(inferiorEsquerdo);

		desenharReta(superiorDireito);
		desenharReta(superiorEsquerdo);

		desenharReta(superiorDireito);
		desenharReta(inferiorDireito);

		desenharReta(superiorEsquerdo);
		desenharReta(inferiorEsquerdo);

		setCor(color);
	}
	//metodo que preenche a tela com a curva do dragao
	private void preencherCanvasCurvaDoDragao() {
		limparCanvas(false);

		Reta reta = new Reta(new Ponto(150 * proporcao,400 * proporcao), new Ponto(600 * proporcao,400 * proporcao));
		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
		List<Reta> retasCurvaDragao = calc.getRetasCurva();

		for (Reta retaCalc : retasCurvaDragao){
			desenharPontos(RetaCalculador.obterPontos(retaCalc));
		}
	}
	//desenha os tipos model.primitivos no canvas baseados no tipo desenhos selecionados
	private void preencherCanvasPrimitivosBasicos(Ponto pt){
		switch(tipoDesenho){
			case RETA:
				desenharReta(pt);
	            break;
			case CIRCULO:
				desenharCirculo(pt);
				break;
			default:
				throw new RuntimeException("Erro interno");//faz dar erro
		}	
	}
	//metodo utilizado para desenhar reta
	private void desenharReta(Ponto pontoFinal){
		if (pontoAtual == null){
			pontoAtual = pontoFinal;
		}else{
			Reta reta = new Reta(pontoAtual, pontoFinal);
			desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta));
            pontoAtual = null;
		}
	}
	// metodo utilizado para desenhar circulo
	private void desenharCirculo(Ponto pontoFinal){
		if (pontoAtual == null){
			pontoAtual = pontoFinal;
		}else{
			int raio = CirculoCalculador.obterRaio(pontoAtual, pontoFinal);
			Circulo circulo = new Circulo(raio, pontoAtual);
			desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo));
			pontoAtual = null;
		}
	}
	//metodo utilizado para desenhar todos os pontos de um array
    private void desenharPontos( List<Ponto> pontos) {
        for (Ponto p : pontos) {
            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "");
        }
    }
     //metodo utilizado para desenhar um ponto em especifico de acordo com as coordenadas
    private void desenharPonto(int x, int y, String nome) {
        PontoGr p;
        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);
        // Desenha o ponto
        p.desenharPonto(canvas.getGraphicsContext2D());
    }


	//setters

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void setDiametro(int diametro) {
		this.diametro = (int) (diametro * proporcao);
	}
}
