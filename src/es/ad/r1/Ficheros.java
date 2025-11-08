package es.ad.r1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Clase Ficheros
 *  - Permite inspeccionar un fichero o directorio y mostrar información básica por consola.
 *  - Puede ejecutarse de forma independiente:
 *
 *      java es.ad.r1.Ficheros [ruta]
 *
 *  Si no se indica ruta, usa "." (directorio actual).
 *
 *  TO DO (alumno): si la ruta es un directorio, muestra también el número de ficheros y directorios que contiene.
 *                  Pista: usa File#listFiles() y cuenta con streams o un bucle clásico.
 */
public class Ficheros {

    public static void main(String[] args) {
        String ruta = args.length > 0 ? args[0] : ".";
        File f = new File(ruta);

        if (!f.exists()) {
            System.out.println("El fichero o ruta no existe.");
            return;
        }

        if (f.isFile()) {
            mostrarInfoFichero(f);
        } else if (f.isDirectory()) {
            mostrarInfoDirectorio(f);
        } else {
            System.out.println("Ruta desconocida: " + f.getAbsolutePath());
        }
    }

    private static void mostrarInfoFichero(File f) {
        System.out.println("Fichero: " + f.getAbsolutePath());
        System.out.println("Tamaño (bytes): " + f.length());
        System.out.println("Última modificación: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(f.lastModified()));
        System.out.println("Permisos -> lectura: " + f.canRead() + ", escritura: " + f.canWrite() + ", ejecución: " + f.canExecute());
    }

    private static void mostrarInfoDirectorio(File dir) {
        System.out.println("Directorio: " + dir.getAbsolutePath());

        String[] hijos = dir.list();
        if (hijos == null) {
            System.out.println(" (sin permiso o error al listar)");
            return;
        }
        Arrays.sort(hijos, String.CASE_INSENSITIVE_ORDER);
        for (String nombre : hijos) {
            System.out.println(" - " + nombre);
        }
        // TO DO (alumno): si la ruta es un directorio, muestra también el
        // número de ficheros y directorios que contiene.
        int numFicheros = 0;
        int numDirectorios = 0;
        
        File[] listaCompleta = dir.listFiles(); // Obtenemos la lista de ficheros/directorios

        if (listaCompleta != null) {
            for (File f : listaCompleta) {
                if (f.isFile()) {
                    numFicheros++; // Si es un fichero, incrementa el contador de ficheros
                } else if (f.isDirectory()) {
                    numDirectorios++; // Si es un directorio, incrementa el de directorios
                }
            }
        }

        // Imprimimos el resultado
        System.out.println("-------------------------------------");
        System.out.println("Total Ficheros: " + numFicheros);
        System.out.println("Total Directorios: " + numDirectorios);
        System.out.println("-------------------------------------");



        // (opcional) ejemplo de cálculo de tamaño agregado SOLO de ficheros del primer nivel
        long total = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File ff : files) {
                if (ff.isFile()) total += ff.length();
            }
        }
        System.out.println("Tamaño agregado (primer nivel, solo ficheros): " + total + " bytes");
    }
}
