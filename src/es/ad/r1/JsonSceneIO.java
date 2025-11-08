package es.ad.r1;
import java.io.IOException; import java.nio.file.*;
public class JsonSceneIO {

    /**
     * Escribe una escena en formato JSON en el archivo especificado.
     * 
     * @param e    Escena a serializar.
     * @param path Ruta del archivo destino.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void write(Escena e, Path path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"escena\": {\n");
        sb.append("    \"dimensiones\": [").append(e.getWidth()).append(", ").append(e.getHeight()).append("],\n");
        sb.append("    \"figuras\": [\n");
        // Recorre todas las figuras de la escena y las serializa según su tipo
        for (int i = 0; i < e.getFiguras().size(); i++) {
            Figura f = e.getFiguras().get(i);
            sb.append("      {");
            // Según el tipo de figura, añade los atributos correspondientes
            if (f instanceof Rectangulo) {
                Rectangulo r = (Rectangulo) f;
                sb.append("\"rectangulo\": {\"x\":\"").append(r.x).append("\",\"y\":\"").append(r.y)
                  .append("\",\"width\":\"").append(r.width).append("\",\"height\":\"").append(r.height)
                  .append("\",\"stroke-width\":\"").append(f.getStrokeWidth()).append("\",\"stroke\":\"").append(f.getStroke())
                  .append("\",\"fill\":\"").append(f.getFill()).append("\"}");
            } else if (f instanceof Circulo) {
                Circulo c = (Circulo) f;
                sb.append("\"circulo\": {\"cx\":\"").append(c.cx).append("\",\"cy\":\"").append(c.cy)
                  .append("\",\"r\":\"").append(c.r).append("\",\"stroke-width\":\"").append(f.getStrokeWidth())
                  .append("\",\"stroke\":\"").append(f.getStroke()).append("\",\"fill\":\"").append(f.getFill()).append("\"}");
            } else if (f instanceof Linea) {
                Linea l = (Linea) f;
                sb.append("\"linea\": {\"x1\":\"").append(l.x1).append("\",\"y1\":\"").append(l.y1)
                  .append("\",\"x2\":\"").append(l.x2).append("\",\"y2\":\"").append(l.y2)
                  .append("\",\"stroke-width\":\"").append(f.getStrokeWidth()).append("\",\"stroke\":\"").append(f.getStroke()).append("\"}");
            }
            sb.append("}");
            if (i < e.getFiguras().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("    ]\n  }\n}\n");
        // Escribe el contenido generado en el archivo destino
        Files.writeString(path, sb.toString());
    }
}
