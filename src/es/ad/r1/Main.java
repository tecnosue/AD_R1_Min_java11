package es.ad.r1;
import java.nio.file.*; import java.util.Scanner;

/**
 * Clase principal del programa. Permite importar, exportar y manipular escenas gráficas desde la línea de comandos.
 * 
 * El algoritmo principal interpreta el primer argumento como comando y ejecuta la acción correspondiente:
 * - import-txt: importa una escena desde un archivo de texto, valida y exporta en varios formatos.
 * - export-svg: exporta una escena desde TXT a SVG.
 * - export-json: exporta una escena desde TXT a JSON.
 * - serialize: serializa una escena desde TXT a binario.
 * - deserialize: deserializa una escena binaria y la exporta a TXT.
 * - wizard/interactivo: lanza el asistente interactivo por consola para crear una escena.
 * 
 * Las llamadas a clases auxiliares gestionan la lectura, validación, serialización y exportación:
 * - TxtSceneIO: lectura y escritura de escenas en formato texto.
 * - SvgSceneIO: exportación a SVG.
 * - JsonSceneIO: exportación a JSON.
 * - BinSceneIO: serialización y deserialización binaria.
 * - ConsoleWizard: asistente interactivo para crear escenas.
 * - ValidationReport: informe de validación de la escena.
 * 
 * El método exportAll centraliza la exportación en todos los formatos soportados.
 * El método parseBase permite personalizar el nombre base de los archivos exportados.
 * El método printHelp muestra la ayuda de uso y los atributos opcionales.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length<1){ printHelp(); return; }
        String cmd=args[0];
        // Importa una escena desde TXT, valida y exporta en todos los formatos
        if("import-txt".equals(cmd)){
            if(args.length<3){ System.err.println("Uso: import-txt <input.txt> <out_dir> [--name base]"); return; }
            Path in=Paths.get(args[1]), out=Paths.get(args[2]); String base=parseBase(args,"escena",3); Files.createDirectories(out);
            ParseResult pr=TxtSceneIO.parse(in); Path val=out.resolve("validacion.txt"); Files.writeString(val, pr.getReport().toText());
            if(!pr.getReport().hasErrors()){ Escena e=pr.getEscena(); exportAll(e,out,base); TxtSceneIO.write(e,out.resolve(base+".txt"));
                System.out.println("OK. Exportados JSON/SVG/TXT/BIN en "+out.toAbsolutePath()); }
            else System.out.println("Se han detectado errores. Revise "+val.toAbsolutePath());
        // Exporta una escena TXT a SVG
        } else if("export-svg".equals(cmd)){
            if(args.length<3){ System.err.println("Uso: export-svg <input.txt> <out.svg>"); return; }
            Escena e=TxtSceneIO.parse(Paths.get(args[1])).getEscena(); SvgSceneIO.write(e, Paths.get(args[2]));
        // Exporta una escena TXT a JSON
        } else if("export-json".equals(cmd)){
            if(args.length<3){ System.err.println("Uso: export-json <input.txt> <out.json>"); return; }
            Escena e=TxtSceneIO.parse(Paths.get(args[1])).getEscena(); JsonSceneIO.write(e, Paths.get(args[2]));
        // Serializa una escena TXT a binario
        } else if("serialize".equals(cmd)){
            if(args.length<3){ System.err.println("Uso: serialize <input.txt> <out.bin>"); return; }
            Escena e=TxtSceneIO.parse(Paths.get(args[1])).getEscena(); BinSceneIO.serialize(e, Paths.get(args[2]));
        // Deserializa una escena binaria y la exporta a TXT
        } else if("deserialize".equals(cmd)){
            if(args.length<3){ System.err.println("Uso: deserialize <input.bin> <out.txt>"); return; }
            Escena e=BinSceneIO.deserialize(Paths.get(args[1])); TxtSceneIO.write(e, Paths.get(args[2]));
        // Asistente interactivo para crear una escena
        } else if("wizard".equals(cmd) || "interactivo".equals(cmd)){
            Path out=Paths.get(args.length>=2?args[1]:"out"); String base=parseBase(args,"escena",2); Files.createDirectories(out);
            ConsoleWizard w=new ConsoleWizard(); Scanner sc=new Scanner(System.in); Escena e=w.buildScene(sc);
            Path val=out.resolve("validacion.txt"); ValidationReport r=new ValidationReport(); Files.writeString(val, r.toText());
            exportAll(e,out,base); TxtSceneIO.write(e,out.resolve(base+".txt"));
            System.out.println("OK. Escena creada y exportada a: "+out.toAbsolutePath());
        } else printHelp();
    }

    /**
     * Obtiene el nombre base para los archivos exportados a partir de los argumentos.
     * 
     * @param args Array de argumentos.
     * @param def  Valor por defecto.
     * @param from Índice desde el que buscar el parámetro --name.
     * @return Nombre base para los archivos.
     */
    static String parseBase(String[] args,String def,int from){ for(int i=from;i<args.length-1;i++) if("--name".equals(args[i])) return args[i+1]; return def; }

    /**
     * Exporta la escena en todos los formatos soportados (TXT, JSON, SVG, BIN).
     * 
     * @param e    Escena a exportar.
     * @param out  Directorio de salida.
     * @param base Nombre base de los archivos.
     * @throws Exception Si ocurre un error en la exportación.
     */
    static void exportAll(Escena e, Path out, String base) throws Exception {
        TxtSceneIO.write(e, out.resolve(base+"_export.txt"));
        JsonSceneIO.write(e, out.resolve(base+".json"));
        SvgSceneIO.write(e, out.resolve(base+".svg"));
        BinSceneIO.serialize(e, out.resolve(base+".bin"));
    }

    /**
     * Muestra la ayuda de uso y los atributos opcionales.
     */
    static void printHelp(){
        System.out.println("Uso:\n"
            +"  import-txt <input.txt> <out_dir> [--name base]\n"
            +"  export-svg <input.txt> <out.svg>\n"
            +"  export-json <input.txt> <out.json>\n"
            +"  serialize  <input.txt> <out.bin>\n"
            +"  deserialize <input.bin> <out.txt>\n"
            +"  wizard [out_dir] [--name base]\n"
            +"Atributos TXT opcionales: fill=#rrggbb sw=3 so=0.8 fo=0.6 stroke=#rrggbb label=Mi_Figura");
    }
}
