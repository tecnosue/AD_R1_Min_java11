package es.ad.r1;

/**
 * Representa una figura geométrica de tipo rectángulo.
 * Hereda de la clase Figura y añade propiedades específicas del rectángulo:
 * posición (x, y), dimensiones (width, height) y color de borde.
 * 
 * Proporciona métodos para exportar la figura en formato texto y SVG,
 * así como para generar una etiqueta SVG descriptiva.
 */
public class Rectangulo extends Figura {
    private static final long serialVersionUID = 1L;

    /** Coordenada X de la esquina superior izquierda */
    public int x;
    /** Coordenada Y de la esquina superior izquierda */
    public int y;
    /** Ancho del rectángulo */
    public int width;
    /** Alto del rectángulo */
    public int height;

    /**
     * Constructor del rectángulo.
     * 
     * @param x     Coordenada X de la esquina superior izquierda.
     * @param y     Coordenada Y de la esquina superior izquierda.
     * @param width Ancho del rectángulo.
     * @param height Alto del rectángulo.
     * @param color Color del borde (stroke). Si es nulo, se usa un color por defecto.
     */
    public Rectangulo(int x,int y,int width,int height,String color){
        this.x=x; this.y=y; this.width=width; this.height=height; this.stroke = color!=null?color:"#333";
    }

    /**
     * Devuelve una representación en texto plano del rectángulo.
     * @return Cadena con los datos del rectángulo.
     */
    @Override
    public String toTxt(){ 
        return String.format("rectangulo %d %d %d %d %s", x,y,width,height,stroke); 
    }

    /**
     * Devuelve una representación SVG del rectángulo.
     * @return Cadena SVG que describe el rectángulo.
     */
    @Override
    public String toSvg(){
        return String.format(
            "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"%s\" stroke-width=\"%d\" stroke-opacity=\"%s\" fill=\"%s\" fill-opacity=\"%s\"/>",
            x,y,width,height,stroke,strokeWidth,fmt01(strokeOpacity),fill,fmt01(fillOpacity)
        );
    }

    /**
     * Devuelve una etiqueta SVG para el rectángulo, mostrando su descripción.
     * @return Cadena SVG con la etiqueta del rectángulo.
     */
    @Override
    public String labelSvg(){
        String t = label!=null?label:String.format("Rect (%d,%d,%d,%d)", x,y,width,height);
        return String.format("<text class=\"lbl\" x=\"%d\" y=\"%d\">%s</text>", x+8, y+16, esc(t));
    }

    /**
     * Normaliza un valor double entre 0 y 1 para atributos SVG.
     * @param v Valor a normalizar.
     * @return Cadena con el valor entre 0 y 1.
     */
    private String fmt01(double v){ return String.valueOf(Math.max(0,Math.min(1,v))); }

    /**
     * Escapa caracteres especiales para texto SVG.
     * @param s Cadena a escapar.
     * @return Cadena escapada.
     */
    private String esc(String s){ return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;"); }
}
