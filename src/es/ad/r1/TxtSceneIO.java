package es.ad.r1;
import java.io.*; import java.nio.file.*; import java.util.*;

/**
 * Clase de utilidades para importar y exportar escenas en formato texto plano.
 * 
 * Permite leer archivos TXT con la definición de una escena y sus figuras, validando los datos
 * y generando un informe de errores/adverencias. También permite guardar una escena en formato TXT.
 * Es fundamental para la interoperabilidad y edición manual de escenas en el proyecto.
 */
public class TxtSceneIO {

    /**
     * Parsea un archivo de texto y construye una instancia de Escena junto a su informe de validación.
     * 
     * @param path Ruta del archivo TXT.
     * @return ParseResult con la escena y el informe de validación.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static ParseResult parse(Path path) throws IOException {
        java.util.List<String> lines = Files.readAllLines(path);
        ValidationReport report = new ValidationReport();
        if (lines.isEmpty()) {
            report.error(1, "Fichero vacío.");
            return new ParseResult(new Escena(0,0), report);
        }
        String l1 = lines.get(0).trim();
        if (!l1.toLowerCase(Locale.ROOT).startsWith("dimensiones")) {
            report.error(1, "La primera línea debe ser: dimensiones <ancho> <alto>");
            return new ParseResult(new Escena(0,0), report);
        }
        String[] p = l1.split("\\s+");
        if (p.length < 3) {
            report.error(1, "Faltan W y H.");
            return new ParseResult(new Escena(0,0), report);
        }
        int w = parseIntSafe(p[1],1,report), h = parseIntSafe(p[2],1,report);
        Escena e = new Escena(w,h);

        // Procesa cada línea y crea las figuras correspondientes
        for (int i=1;i<lines.size();i++){
            String ln = lines.get(i).trim();
            if (ln.isEmpty() || ln.startsWith("#")) continue;

            String[] tok = ln.split("\\s+");
            String tipo = tok[0].toLowerCase(Locale.ROOT);
            try{
                if ("rectangulo".equals(tipo)) {
                    if (tok.length < 6) { report.error(i+1,"rectangulo: x y width height #RRGGBB"); continue; }
                    Rectangulo r = new Rectangulo(
                        parseIntSafe(tok[1],i+1,report), parseIntSafe(tok[2],i+1,report),
                        parseIntSafe(tok[3],i+1,report), parseIntSafe(tok[4],i+1,report), tok[5]
                    );
                    if (!isColor(tok[5])) report.warn(i+1,"Color no parece #RRGGBB: "+tok[5]);
                    applyAttrs(r, tok, 6, report, i+1);
                    e.add(r);

                } else if ("circulo".equals(tipo)) {
                    if (tok.length < 5) { report.error(i+1,"circulo: cx cy r #RRGGBB"); continue; }
                    Circulo c = new Circulo(
                        parseIntSafe(tok[1],i+1,report), parseIntSafe(tok[2],i+1,report),
                        parseIntSafe(tok[3],i+1,report), tok[4]
                    );
                    if (!isColor(tok[4])) report.warn(i+1,"Color no parece #RRGGBB: "+tok[4]);
                    applyAttrs(c, tok, 5, report, i+1);
                    e.add(c);

                } else if ("linea".equals(tipo)) {
                    if (tok.length < 6) { report.error(i+1,"linea: x1 y1 x2 y2 #RRGGBB"); continue; }
                    Linea l = new Linea(
                        parseIntSafe(tok[1],i+1,report), parseIntSafe(tok[2],i+1,report),
                        parseIntSafe(tok[3],i+1,report), parseIntSafe(tok[4],i+1,report), tok[5]
                    );
                    if (!isColor(tok[5])) report.warn(i+1,"Color no parece #RRGGBB: "+tok[5]);
                    applyAttrs(l, tok, 6, report, i+1);
                    e.add(l);

                } else {
                    report.error(i+1,"Tipo desconocido: "+tipo);
                }
            }catch(Exception ex){
                report.error(i+1,"Error procesando línea: "+ex.getMessage());
            }
        }
        return new ParseResult(e,report);
    }

    /**
     * Exporta una escena a un archivo de texto plano.
     * 
     * @param e    Escena a exportar.
     * @param path Ruta del archivo destino.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void write(Escena e, Path path) throws IOException {
        java.util.List<String> out = new java.util.ArrayList<>();
        out.add("dimensiones " + e.getWidth() + " " + e.getHeight());
        for (Figura f : e.getFiguras()) out.add(f.toTxt());
        Files.write(path, out);
    }

    // Aplica atributos opcionales a la figura desde el TXT
    private static void applyAttrs(Figura f, String[] tok, int start, ValidationReport report, int line){
        for (int i=start;i<tok.length;i++){
            String t = tok[i];
            if (!t.contains("=")) continue;
            String[] kv = t.split("=",2);
            if (kv.length < 2) continue;
            String k = kv[0].toLowerCase(Locale.ROOT), v = kv[1];
            try{
                switch(k){
                    case "fill":   if (isColor(v) || "none".equals(v)) f.setFill(v); else report.warn(line,"fill inválido: "+v); break;
                    case "stroke": if (isColor(v)) f.setStroke(v); else report.warn(line,"stroke inválido: "+v); break;
                    case "sw":     f.setStrokeWidth(Integer.parseInt(v)); break;
                    case "so":     f.setStrokeOpacity(Double.parseDouble(v)); break;
                    case "fo":     f.setFillOpacity(Double.parseDouble(v)); break;
                    case "label":  f.setLabel(v.replace("_"," ")); break;
                    default: report.warn(line,"Atributo desconocido: "+k);
                }
            }catch(Exception ex){
                report.warn(line,"Valor inválido para "+k+": "+v);
            }
        }
    }

    // Conversión segura de String a int con reporte de error
    private static int parseIntSafe(String s, int line, ValidationReport report){
        try{ return Integer.parseInt(s); }
        catch(Exception e){ report.error(line,"No es entero válido: '"+s+"'"); return 0; }
    }
    // Comprueba si el color tiene formato hexadecimal válido
    private static boolean isColor(String s){ return s.matches("#[0-9a-fA-F]{6}"); }
}
