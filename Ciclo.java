package Graphs;

/**
 * Clase que define el objeto ciclo que contiene como atributos un array de 
 * enteros correspondiente al recorrido y un entero correspondiente a la distancia
 * @author Esteban Salazar, David Sanchez
 * @version 1
 */
public class Ciclo {

    private int[] recorrido;
    private float distancia;

    public Ciclo(int[] recorrido, float distancia) {
        this.recorrido = recorrido;
        this.distancia = distancia;
    }

    public void setRecorrido(int[] recorrido) {
        this.recorrido = recorrido;
    }

    public int[] getRecorrido() {
        return recorrido;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public float getDistancia() {
        return distancia;
    }

}
