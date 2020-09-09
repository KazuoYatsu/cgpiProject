/**
 *
 */
package primitivos;

public class Reta {

    Ponto a; // ponto inicial
    Ponto b; //ponto final
    Double coeficienteAngular; //inclinacao

    public Reta(Ponto a, Ponto b) { // cria a reta
        this.a = a; // atribuindo as variaveis da classe
        this.b = b; // `
        this.calcularCoeficienteAngular(a, b);//chama a funcao para calcular o coeficiente angular
    }

    private void calcularCoeficienteAngular(Ponto a, Ponto b) {
        // TODO: Implementar : a.getx() - b.getx() != 0
        this.coeficienteAngular = (a.gety() - b.gety()) / (a.getx() - b.getx()); //formula do coeficiente angular
    }

// geters e seters
    public Ponto getA() {
        return a;
    }

    public void setA(Ponto a) {
        this.a = a;
        this.calcularCoeficienteAngular(this.a, this.b);
    }

    public Ponto getB() {
        return b;
    }

    public void setB(Ponto b) {
        this.b = b;
        this.calcularCoeficienteAngular(this.a, this.b);
    }

    public Double getCoeficienteAngular() {
        return coeficienteAngular;
    }

    public void setCoeficienteAngular(Double coeficienteAngular) {
        this.coeficienteAngular = coeficienteAngular;
    }
}
