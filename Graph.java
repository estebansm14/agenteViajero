package Graphs;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Abstract class for implementations of Digraphs
 *
 * @author Esteban Salazar, David Sanchez
 * @version 1
 */
public abstract class Graph {

    private int size;
    private String[] etiquetasVertices;

    private float[][] coordenadas;

    public Graph(int vertices) {
        size = vertices;
        etiquetasVertices = new String[size];



        coordenadas = new float[size][2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 2; j++) {
                coordenadas[i][j] = -1;
            }
        }
    }

    public abstract void addArc(int source, int destination, float weight, String etiqueta);

    public abstract ArrayList<Integer> getSuccessors(int vertice);

    public ArrayList<Integer> sucGeoSort(int vertice) {
        ArrayList<Integer> sucesoresGeo = this.getSuccessors(vertice);
        quickSortGeo(sucesoresGeo, 0, sucesoresGeo.size() - 1, vertice);
        return sucesoresGeo;
    }

    private void quickSortGeo(ArrayList<Integer> arreglo, int lo, int hi, int source) {
        if (lo < hi) {
            int p = partitionGeo(arreglo, lo, hi, source);
            quickSortGeo(arreglo, lo, p - 1, source);
            quickSortGeo(arreglo, p + 1, hi, source);
        }
    }

    private int partitionGeo(ArrayList<Integer> arreglo, int lo, int hi, int source) {
        int pivote = arreglo.get(hi);
        float distPivote = getDistancia(source, pivote);
        int i = lo;
        for (int j = lo; j < hi ; j++) {
            if (this.getDistancia(source, arreglo.get(j)) < distPivote) {
                swap(arreglo, i, j);
                i++;
            }
        }
        swap(arreglo, i, hi);
        return i;
    }
    
    public ArrayList<Integer> sucGeoSortDest(int vertice, int dest) {
        ArrayList<Integer> sucesoresGeoDest = this.getSuccessors(vertice);
        quickSortGeo(sucesoresGeoDest, 0, sucesoresGeoDest.size() - 1, dest);
        return sucesoresGeoDest;
    }
    
    
    public ArrayList<Integer> sucWeightSort(int vertice) {
        ArrayList<Integer> sucesoresWeight = this.getSuccessors(vertice);
        quickSortWeight(sucesoresWeight, 0, sucesoresWeight.size() - 1, vertice);
        return sucesoresWeight;
    }

    private void quickSortWeight(ArrayList<Integer> arreglo, int lo, int hi, int source) {
        if (lo < hi) {
            int p = partitionWeight(arreglo, lo, hi, source);
            quickSortWeight(arreglo, lo, p - 1, source);
            quickSortWeight(arreglo, p + 1, hi, source);
        }
    }

    private int partitionWeight(ArrayList<Integer> arreglo, int lo, int hi, int source) {
        int pivote = arreglo.get(hi);
        float weightPivote = getWeight(source, pivote);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (this.getWeight(source, arreglo.get(j)) < weightPivote) {
                swap(arreglo, i, j);
                i++;
            }
        }
        swap(arreglo, i, hi);
        return i;
    }

    private void swap(ArrayList<Integer> arreglo, int p, int q) {
        int valorP = arreglo.get(p);
        int valorQ = arreglo.get(q);
        arreglo.remove(p);
        arreglo.add(p, valorQ);
        arreglo.remove(q);
        arreglo.add(q, valorP);
    }


    public abstract float getWeight(int source, int destination);

    public float getDistancia(int source, int destination) {
        float difx = coordenadas[destination][0] - coordenadas[source][0];
        float dify = coordenadas[destination][1] - coordenadas[source][1];

        return (float) Math.sqrt(Math.pow(difx, 2) + Math.pow(dify, 2));
    }

    public int size() {
        return size;
    }

    public void setEtiquetasVertices(int vertice, String etiqueta) {
        etiquetasVertices[vertice] = etiqueta;
    }

    public String[] getEtiquetasVertices() {
        return etiquetasVertices;
    }

    public abstract void setEtiquetasArcos(int source, int destination, String etiqueta);

    public abstract String getEtiquetasArcos(int source, int destination);

    public void setCoordenadas(int vertice, float x, float y) {
        coordenadas[vertice][0] = x;
        coordenadas[vertice][1] = y;
    }

    public float[][] getCoordenadas() {
        return coordenadas;
    }
    
    public int getVertice(float coordenadaX, float coordenadaY){
        for(int i = 0; i < coordenadas.length; i++){
            if(coordenadas[i][0] == coordenadaX && coordenadas[i][1] == coordenadaY){
                return i;
            }
        }
        return -1;
    }

}
