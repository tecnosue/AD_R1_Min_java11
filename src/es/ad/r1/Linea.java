package es.ad.r1;

/**
 * Representa una figura geométrica de tipo línea.
 * Hereda de la clase Figura y añade propiedades específicas de la línea,
 * como las coordenadas de los puntos inicial (x1, y1) y final (x2, y2).
 * 
 * Proporciona métodos para exportar la figura en formato texto y SVG,
 * así como para generar una etiqueta SVG descriptiva.
 */
public class Linea extends Figura {
    private static final long serialVersionUID = 1L;

    /** Coordenada X del punto inicial */
    public int x1;
    /** Coordenada Y del punto inicial */
    public int y1;
    /** Coordenada X del punto final */
    public int x2;
    /** Coordenada Y del punto final */
    public int y2;

    /**
     * Constructor de la línea.
     * 
     * @param x1    Coordenada X del punto inicial.
     * @param y1    Coordenada Y del punto inicial.
     * @param x2    Coordenada X del punto final.
     * @param y2    Coordenada Y del punto final.
     * @param color Color del borde (stroke). Si es nulo, se usa un color por defecto.
     */
    public Linea(int x1,int y1,int x2,int y2,String color){
        this.x1=x1; this.y1=y1; this.x2=x2; this.y2=y2;
        this.stroke=color!=null?color:"#ff6d00";
    }

    /**
     * Devuelve una representación en texto plano de la línea.
     * @return Cadena con los datos de la línea.
     */
    @Override
    public String toTxt(){
        return String.format("linea %d %d %d %d %s", x1,y1,x2,y2,stroke);
    }

    /**
     * Devuelve una representación SVG de la línea.
     * @return Cadena SVG que describe la línea.
     */
    @Override
    public String toSvg(){
        return String.format(
            "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"%s\" stroke-width=\"%d\" stroke-opacity=\"%s\"/>",
            x1,y1,x2,y2,stroke,strokeWidth,fmt01(strokeOpacity)
        );
    }

    /**
     * Devuelve una etiqueta SVG para la línea, mostrando su descripción.
     * @return Cadena SVG con la etiqueta de la línea.
     */
    @Override
    public String labelSvg(){
        int lx=(x1+x2)/2+8, ly=(y1+y2)/2;
        String t = label!=null?label:String.format("L (%d,%d→%d,%d)", x1,y1,x2,y2);
        return String.format("<text class=\"lbl\" x=\"%d\" y=\"%d\">%s</text>", lx, ly, esc(t));
    }

    /**
     * Normaliza un valor double entre 0 y 1 para atributos SVG.
     * @param v Valor a normalizar.
     * @return Cadena con el valor entre 0 y 1.
     */
    private String fmt01(double v){
        return String.valueOf(Math.max(0,Math.min(1,v)));
    }

    /**
     * Escapa caracteres especiales para texto SVG.
     * @param s Cadena a escapar.
     * @return Cadena escapada.
     */
    private String esc(String s){
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
