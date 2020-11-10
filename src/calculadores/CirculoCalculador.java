package calculadores;

import java.util.ArrayList;
import java.util.List;

import model.primitivos.Circulo;
import model.primitivos.Ponto;

/**
 * classe onde realiza os calculos do circulo
 */
public class CirculoCalculador {
    public static Integer obterRaio(Ponto inicio, Ponto fim) {
        double equacao = Math.pow((fim.getx() - inicio.getx()), 2) + Math.pow((fim.gety() - inicio.gety()), 2);
        return (int) Math.floor(Math.sqrt(equacao));
    }
    //retorna os pontos dado circulo de acordo com o algoritmo midpoint
    public static List<Ponto> obterPontosAlgoritmoMidPoint(Circulo circulo) {
    	 List<Ponto> pontos = new ArrayList<>();
    	 
        if (circulo.getRaio() != 0) {

            int x = 0;
            int y = circulo.getRaio();
            double d = 5 / 4 - circulo.getRaio();   	
            gerarPontosPorSimetria(pontos, circulo.getPontoOrigem(), new Ponto(x,y));
            
            while (y > x) {
                if (d < 0) {
                    d = d + 2 * x + 3;
                    x++;
                }
                
                else {
                    d = d + 2 * (x - y) + 5;
                    x++;
                    y--;
                }
                gerarPontosPorSimetria(pontos, circulo.getPontoOrigem(), new Ponto(x,y));
            }
        } 
        
        return pontos;
    }
    
    //desenha os pontos passados pelo Bresenham para cada 1/8 do circulo
    private static void gerarPontosPorSimetria(List<Ponto> pontos, Ponto pontoCentro, Ponto pontoB) {
        
    	int x0 = (int) Math.floor(pontoCentro.getx());
    	int x = (int) Math.floor(pontoB.getx());
    	int y0 = (int) Math.floor(pontoCentro.gety());
    	int y = (int) Math.floor(pontoB.gety());
    	
        pontos.add(new Ponto(x0+x, y0+y));
        pontos.add(new Ponto(x0+y, y0+x));
        pontos.add(new Ponto(x0+y, y0-x));
        pontos.add(new Ponto(x0+x, y0-y));
        pontos.add(new Ponto(x0-x, y0-y));
        pontos.add(new Ponto(x0-y, y0-x));
        pontos.add(new Ponto(x0-y, y0+x));
        pontos.add(new Ponto(x0-x, y0+y));        
    }
}
