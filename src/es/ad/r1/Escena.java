package es.ad.r1;
import java.io.Serializable;
import java.util.*;

/**
 * Representa una escena gráfica compuesta por figuras geométricas.
 * 
 * Permite definir el tamaño de la escena (ancho y alto) y gestionar una lista de figuras.
 * Es serializable para poder guardarse y recuperarse en disco.
 */
public class Escena implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Ancho de la escena en píxeles */
    private int width;
    /** Alto de la escena en píxeles */
    private int height;
    /** Lista de figuras que forman la escena */
    private List<Figura> figuras = new ArrayList<>();

    /**
     * Constructor de la escena.
     * 
     * @param width  Ancho de la escena.
     * @param height Alto de la escena.
     */
    public Escena(int width, int height){ this.width=width; this.height=height; }

    /** @return Ancho de la escena en píxeles */
    public int getWidth(){ return width; }

    /** @return Alto de la escena en píxeles */
    public int getHeight(){ return height; }

    /** @return Lista de figuras de la escena */
    public List<Figura> getFiguras(){ return figuras; }

    /**
     * Añade una figura a la escena.
     * @param f Figura a añadir.
     */
    public void add(Figura f){ figuras.add(f); }
}
