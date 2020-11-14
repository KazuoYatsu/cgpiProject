package calculadores;

import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.Reta;

import java.util.ArrayList;
import java.util.List;

public class PoligonoCalculador {

    public static List<Ponto> obterPontos(Poligono poligono) {

        List<Ponto> pontos = new ArrayList<>();

        for (Reta reta : poligono.getRetas()) {
            pontos.addAll(RetaCalculador.obterPontos(reta));
        }

        return pontos;

    }

    public static double calcularDistanciaPontoRetasPoligono(Ponto pt, Poligono poligono) {
        double distancia = 100000;
        // verifica qual reta mais proxima do ponto
        for (Reta reta : poligono.getRetas()) {
            double distanciaPontoRetaAtual = (RetaCalculador.calcularDistanciaPontoReta(pt, reta));
            distancia = Math.min(distanciaPontoRetaAtual, distancia);
        }
        return distancia;
    }
}
