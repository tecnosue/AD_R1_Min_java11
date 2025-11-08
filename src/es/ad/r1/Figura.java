package es.ad.r1;
import java.io.Serializable;

/**
 * Clase abstracta que representa una figura geométrica genérica.
 * 
 * Define atributos comunes como color de borde (stroke), color de relleno (fill),
 * grosor del borde, opacidad del borde y del relleno, y una etiqueta descriptiva.
 * Proporciona métodos getter y setter para estos atributos.
 * Es serializable para poder guardarse y recuperarse en disco.
 * 
 * Las subclases deben implementar los métodos para exportar la figura en texto plano,
 * en formato SVG y para generar una etiqueta SVG descriptiva.
 */
public abstract class Figura implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Color del borde en formato hexadecimal (#RRGGBB) */
    protected String stroke="#000000";
    /** Color de relleno en formato hexadecimal (#RRGGBB) o "none" */
    protected String fill="none";
    /** Grosor del borde */
    protected int strokeWidth=3;
    /** Opacidad del borde (0.0 a 1.0) */
    protected double strokeOpacity=1.0;
    /** Opacidad del relleno (0.0 a 1.0) */
    protected double fillOpacity=1.0;
    /** Etiqueta descriptiva de la figura */
    protected String label=null;

    /** @return Color del borde */
    public String getStroke(){ return stroke; }
    /** @param v Color del borde */
    public void setStroke(String v){ stroke=v; }

    /** @return Color de relleno */
    public String getFill(){ return fill; }
    /** @param v Color de relleno */
    public void setFill(String v){ fill=v; }

    /** @return Grosor del borde */
    public int getStrokeWidth(){ return strokeWidth; }
    /** @param v Grosor del borde */
    public void setStrokeWidth(int v){ strokeWidth=v; }

    /** @return Opacidad del borde */
    public double getStrokeOpacity(){ return strokeOpacity; }
    /** @param v Opacidad del borde */
    public void setStrokeOpacity(double v){ strokeOpacity=v; }

    /** @return Opacidad del relleno */
    public double getFillOpacity(){ return fillOpacity; }
    /** @param v Opacidad del relleno */
    public void setFillOpacity(double v){ fillOpacity=v; }

    /** @return Etiqueta descriptiva */
    public String getLabel(){ return label; }
    /** @param v Etiqueta descriptiva */
    public void setLabel(String v){ label=v; }

    /**
     * Devuelve una representación en texto plano de la figura.
     * @return Cadena con los datos de la figura.
     */
    public abstract String toTxt();

    /**
     * Devuelve una representación SVG de la figura.
     * @return Cadena SVG que describe la figura.
     */
    public abstract String toSvg();

    /**
     * Devuelve una etiqueta SVG descriptiva para la figura.
     * @return Cadena SVG con la etiqueta de la figura.
     */
    public abstract String labelSvg();
}
