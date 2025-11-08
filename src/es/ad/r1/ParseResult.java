package es.ad.r1;

/**
 * Clase que encapsula el resultado de parsear una escena desde un archivo de texto.
 * método parse() recibe una cadena como entrada, extrae la información necesaria y la convierte en un objeto de la clase que la llama 
 * Permite devolver conjuntamente la instancia de Escena construida y el informe de validación
 * asociado, facilitando el manejo de ambos datos en las operaciones de importación y validación.
 * Es fundamental en el flujo del proyecto porque centraliza el resultado de la lectura y validación.
 */
public class ParseResult {
    /** Escena construida a partir del archivo */
    private final Escena escena;
    /** Informe de validación asociado a la escena */
    private final ValidationReport report;

    /**
     * Constructor del resultado de parseo.
     * 
     * @param e Instancia de Escena parseada.
     * @param r Informe de validación generado.
     */
    public ParseResult(Escena e, ValidationReport r){ this.escena=e; this.report=r; }

    /** @return Escena parseada */
    public Escena getEscena(){ return escena; }

    /** @return Informe de validación */
    public ValidationReport getReport(){ return report; }
}
