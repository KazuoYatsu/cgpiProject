package utils;

import controladores.TipoPrimitivo;
import primitivos.Poligono;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "Figura")
public class Figura implements Serializable {

    @XmlAnyElement(lax = true)
    public List<Object> todosObjetosDesenhados;

    public void setObjetosDesenhados(Map<TipoPrimitivo, List<Object>> objetosDesenhados) {
        this.todosObjetosDesenhados = new ArrayList<>();
        objetosDesenhados.forEach((key, value1) -> this.todosObjetosDesenhados.addAll(value1));
    }

    @XmlTransient
    public Map<TipoPrimitivo, List<Object>> getObjetosDesenhados() {
        Map<TipoPrimitivo, List<Object>> objetos = new HashMap<>();
        TipoPrimitivo[] listEnum = TipoPrimitivo.values();

        for (TipoPrimitivo tipoPrimitivo : listEnum) {
            objetos.put(tipoPrimitivo, new ArrayList<>());
        }

        this.todosObjetosDesenhados.forEach((obj) -> {
            switch (obj.getClass().getSimpleName()) {
                case "Reta":
                    objetos.get(TipoPrimitivo.RETA).add(obj);
                    break;
                case "Poligono":
                    ((Poligono) obj).adicionarUltimaReta();
                    objetos.get(TipoPrimitivo.POLIGONO).add(obj);
                    break;
                case "LinhaPoligonal":
                    objetos.get(TipoPrimitivo.LINHA_POLIGONAL).add(obj);
                    break;
                case "Retangulo":
                    objetos.get(TipoPrimitivo.RETANGULO).add(obj);
                    break;
                case "Circulo":
                    objetos.get(TipoPrimitivo.CIRCULO).add(obj);
                    break;
            }
        });

        return objetos;
    }
}
