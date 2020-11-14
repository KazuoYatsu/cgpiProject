package gui;

import calculadores.*;
import controladores.TipoDesenho;
import controladores.TipoPrimitivo;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import primitivos.*;

import java.util.*;

public class Desenhador {

    private Color cor;
    private int diametro;
    private final Canvas canvas;
    private Poligono poligonoEmDesenho;
    private Map<TipoPrimitivo, List<Object>> objetosDesenhados;
    private Map<TipoPrimitivo, List<Integer>> indicesSelecionados;

    public Desenhador(Canvas canvas) {
        this.diametro = 3;
        this.cor = Color.BLACK;
        this.canvas = canvas;
        this.inicilizarEstruturasManipulacaoDeDesenhos();
    }

    public Map<TipoPrimitivo, List<Object>> getObjetosDesenhados() {
        return objetosDesenhados;
    }

    public void setObjetosDesenhados(Map<TipoPrimitivo, List<Object>> objetosDesenhados) {
        this.objetosDesenhados = objetosDesenhados;
    }

    public Map<TipoPrimitivo, List<Integer>> getIndicesObjetosSelecionados() {
        return indicesSelecionados;
    }


    public Poligono getPoligonoEmDesenho() {
        return poligonoEmDesenho;
    }

    public void setPoligonoEmDesenho(Poligono poligonoEmDesenho) {
        this.poligonoEmDesenho = poligonoEmDesenho;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    public void inicilizarEstruturasManipulacaoDeDesenhos() {
        objetosDesenhados = new HashMap<>();
        indicesSelecionados = new HashMap<>();
        TipoPrimitivo[] listEnum = TipoPrimitivo.values();

        for (TipoPrimitivo tipoPrimitivo : listEnum) {
            objetosDesenhados.put(tipoPrimitivo, new ArrayList<>());
            indicesSelecionados.put(tipoPrimitivo, new ArrayList<>());
        }
    }

    public void desenharPrimitivoElastico(Ponto pontoInicial, Ponto pontoFinal, TipoDesenho tipoDesenho, boolean salvar) {
        switch (tipoDesenho) {
            case RETA_ELASTICA:
                desenharReta(pontoInicial, pontoFinal, salvar);
                break;
            case CIRCULO_ELASTICO:
                desenharCirculo(pontoInicial, pontoFinal, salvar);
                break;
            case POLIGONO_ELASTICO:
            case RETA_POLIGONAL:
                desenharPoligono(pontoInicial, pontoFinal, salvar);
                break;
            case RETANGULO_ELASTICO:
                desenharRetangulo(pontoInicial, pontoFinal, salvar);
                break;
        }
    }

    public void desenharReta(Ponto pontoInicial, Ponto pontoFinal, boolean salvar) {
        Reta reta = new Reta(pontoInicial, pontoFinal, cor);
        desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta), cor);
        if (salvar) salvarPrimitivoDesenhado(TipoPrimitivo.RETA, reta);
    }

    public void desenharCirculo(Ponto pontoInicial, Ponto pontoFinal, boolean salvar) {
        Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoInicial, pontoFinal);
        int raio = CirculoCalculador.obterRaio(pontoMedio, pontoFinal);
        Circulo circulo = new Circulo(raio, pontoMedio, cor);
        desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo), cor);
        if (salvar) salvarPrimitivoDesenhado(TipoPrimitivo.CIRCULO, circulo);
    }

    public void desenharRetangulo(Ponto pontoInicial, Ponto pontoFinal, boolean salvar) {
        Retangulo retangulo = new Retangulo(pontoInicial, pontoFinal, cor);
        desenharPontos(RetanguloCalculador.obterPontos(retangulo), cor);
        if (salvar) salvarPrimitivoDesenhado(TipoPrimitivo.RETANGULO, retangulo);
    }

    public void desenharPoligono(Ponto pontoInicial, Ponto pontoFinal, boolean addReta) {
        Reta reta = new Reta(pontoInicial, pontoFinal, cor);
        if (addReta) {
            poligonoEmDesenho.addReta(reta);
        } else {
            // Corrigindo desenho de retas no dragged
            desenharReta(reta.getA(), reta.getB(), false);
        }
        desenharPontos(PoligonoCalculador.obterPontos(poligonoEmDesenho), cor);
    }

    public void desenharPontos(List<Ponto> pontos, Color cor) {
        for (Ponto p : pontos) {
            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "", cor);
        }
    }

    public void desenharPonto(int x, int y) {
        this.desenharPonto(x, y, "", cor);
    }

    public void desenharPonto(int x, int y, String nome, Color cor) {
        PontoGr p;
        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);
        // Desenha o ponto
        p.desenharPonto(canvas.getGraphicsContext2D());
    }

    public void salvarPrimitivoDesenhado(TipoPrimitivo tipoPrimitivo, Object primitivo) {
        objetosDesenhados.get(tipoPrimitivo).add(primitivo);
    }

    public void salvarPoligonoDesenhado(TipoPrimitivo tipoPrimitivo) {
        objetosDesenhados.get(tipoPrimitivo).add(this.poligonoEmDesenho);
    }

    public void desenharObjetosArmazenados(Color novaCor) {
        // apaga canvas
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //desenha todos os objetos
        objetosDesenhados.forEach((tipoPrimitivo, objetos) -> {
            for (Object desenho : objetos) {
                //verifica se objeto esta selecionado
                boolean selecionado = this.indicesSelecionados.get(tipoPrimitivo).contains(objetos.indexOf(desenho));

                Color cor;

                switch (tipoPrimitivo) {
                    case RETA:
                        Reta reta = (Reta) desenho;
                        cor = (selecionado) ? novaCor : reta.getCor();
                        this.desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta), cor);
                        break;
                    case RETANGULO:
                        Retangulo retangulo = (Retangulo) desenho;
                        cor = (selecionado) ? novaCor : retangulo.getCor();
                        this.desenharPontos(RetanguloCalculador.obterPontos(retangulo), cor);
                        break;
                    case POLIGONO:
                    case LINHA_POLIGONAL:
                        Poligono poligono = (Poligono) desenho;
                        cor = (selecionado) ? novaCor : poligono.getCor();
                        this.desenharPontos(PoligonoCalculador.obterPontos(poligono), cor);
                        break;
                    case CIRCULO:
                        Circulo circulo = (Circulo) desenho;
                        cor = (selecionado) ? novaCor : circulo.getCor();
                        desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo), cor);
                        break;
                }
            }
        });
    }

    public void limparObjetosSelecionados() {
        indicesSelecionados.forEach((tipoPrimitivo, indices) -> indices.clear());
        desenharObjetosArmazenados(Color.WHITE);
    }

    public boolean isPoligonoElasticoEmDesenho() {
        return (poligonoEmDesenho != null && poligonoEmDesenho.getRetas().size() >= 2);
    }
}
