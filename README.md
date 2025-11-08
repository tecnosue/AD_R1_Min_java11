# AD_R1_Min_java11 — Partes 1–5 (simplificado)

Cumple **fielmente** el enunciado (TXT/CSV*, BIN, SVG, JSON). *CSV opcional.

## Uso
```bash
bash build.sh

# TXT → validar y exportar (JSON, SVG, TXT, BIN)
bash run.sh import-txt data/escena.txt out --name demo

# TXT → SVG
bash run.sh export-svg data/escena.txt out/escena.svg

# TXT → JSON
bash run.sh export-json data/escena.txt out/escena.json

# Serialización / Deserialización
bash run.sh serialize data/escena.txt out/escena.bin
bash run.sh deserialize out/escena.bin out/escena_deserializada.txt

# Asistente por teclado
bash run.sh wizard out --name practica
```


---

## Clases ejecutables por separado (Ficheros y DOM)

Además de `es.ad.r1.Main`, ahora puedes ejecutar estas clases de forma independiente:

### Ficheros
Inspecciona un fichero o directorio con `java.io.File`.

```bash
# Compilar y empaquetar (si no lo has hecho)
bash build.sh

# Ejecutar sobre el directorio actual
java -cp target/ad-r1-escena.jar es.ad.r1.Ficheros

# O sobre una ruta concreta
java -cp target/ad-r1-escena.jar es.ad.r1.Ficheros data
```

> **To Do (alumno)**: si la ruta es un directorio, mostrar también el número de ficheros y directorios que contiene.

### DOM
Lectura y escritura de XML con DOM.

```bash
# Leer el XML de ejemplo
java -cp target/ad-r1-escena.jar es.ad.r1.DOM leer data/cursos.xml

# Escribir un XML nuevo en out/cursos_generado.xml
java -cp target/ad-r1-escena.jar es.ad.r1.DOM escribir out/cursos_generado.xml
```

> **To Do (alumno)**: al escribir, añade un segundo `<modulo>` con otros valores.
