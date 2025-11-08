package es.ad.r1;
import java.util.*; import java.util.stream.Collectors;

/**
 * Clase que representa el informe de validación de una escena.
 * 
 * Permite registrar incidencias (información, advertencias y errores) detectadas durante el parseo
 * o validación de los datos. Cada incidencia se almacena como un objeto Issue, indicando la línea,
 * el tipo (INFO, WARN, ERROR) y el mensaje descriptivo.
 * 
 * Es utilizada por clases como TxtSceneIO y ParseResult para informar al usuario sobre problemas
 * en los datos importados, facilitando la depuración y la calidad del proyecto.
 */
public class ValidationReport {

    /**
     * Clase interna que representa una incidencia en la validación.
     * Almacena la línea, el tipo de severidad y el mensaje.
     */
    public static class Issue{
        public final int line;
        public final String severity;
        public final String message;

        /**
         * Constructor de Issue.
         * @param line Línea donde se detectó la incidencia.
         * @param severity Severidad (INFO, WARN, ERROR).
         * @param message Mensaje descriptivo.
         */
        public Issue(int line,String severity,String message){
            this.line=line; this.severity=severity; this.message=message;
        }

        /** Devuelve la incidencia en formato texto. */
        public String toText(){
            return String.format("[%s] línea %d: %s", severity, line, message);
        }
    }

    /** Lista de incidencias detectadas en la validación. */
    private final List<Issue> issues=new ArrayList<>();

    /** Añade una incidencia informativa. */
    public void info(int line,String msg){ issues.add(new Issue(line,"INFO",msg)); }
    /** Añade una advertencia. */
    public void warn(int line,String msg){ issues.add(new Issue(line,"WARN",msg)); }
    /** Añade un error. */
    public void error(int line,String msg){ issues.add(new Issue(line,"ERROR",msg)); }

    /** Indica si hay errores en el informe. */
    public boolean hasErrors(){ return issues.stream().anyMatch(i->i.severity.equals("ERROR")); }

    /** Devuelve el informe en formato texto plano. */
    public String toText(){
        return issues.isEmpty()? "Sin incidencias. Formato correcto.\n"
            : issues.stream().map(Issue::toText).collect(Collectors.joining("\n"))+"\n";
    }
}
