package transformadoresGeometricos;

import java.util.ArrayList;
import java.util.List;

import primitivos.Ponto;

public class Escala {

	private Ponto pontoReferencia;
	private int Sx;
	private int Sy;
	private List<Ponto> pontosDaFigura;

	public Escala(int sx, int sy, List<Ponto> pontosDaFigura) {
		this.pontoReferencia = new Ponto(0,0);
		Sx = sx;
		Sy = sy;
		this.pontosDaFigura = pontosDaFigura;
	}

	public Escala(Ponto pontoReferencia, int sx, int sy, List<Ponto> pontosDaFigura) {
		this.pontoReferencia = pontoReferencia;
		Sx = sx;
		Sy = sy;
		this.pontosDaFigura = pontosDaFigura;
	}

	public List<Ponto> getPontosDaFigura() {
		return pontosDaFigura;
	}

	public void setPontosDaFigura(List<Ponto> pontosDaFigura) {
		this.pontosDaFigura = pontosDaFigura;
	}

	public Ponto getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(Ponto pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public int getSx() {
		return Sx;
	}

	public void setSx(int sx) {
		Sx = sx;
	}

	public int getSy() {
		return Sy;
	}

	public void setSy(int sy) {
		Sy = sy;
	}
	
	public List<Ponto> aplicarRotacao(){
		List<Ponto> pontosTransladados = new ArrayList<>();
		this.pontosDaFigura.forEach(pt -> {	
//			Defini��o matem�tica
//			x�= x.Sx + xf(1-Sx)
//			y�= y.Sy + yf(1-Sy)
			double x = (pt.getx()*Sx)+(pontoReferencia.getx()*(1-Sx));
			double y = (pt.gety()*Sy)+(pontoReferencia.gety()*(1-Sy));
			pontosTransladados.add(new Ponto(x,y));
		});
		return pontosTransladados;
	}
	
	

	
}
