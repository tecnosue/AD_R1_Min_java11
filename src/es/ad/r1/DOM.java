package es.ad.r1;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Clase DOM
 *  - Demuestra lectura y escritura de XML con la API DOM estándar.
 *  - Puede ejecutarse de forma independiente:
 *
 *      java es.ad.r1.DOM leer [rutaXml]      # lee y muestra por consola
 *      java es.ad.r1.DOM escribir [salida]   # genera un XML de ejemplo
 *
 *  Valores por defecto: rutaXml = data/cursos.xml, salida = out/cursos_generado.xml
 *
 *  TO DO (alumno): al escribir el DOM, añade un segundo <modulo> diferente al de ejemplo
 *                  (por ejemplo, nombre = "PSP", horas = 140, nota = 7.0).
 */
public class DOM {

    public static void main(String[] args) throws Exception {
        String accion = (args.length > 0) ? args[0].toLowerCase() : "leer";
        switch (accion) {
            case "leer":
                String ruta = (args.length > 1) ? args[1] : "data/cursos.xml";
                leer(ruta);
                break;
            case "escribir":
                String salida = (args.length > 1) ? args[1] : "out/cursos_generado.xml";
                escribir(salida);
                System.out.println("XML generado en: " + new File(salida).getAbsolutePath());
                break;
            default:
                System.out.println("Uso:");
                System.out.println("  java es.ad.r1.DOM leer [rutaXml]");
                System.out.println("  java es.ad.r1.DOM escribir [salida]");
        }
    }

    /** Lee un XML y muestra su contenido por consola. */
    public static void leer(String rutaXml) throws Exception {
        File xml = new File(rutaXml);
        if (!xml.exists()) {
            System.out.println("No existe el XML: " + xml.getAbsolutePath());
            return;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();

        Element raiz = doc.getDocumentElement(); // <cursos>
        System.out.println("Raíz: " + raiz.getTagName());
        if (raiz.hasAttribute("nivel")) {
            System.out.println("Atributo 'nivel' = " + raiz.getAttribute("nivel"));
        }

        NodeList nodos = raiz.getElementsByTagName("modulo");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element el = (Element) nodos.item(i);

            String nombre = el.getElementsByTagName("nombre").item(0).getTextContent();
            String horas = el.getElementsByTagName("horas").item(0).getTextContent();
            String nota  = el.getElementsByTagName("nota").item(0).getTextContent();

            System.out.println("Nombre: " + nombre);
            System.out.println("Horas : " + horas);
            System.out.println("Nota  : " + nota);
            System.out.println();
        }
    }

    /** Construye un DOM de ejemplo y lo guarda a disco. */
    public static void escribir(String rutaSalida) throws Exception {
        // 1) Crea documento vacío
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        Document document = dBuilder.newDocument();

        // 2) Raíz <cursos nivel="2º">
        Element root = document.createElement("cursos");
        root.setAttribute("nivel", "2º");
        document.appendChild(root);

        // 3) Primer módulo
        Element modulo = document.createElement("modulo");
        root.appendChild(modulo);

        Element nombre = document.createElement("nombre");
        nombre.appendChild(document.createTextNode("Acceso a Datos"));
        modulo.appendChild(nombre);

        Element horas = document.createElement("horas");
        horas.appendChild(document.createTextNode("240"));
        modulo.appendChild(horas);

        Element nota = document.createElement("nota");
        nota.appendChild(document.createTextNode("8.4"));
        modulo.appendChild(nota);

        // TO DO (alumno): añade aquí un segundo <modulo> con otros valores
        // Segundo módulo
        Element modulo2 = document.createElement("modulo");
        root.appendChild(modulo2);

        Element nombre2 = document.createElement("nombre");
        nombre2.appendChild(document.createTextNode("PSP")); // Nombre: PSP
        modulo2.appendChild(nombre2);

        Element horas2 = document.createElement("horas");
        horas2.appendChild(document.createTextNode("140")); // Horas: 140
        modulo2.appendChild(horas2);

        Element nota2 = document.createElement("nota");
        nota2.appendChild(document.createTextNode("7.0")); // Nota: 7.0
        modulo2.appendChild(nota2);


       

        // 4) Guarda a fichero
        new File(new File(rutaSalida).getParent() == null ? "." : new File(rutaSalida).getParent()).mkdirs();
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.transform(new DOMSource(document), new StreamResult(new File(rutaSalida)));
    }
}
