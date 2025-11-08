package es.ad.r1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;

/**
 * Clase de utilidades para la serialización y deserialización binaria de objetos Escena.
 * 
 * Permite guardar una instancia de Escena en un archivo y recuperarla posteriormente,
 * utilizando la serialización estándar de Java. Los métodos usan streams con buffering
 * para mejorar el rendimiento en operaciones de disco.
 */
public class BinSceneIO {

    /**
     * Serializa un objeto Escena y lo guarda en el archivo especificado.
     * Si el directorio padre del archivo no existe, lo crea automáticamente.
     * 
     * @param e   La instancia de Escena a serializar.
     * @param out Ruta del archivo destino donde se guardará el objeto.
     * @throws IOException Si ocurre un error de E/S durante la operación.
     */
    public static void serialize(Escena e, Path out) throws IOException {
        Path parent = out.getParent();
        if (parent != null) Files.createDirectories(parent);
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                        Files.newOutputStream(out, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
                )
        )) {
            oos.writeObject(e);
        }
    }

    /**
     * Deserializa un objeto Escena desde el archivo especificado.
     * 
     * @param in Ruta del archivo origen desde donde se leerá el objeto.
     * @return La instancia de Escena recuperada del archivo.
     * @throws IOException Si ocurre un error de E/S o si la clase no se encuentra.
     */
    public static Escena deserialize(Path in) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        Files.newInputStream(in, StandardOpenOption.READ)
                )
        )) {
            try {
                return (Escena) ois.readObject();
            } catch (ClassNotFoundException ex) {
                throw new IOException(ex);
            }
        }
    }
}
