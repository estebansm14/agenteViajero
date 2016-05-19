package agenteviajero;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import Graphs.*;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Desktop;
import java.net.*;
import java.io.IOException;

/**
 * Clase que lee un archivo en la plantilla sugerida, construye el grafo a
 * partir de este y luego invoca el metodo para encontrar el ciclo hamiltoniano
 * mas corto y luego lo representa en GoogleMaps
 *
 * @author Esteban Salazar, David Sanchez
 * @version 1
 */
public class AgenteViajero {

    /**
     * @param args contiene como primer argumento el nombre del archivo a
     * examinar
     * @version 1
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            String file;
            if (args.length > 0) {
                file = args[0];
            } else {
                file = "Medellin.txt";
            }
            Scanner reader = new Scanner(new File(file));

            GraphAL mapa = new GraphAL(0);

            String delims = "[, ]+";
            ArrayList<String> verticesL = new ArrayList<String>();
            HashMap<String, Integer> verticesMInt = new HashMap<>();
            HashMap<Integer, String> verticesMStr = new HashMap<>();
            int cont1 = 0;

            while (reader.hasNext()) {
                String str = reader.nextLine();
                if (str.startsWith("Vertices") || str.startsWith("Nodos")) {
                    str = reader.nextLine();
                    while (!str.equals("")) {
                        verticesL.add(str);
                        if (reader.hasNext()) {
                            str = reader.nextLine();
                        } else {
                            break;
                        }
                    }
                    mapa = new GraphAL(verticesL.size());
                    for (int i = 0; i < verticesL.size(); i++) {
                        String[] tokens = verticesL.get(i).split(delims);
                        verticesMInt.put(tokens[0], i);
                        verticesMStr.put(i, tokens[0]);
                        mapa.setCoordenadas(i, Float.parseFloat(tokens[2]), Float.parseFloat(tokens[1]));
                        String etiqueta = "";
                        if (tokens.length > 3) {
                            etiqueta = tokens[3];
                            for (int j = 4; j < tokens.length; j++) {
                                etiqueta = etiqueta + " " + tokens[j];
                            }
                        }
                        mapa.setEtiquetasVertices(i, etiqueta);
                    }
                } else if (str.startsWith("Arcos")) {
                    str = reader.nextLine();
                    while (!str.equals("")) {
                        String[] tokens = str.split(delims);
                        if (tokens.length > 2/*verticesMInt.get(tokens[0]) < mapa.size()*/) {
                            if (verticesMInt.get(tokens[0]) != null || verticesMInt.get(tokens[1]) != null) {
                                String etiqueta = "";
                                if (tokens.length > 3) {
                                    etiqueta = tokens[3];
                                    for (int i = 4; i < tokens.length; i++) {
                                        etiqueta = etiqueta + " " + tokens[i];
                                    }
                                }
                                cont1++;
                                mapa.addArc(verticesMInt.get(tokens[0]), verticesMInt.get(tokens[1]), Float.parseFloat(tokens[2]), etiqueta);

                            } else {
                                if (verticesMInt.get(tokens[0]) != null) {
                                    System.err.println("No esta definido el vertice '" + tokens[0] + "' en el mapa");

                                } else {
                                    System.err.println("No esta definido el vertice '" + tokens[1] + "' en el mapa");

                                }

                            }
                        } else {
                            System.err.println("La definición de arco: '" + str + "' es inválida y no ha sido añadida");
                        }

                        //mapa.setEtiquetasArcos(verticesMInt.get(tokens[0]), verticesMInt.get(tokens[1]), etiqueta);
                        if (reader.hasNext()) {
                            str = reader.nextLine();
                        } else {
                            break;
                        }
                    }
                }
            }
            ArrayList<Integer> aVisitar = puntos(mapa, "entrada.txt", verticesMStr);
            GraphAL miniGrafo = GraphAlgorithms.miniGrafo(mapa, aVisitar);
            Ciclo cicloH = GraphAlgorithms.backBranch(miniGrafo, 0);

            String URL = "https://www.google.es/maps/dir";
            for (int i = 0; i < cicloH.getRecorrido().length; i++) {
                int punto = aVisitar.get(cicloH.getRecorrido()[i]);
                URL = URL + "/" + mapa.getCoordenadas()[punto][1] + "," + mapa.getCoordenadas()[punto][0];

            }
            System.out.println(URL);
            googleMaps(URL);

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }

    }

    public static void googleMaps(String URL) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(URL));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (URISyntaxException use) {
                    use.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<Integer> puntos(Graph g, String entrada, HashMap<Integer, String> verticesMStr) {

        try {
            ArrayList<Integer> ret = new ArrayList<>();
            String delims = "[, ]+";
            Scanner reader = new Scanner(new File(entrada));
            while (reader.hasNext()) {
                String str = reader.nextLine();
                String[] tokens = str.split(delims);
                if (tokens.length == 2) {
                    ret.add(g.getVertice(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[0])));
                }
            }
            return ret;

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        return null;

    }
}
