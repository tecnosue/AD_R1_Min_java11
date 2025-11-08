package es.ad.r1;
import java.io.IOException; import java.nio.file.*;
/**
 * Clase de utilidades para exportar una escena en formato SVG.
 * 
 * Permite generar un archivo SVG a partir de una instancia de Escena, incluyendo
 * la cuadrícula, el fondo, el borde y todas las figuras geométricas con sus etiquetas.
 * El SVG generado es compatible con navegadores y editores gráficos.
 */
public class SvgSceneIO {

    /**
     * Escribe una escena en formato SVG en el archivo especificado.
     * 
     * @param e    Escena a exportar.
     * @param path Ruta del archivo destino.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void write(Escena e, Path path) throws IOException {
        StringBuilder sb=new StringBuilder();
        // Cabecera SVG con dimensiones y vista
        sb.append(String.format("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">\n",
            e.getWidth(), e.getHeight(), e.getWidth(), e.getHeight()));
        // Definiciones de estilos y cuadrícula
        sb.append("<defs>\n<style><![CDATA[\n.grid{stroke:#e6e6e6;stroke-width:1}\n.lbl{font-family:monospace;font-size:12px;fill:#333}\n]]></style>\n</defs>\n");
        // Fondo blanco
        sb.append("<rect x=\"0\" y=\"0\" width=\"100%\" height=\"100%\" fill=\"#fff\"/>\n");
        int step=50;
        // Líneas de la cuadrícula verticales
        for(int x=0;x<=e.getWidth();x+=step) sb.append(String.format("<line x1=\"%d\" y1=\"0\" x2=\"%d\" y2=\"%d\" class=\"grid\"/>\n",x,x,e.getHeight()));
        // Líneas de la cuadrícula horizontales
        for(int y=0;y<=e.getHeight();y+=step) sb.append(String.format("<line x1=\"0\" y1=\"%d\" x2=\"%d\" y2=\"%d\" class=\"grid\"/>\n",y,e.getWidth(),y));
        // Borde de la escena
        sb.append(String.format("<rect x=\"0\" y=\"0\" width=\"%d\" height=\"%d\" fill=\"none\" stroke=\"#ccc\" stroke-width=\"2\"/>\n", e.getWidth(), e.getHeight()));
        // Figuras y etiquetas
        for(Figura f:e.getFiguras()){ 
            sb.append("  ").append(f.toSvg()).append("\n"); 
            sb.append("  ").append(f.labelSvg()).append("\n"); 
        }
        sb.append("</svg>\n");
        // Escribe el SVG en el archivo destino
        Files.writeString(path, sb.toString());
    }
}
