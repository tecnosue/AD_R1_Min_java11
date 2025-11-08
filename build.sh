#!/usr/bin/env bash
# Script de construcción manual para el proyecto AD_R1_Min_java11.
# 
# Este script compila todos los archivos Java del proyecto y genera un archivo JAR ejecutable.
# Es importante porque permite construir el proyecto de forma sencilla y reproducible, sin depender de herramientas externas.
# Es útil para entornos educativos, pruebas rápidas o proyectos pequeños donde no se requiere la complejidad de Maven o Gradle.
#
# Si se utilizara Maven, la gestión de dependencias, compilación, empaquetado y ejecución sería automática y más flexible.
# Maven permite definir el ciclo de vida del proyecto, perfiles, plugins y dependencias externas en un archivo pom.xml,
# facilitando la integración continua y el despliegue en distintos entornos.
# Sin embargo, para proyectos simples, un script como este es suficiente y más transparente para el usuario.

set -euo pipefail  # Hace que el script termine si ocurre cualquier error, variable no definida o error en pipes.

mkdir -p bin target  # Crea los directorios de salida para los archivos compilados y el JAR.

find src -name '*.java' > sources.list  # Busca todos los archivos .java en src y los lista en sources.list.

javac --release 11 -d bin @sources.list  # Compila los fuentes con Java 11 y coloca los .class en bin.

jar cfe target/ad-r1-escena.jar es.ad.r1.Main -C bin .  # Empaqueta todo en un JAR ejecutable con Main como clase principal.

echo 'OK -> target/ad-r1-escena.jar'  # Mensaje de éxito indicando la ubicación del JAR generado.