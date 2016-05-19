package Graphs;

/**
 *
 * @author Esteban Salazar y David Sanchez
 */
public class Eleccion {
    private int vertice;
    private float score;
    
    public Eleccion(int vertice, float score){
        this.vertice = vertice;
        this.score = score;
        
    }
    
    public int getVertice(){
        return vertice;
    }
    
    public void setVertice(int vertice){
        this.vertice = vertice;
        
    }
    
    public float getScore(){
        return score;
    }
    
    public void setScore(float score){
        this.score = score;
    }
    
    
}
