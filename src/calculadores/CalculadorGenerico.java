package calculadores;

import primitivos.Ponto;

public class CalculadorGenerico {

    public static Ponto obterPontoMedio(Ponto pontoInicial, Ponto pontoFinal) {
        int xMedio = (int) ((pontoFinal.getx() + pontoInicial.getx()) / 2);
        int yMedio = (int) ((pontoFinal.gety() + pontoInicial.gety()) / 2);
        return new Ponto(xMedio, yMedio);
    }

    public static double obterDistanciaEntreDoisPontos(Ponto pontoA, Ponto pontoB) {
        /*
         * Formula da distancia entre dois pontos
         * dAB = sqtr( ((x2 - x1) ^2 ) + ((y2 - y1) ^2 ) )
         */
        double deltaX = pontoB.getx() - pontoA.getx();
        double deltaY = pontoB.gety() - pontoA.gety();
        return Math.sqrt((Math.pow(deltaX, 2)) + (Math.pow(deltaY, 2)));
    }
}
