#!/usr/bin/env bash
# Script para ejecutar el proyecto AD_R1_Min_java11 desde la línea de comandos.
#
# Este script lanza el archivo JAR generado por build.sh, pasando todos los argumentos recibidos al programa principal.
# Es importante porque simplifica la ejecución del proyecto, permitiendo probar y utilizar las funcionalidades
# sin necesidad de recordar la ruta ni los parámetros de java manualmente.
#
# Si el proyecto se gestionara con Maven, la ejecución podría hacerse con 'mvn exec:java' o 'mvn package' seguido de 'java -jar ...',
# integrando la ejecución en el ciclo de vida del proyecto y facilitando la gestión de dependencias y perfiles.
# Sin embargo, para proyectos sencillos, este script es suficiente y directo.

set -euo pipefail  # Termina el script si ocurre cualquier error, variable no definida o error en pipes.

java -jar target/ad-r1-escena.jar "$@"  # Ejecuta el JAR principal, pasando todos los argumentos recibidos.