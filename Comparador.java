package Graphs;

import java.util.Comparator;

/**
 * @author Esteban Salazar y David Sanchez
 * Clase que compara dos vertices segun la distancia a un punto de origen
 * 
 */
public class Comparador implements Comparator<Integer>{
    private float[][] distPrev;
    /**
     * 
     * @param distPrev arreglo que en la posición [vertice][0] contiene la distancia 
     * encontrada por dijkstra
     */
    public Comparador(float[][] distPrev){
        this.distPrev = distPrev;
    }

    public int compare(Integer i1, Integer i2) {
        if(distPrev[i1][0] < distPrev[i2][0]){
            return -1;
            
        } else if(distPrev[i1][0] == distPrev[i2][0]){
            return 0;
        } else{
            return 1;
        }
        
    }

    
}
