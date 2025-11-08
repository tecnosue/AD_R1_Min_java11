package es.ad.r1;

/**
 * Representa una figura geométrica de tipo círculo.
 * Hereda de la clase Figura y añade propiedades específicas del círculo,
 * como el centro (cx, cy) y el radio (r).
 * 
 * Proporciona métodos para exportar la figura en formato texto y SVG,
 * así como para generar una etiqueta SVG descriptiva.
 */
public class Circulo extends Figura {
    private static final long serialVersionUID = 1L;

    /** Coordenada X del centro del círculo */
    public int cx;
    /** Coordenada Y del centro del círculo */
    public int cy;
    /** Radio del círculo */
    public int r;

    /**
     * Constructor del círculo.
     * 
     * @param cx    Coordenada X del centro.
     * @param cy    Coordenada Y del centro.
     * @param r     Radio del círculo.
     * @param color Color del borde (stroke). Si es nulo, se usa un color por defecto.
     */
    public Circulo(int cx, int cy, int r, String color) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.stroke = color != null ? color : "#1a73e8";
    }

    /**
     * Devuelve una representación en texto plano del círculo.
     * @return Cadena con los datos del círculo.
     */
    @Override
    public String toTxt() {
        return String.format("circulo %d %d %d %s", cx, cy, r, stroke);
    }

    /**
     * Devuelve una representación SVG del círculo.
     * @return Cadena SVG que describe el círculo.
     */
    @Override
    public String toSvg() {
        return String.format(
            "<circle cx=\"%d\" cy=\"%d\" r=\"%d\" stroke=\"%s\" stroke-width=\"%d\" stroke-opacity=\"%s\" fill=\"%s\" fill-opacity=\"%s\"/>",
            cx, cy, r, stroke, strokeWidth, fmt01(strokeOpacity), fill, fmt01(fillOpacity)
        );
    }

    /**
     * Devuelve una etiqueta SVG para el círculo, mostrando su descripción.
     * @return Cadena SVG con la etiqueta del círculo.
     */
    @Override
    public String labelSvg() {
        String t = label != null ? label : String.format("Circ (%d,%d,r=%d)", cx, cy, r);
        return String.format("<text class=\"lbl\" x=\"%d\" y=\"%d\">%s</text>", cx + r + 8, cy, esc(t));
    }

    /**
     * Normaliza un valor double entre 0 y 1 para atributos SVG.
     * @param v Valor a normalizar.
     * @return Cadena con el valor entre 0 y 1.
     */
    private String fmt01(double v) {
        return String.valueOf(Math.max(0, Math.min(1, v)));
    }

    /**
     * Escapa caracteres especiales para texto SVG.
     * @param s Cadena a escapar.
     * @return Cadena escapada.
     */
    private String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
