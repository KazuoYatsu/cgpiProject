package controladores;

import calculadores.*;
import gui.Desenhador;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import primitivos.*;

import java.util.List;

public class ControladorDeEventos {

    private int iteracoesCurvaDragao;
    private final Canvas canvas;
    private TipoDesenho tipoDesenho;
    private Ponto pontoAtual;
    private WritableImage backup;
    private boolean fimElastico;
    private final Desenhador desenhador;
    private boolean transformacaoEmAndamento;
    private final double proporcao;
    
    public ControladorDeEventos(Canvas canvas) {
        this(canvas, 1.0);
    }
    
    public ControladorDeEventos(Canvas canvas, double proporcao) {
        super();
        this.proporcao = proporcao;
        this.canvas = canvas;
        this.iteracoesCurvaDragao = 0;
        fimElastico = true;
        this.desenhador = new Desenhador(this.canvas);
    }

    public boolean isTransformacaoEmAndamento() {
        return transformacaoEmAndamento;
    }

    public void setTransformacaoEmAndamento(boolean transformacaoEmAndamento) {
        this.transformacaoEmAndamento = transformacaoEmAndamento;
    }

    public Desenhador getDesenhador() {
        return desenhador;
    }

    public void setTipoDesenho(TipoDesenho tipoDesenho) {
        this.tipoDesenho = tipoDesenho;
        resetCanvas();
    }

    public void onCanvasMousePressed(MouseEvent event) {
        Ponto pontoClicado = new Ponto(event.getX() * proporcao, event.getY() * proporcao);

        if (tipoDesenho != null) {
            onCanvasMousePressedDesenho(event, pontoClicado);
        }
    }

    private void onMousePressedPrimitivosElasticos(Ponto pt) {
        if (pontoAtual == null) {
            pontoAtual = pt;
            salvarCanvas();
            fimElastico = false;
        }
    }

    private void onMousePressedPoligonosElasticos(Ponto pt) {
        if (pontoAtual == null) {
            Poligono poligono = (tipoDesenho.equals(TipoDesenho.POLIGONO_ELASTICO))
                    ? new Poligono(desenhador.getCor())
                    : new LinhaPoligonal(desenhador.getCor());
            this.desenhador.setPoligonoEmDesenho(poligono);
            pontoAtual = pt;
            fimElastico = false;
        }
        salvarCanvas();
    }

    public void onMouseDraggedPrimitivosElasticos(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (!fimElastico) {
                // Seta canvas para estado capturado quando o mouse foi pressionado
                canvas.getGraphicsContext2D().drawImage(backup, 0, 0);
                // Desenha sobre o "estado" capturado quando mouse foi pressionado
                Ponto ptFinal = new Ponto(event.getX() * proporcao, event.getY() * proporcao);

                this.desenhador.desenharPrimitivoElastico(pontoAtual, ptFinal, tipoDesenho, fimElastico);
                fimElastico = false;
            }
        }
    }

    public void onMouseReleasedPrimitivosElasticos(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (!fimElastico) {
                Ponto ptFinal = new Ponto(event.getX() * proporcao, event.getY() * proporcao);
                //Atualiza se nao estiver desenhando poligono elastico
                fimElastico = !isPoligonoElastico();
                this.desenhador.desenharPrimitivoElastico(pontoAtual, ptFinal, tipoDesenho, (fimElastico || isPoligonoElastico()));
                //Se estiver desenhando poligono elastico, precisa usar o ultimo ponto para desenhar a proxima reta
                pontoAtual = (isPoligonoElastico())
                        ? ptFinal
                        : null;
            }
        }
    }

    private void desenharCurvaDoDragao() {
        if (iteracoesCurvaDragao <= 15) {
            preencherCanvasCurvaDoDragao();
            this.iteracoesCurvaDragao += 1;
        } else {
            Alert alerta = new Alert(AlertType.WARNING, "A aplicaaoo atingiu o maximo de iteracoes possoveis.",
                    ButtonType.FINISH);
            alerta.show();
        }
    }

    private void preencherCanvasCurvaDoDragao() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Reta reta = new Reta(new Ponto(150, 400), new Ponto(600, 400), this.desenhador.getCor());
        CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
        List<Reta> retasCurvaDragao;

        try {
            retasCurvaDragao = calc.getRetasCurva();
            for (Reta retaCalc : retasCurvaDragao) {
                this.desenhador.desenharPontos(RetaCalculador.obterPontos(retaCalc), this.desenhador.getCor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCanvasMousePressedDesenho(MouseEvent event, Ponto pontoClicado) {
        if (event.getButton() == MouseButton.PRIMARY) {
            //Definir qual desenho sera feito
            switch (tipoDesenho) {
                case CURVA_DO_DRAGAO:
                    desenharCurvaDoDragao();
                    break;
                case RETA_ELASTICA:
                case CIRCULO_ELASTICO:
                case RETANGULO_ELASTICO:
                    onMousePressedPrimitivosElasticos(pontoClicado);
                case POLIGONO_ELASTICO:
                case RETA_POLIGONAL:
                    onMousePressedPoligonosElasticos(pontoClicado);
                    break;
            }
        } else if (event.getButton() == MouseButton.SECONDARY && this.desenhador.isPoligonoElasticoEmDesenho()) {
            // Captura clique com o botao secundario do mouse quando usuario esta desenhando poligonos
            switch (tipoDesenho) {
                case POLIGONO_ELASTICO:
                    Ponto ptInicio = this.desenhador.getPoligonoEmDesenho().getRetas().get(0).getA();
                    this.desenhador.desenharPoligono(pontoAtual, ptInicio, true);
                    this.desenhador.salvarPoligonoDesenhado(TipoPrimitivo.POLIGONO);
                    break;
                case RETA_POLIGONAL:
                    this.desenhador.salvarPoligonoDesenhado(TipoPrimitivo.LINHA_POLIGONAL);
                    break;
            }
            resetCanvas();
            resetPoligonoEmDesenho();
        }
    }

    public void limparCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.desenhador.inicilizarEstruturasManipulacaoDeDesenhos();
        resetCanvas();
        resetPoligonoEmDesenho();
    }

    private void salvarCanvas() {
        // Capturando estado do canvas para desenhar sobre ele
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITE);
        backup = canvas.snapshot(params, backup);
    }

    public void resetCanvas() {
        fimElastico = true;
        pontoAtual = null;
        iteracoesCurvaDragao = 0;
        setTransformacaoEmAndamento(false);
    }

    public void resetPoligonoEmDesenho() {
        this.desenhador.setPoligonoEmDesenho(null);
    }

    private Boolean isPoligonoElastico() {
        return tipoDesenho.equals(TipoDesenho.POLIGONO_ELASTICO) || (tipoDesenho.equals(TipoDesenho.RETA_POLIGONAL));
    }

    public void desfazerSelecao() {
        this.desenhador.limparObjetosSelecionados();
    }
}
