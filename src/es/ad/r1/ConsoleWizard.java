package es.ad.r1;
import java.util.Scanner;

/**
 * Clase que implementa un asistente interactivo por consola para crear escenas gráficas.
 * 
 * Permite al usuario definir el tamaño de la escena y añadir figuras geométricas (rectángulo, círculo, línea)
 * solicitando los parámetros necesarios mediante preguntas en la consola. Cada figura puede personalizarse
 * con atributos como color, grosor, opacidad y etiqueta.
 */
public class ConsoleWizard {

    /**
     * Inicia el asistente interactivo para construir una escena.
     * Solicita al usuario el tamaño de la escena y permite añadir figuras una a una.
     * 
     * @param sc Scanner para leer la entrada del usuario.
     * @return La escena construida con las figuras añadidas.
     */
    public Escena buildScene(Scanner sc){
        System.out.println("== Asistente interactivo ==");
        int w = askInt(sc, "Ancho (px)", 600), h = askInt(sc, "Alto (px)", 400);
        Escena e = new Escena(w, h);
        System.out.println("Añade figuras (rectangulo|circulo|linea). 'fin' para terminar.");
        for (;;) {
            System.out.print("Tipo (rectangulo|circulo|linea|fin): ");
            String t = sc.next().trim().toLowerCase();
            if ("fin".equals(t)) break;
            try {
                switch (t) {
                    case "rectangulo": {
                        int x = askInt(sc, "x", 40), y = askInt(sc, "y", 40),
                            ww = askInt(sc, "width", 200), hh = askInt(sc, "height", 140);
                        String s = askColor(sc, "stroke #RRGGBB", "#444444");
                        Rectangulo r = new Rectangulo(x, y, ww, hh, s);
                        style(sc, r);
                        e.add(r);
                        break;
                    }
                    case "circulo": {
                        int cx = askInt(sc, "cx", 330), cy = askInt(sc, "cy", 140), rr = askInt(sc, "r", 80);
                        String s = askColor(sc, "stroke #RRGGBB", "#1a73e8");
                        Circulo c = new Circulo(cx, cy, rr, s);
                        style(sc, c);
                        e.add(c);
                        break;
                    }
                    case "linea": {
                        int x1 = askInt(sc, "x1", 60), y1 = askInt(sc, "y1", 320),
                            x2 = askInt(sc, "x2", 540), y2 = askInt(sc, "y2", 80);
                        String s = askColor(sc, "stroke #RRGGBB", "#ff6d00");
                        Linea l = new Linea(x1, y1, x2, y2, s);
                        style(sc, l);
                        e.add(l);
                        break;
                    }
                    default:
                        System.out.println("Tipo no reconocido.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                sc.nextLine();
            }
        }
        return e;
    }

    /**
     * Solicita al usuario un valor entero por consola, mostrando un valor por defecto.
     * Si el usuario no introduce nada, se usa el valor por defecto.
     * 
     * @param sc    Scanner para leer la entrada.
     * @param label Etiqueta descriptiva de la pregunta.
     * @param def   Valor entero por defecto.
     * @return El valor introducido por el usuario o el valor por defecto.
     */
    private int askInt(Scanner sc, String label, int def) {
        for (;;) {
            System.out.print(label + " [" + def + "]: ");
            String s = sc.next().trim();
            if (s.isEmpty()) return def;
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("→ Debe ser entero.");
            }
        }
    }

    /**
     * Solicita al usuario un color en formato hexadecimal (#RRGGBB), mostrando un valor por defecto.
     * Si el usuario no introduce nada, se usa el valor por defecto.
     * 
     * @param sc    Scanner para leer la entrada.
     * @param label Etiqueta descriptiva de la pregunta.
     * @param def   Color por defecto en formato #RRGGBB.
     * @return El color introducido por el usuario o el valor por defecto.
     */
    private String askColor(Scanner sc, String label, String def) {
        for (;;) {
            System.out.print(label + " [" + def + "]: ");
            String s = sc.next().trim();
            if (s.isEmpty()) return def;
            if (s.matches("#[0-9a-fA-F]{6}")) return s;
            System.out.println("→ Formato #RRGGBB.");
        }
    }

    /**
     * Solicita al usuario atributos de estilo para una figura (relleno, grosor, opacidad, etiqueta).
     * Los valores por defecto se muestran y se usan si el usuario no introduce nada.
     * 
     * @param sc Scanner para leer la entrada.
     * @param f  Figura a la que se aplicarán los estilos.
     */
    private void style(Scanner sc, Figura f) {
        System.out.print("fill (#RRGGBB|none) [" + f.getFill() + "]: ");
        String fill = sc.next().trim();
        if (!fill.isEmpty()) f.setFill(fill);

        System.out.print("sw (grosor) [" + f.getStrokeWidth() + "]: ");
        String sw = sc.next().trim();
        if (!sw.isEmpty()) try { f.setStrokeWidth(Integer.parseInt(sw)); } catch (Exception ignored) {}

        System.out.print("so (0..1) [" + f.getStrokeOpacity() + "]: ");
        String so = sc.next().trim();
        if (!so.isEmpty()) try { f.setStrokeOpacity(Double.parseDouble(so)); } catch (Exception ignored) {}

        System.out.print("fo (0..1) [" + f.getFillOpacity() + "]: ");
        String fo = sc.next().trim();
        if (!fo.isEmpty()) try { f.setFillOpacity(Double.parseDouble(fo)); } catch (Exception ignored) {}

        System.out.print("label (usa _ para espacios) [vacío]: ");
        String lb = sc.next().trim();
        if (!lb.isEmpty()) f.setLabel(lb.replace('_', ' '));
    }
}
