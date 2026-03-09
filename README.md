# KruskalAnimado 🌀

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![University](https://img.shields.io/badge/UNEG-Inform%C3%A1tica-red.svg)](http://www.uneg.edu.ve/)
[![Swing](https://img.shields.io/badge/GUI-Swing-green.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Status](https://img.shields.io/badge/Status-Academic-success.svg)]()

**KruskalAnimado** es una herramienta educativa interactiva desarrollada en Java para visualizar el **Algoritmo de Kruskal**. A diferencia de otros simuladores, este proyecto permite explorar múltiples Árboles de Expansión Mínima (MST) cuando existen aristas con pesos iguales, utilizando un motor de búsqueda exhaustiva (DFS) con retroceso.

<p align="center">
  <img  alt="Kruska<img width="1601" height="921" alt="pantalla3" src="https://github.com/user-attachments/assets/a9be76fb-3e1d-485f-98ca-b0683d50d92b" />
lAnimado en acción" width="600"/>
</p>
<img width="1258" height="570" alt="Captura de pantalla 2026-03-09 033349" src="https://github.com/user-attachments/assets/65da5692-447d-4df0-9267-e9adefe76f1f" />

---


## 📂 Estructura del Proyecto

El proyecto sigue una arquitectura estricta **MVC (Modelo-Vista-Controlador)**, organizada en los siguientes paquetes bajo `com.mycompany.kruskalanimado`:

```text
src/main/java/com/mycompany/kruskalanimado/
│
├── KruskalAnimadoApp.java             # Clase principal (Punto de entrada de la aplicación)
│
├── config/                            # ⚙️ Configuración global
│   └── ApplicationConfig.java         # Singleton para parámetros (velocidad, límites, radios)
│
├── model/                             # 🧠 Lógica de negocio y datos (Modelo)
│   ├── GraphModel.java                # Fachada principal del modelo y gestión del estado actual
│   │
│   ├── algorithm/                     # Lógica algorítmica y estrategias
│   │   ├── KruskalStrategy.java       # Implementación concreta del algoritmo de Kruskal
│   │   ├── MSTSolution.java           # Contenedor de una solución (costo total y pasos)
│   │   ├── MSTStep.java               # Representa un paso individual en la evaluación
│   │   └── MSTStrategy.java           # Interfaz (Strategy) para cálculo de árboles de expansión
│   │
│   ├── builder/                       # Lógica de construcción
│   │   └── GraphBuilder.java          # Constructor de la topología con soporte Memento (Historial)
│   │
│   └── graph/                         # Estructuras de datos puras
│       ├── Arista.java                # Definición de conexión matemática y su peso
│       ├── AristaState.java           # Enum con estados de la arista (PENDING, FIXED, etc.)
│       ├── UnionFind.java             # Estructura de conjuntos disjuntos para detección de ciclos
│       └── Vertices.java              # Definición de un nodo en el plano 2D
│
├── controller/                        # 🕹️ Gestión de eventos y orquestación (Controlador)
│   ├── AnimationController.java       # Maneja el Timer y el flujo temporal de la reproducción
│   ├── AnimationListener.java         # Interfaz para escuchar los 'ticks' de la animación
│   ├── AnimationState.java            # Enum para estados (PLAYING, PAUSED, STOPPED)
│   ├── MainController.java            # Orquestador central que vincula las vistas con el modelo
│   │
│   └── commands/                      # Implementación del Patrón Command (Undo/Redo)
│       ├── AddVertexCommand.java      # Lógica encapsulada para agregar un vértice
│       ├── Command.java               # Interfaz base para todas las acciones ejecutables
│       ├── CommandHistory.java        # Pilas de ejecución para Deshacer/Rehacer
│       └── DeleteVertexCommand.java   # Lógica encapsulada para eliminar un vértice
│
└── view/                              # 🎨 Interfaz gráfica de usuario (Vista)
    ├── ControlPanel.java              # Panel inferior (botones de control, slider, métricas)
    ├── GraphView.java                 # Lienzo (Canvas) interactivo donde se dibuja el grafo
    ├── LegendPanel.java               # Panel informativo que describe la paleta de colores
    │
    ├── observers/                     # Interfaces para el Patrón Observer
    │   ├── ModelObserver.java         # Interfaz para que las vistas escuchen al modelo
    │   └── ViewListener.java          # Interfaz para que el controlador escuche a las vistas
    │
    └── renderers/                     # Lógica de pintado gráfico delegada
        ├── ColorScheme.java           # Paleta de colores oficial unificada del sistema
        └── GraphRenderer.java         # Motor de dibujo que utiliza Graphics2D
🚀 Características Principales
Característica	Descripción
🖱️ Interacción Directa	Dibuja nodos haciendo clic en el lienzo. Cada clic genera un nuevo vértice numerado.
⚡ Cálculo en Tiempo Real	Las aristas se generan automáticamente basándose en la distancia euclidiana, redondeada a 2 decimales para agrupar pesos iguales.
🔍 Múltiples MSTs	Identifica y permite navegar entre todas las soluciones óptimas posibles mediante un algoritmo de búsqueda exhaustiva con backtracking.
🎬 Animación Paso a Paso	Control de reproducción (Play/Pause/Stop) con ajuste dinámico de velocidad (1-10) para observar el proceso de decisión.
🎨 Diseño Minimalista	Interfaz limpia con tipografía Segoe UI, paleta de colores sobria y componentes personalizados sin bordes predeterminados.
📊 Visualización de Estados	Colores diferenciados para aristas: azul (fijas), verde (evaluando válida), naranja (evaluando ciclo) y rojo punteado (descartadas).
📏 Estadísticas en Tiempo Real	Contador de vértices, número de MSTs encontrados, solución actual y costo total.
🛠️ Requisitos de Instalación
Java Development Kit (JDK) 17 o superior

Apache NetBeans 12+ o cualquier IDE compatible (IntelliJ IDEA, Eclipse)

Git (opcional, para clonar el repositorio)

Mínimo 2GB de RAM para manejo de múltiples MSTs en grafos complejos

📥 Configuración y Ejecución
Opción 1: Desde el IDE
Clonar el repositorio:

bash
git clone https://github.com/tu-usuario/KruskalAnimado.git
cd KruskalAnimado
Abrir el proyecto:

NetBeans: File > Open Project y selecciona la carpeta raíz

IntelliJ: File > Open y selecciona el archivo pom.xml o la carpeta del proyecto

Compilar y Ejecutar:

Haz clic derecho sobre el proyecto y selecciona Clean and Build

Ejecuta la clase KruskalAnimado.java

Opción 2: Desde terminal (con Maven)
bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.mycompany.kruskalanimado.KruskalAnimado"
Opción 3: Generar JAR ejecutable
bash
# Generar el archivo JAR con dependencias
mvn clean package

# Ejecutar el JAR generado
java -jar target/KruskalAnimado-1.0-SNAPSHOT.jar
📖 Instrucciones de Uso
1. Crear el Grafo
Haz clic en el área blanca para agregar puntos (vértices)

Cada nuevo vértice se numera automáticamente (V1, V2, V3...)

2. Generar Soluciones
El sistema calcula automáticamente todos los MSTs disponibles

El panel muestra el contador de soluciones encontradas

3. Navegar entre Soluciones
Usa las flechas ◀ y ▶ para cambiar entre las distintas soluciones óptimas

El costo total se actualiza en tiempo real (debe ser el mismo para todas)

4. Animar el Proceso
Presiona Play ▶ para observar la construcción paso a paso

Pausa ⏸ para detener temporalmente

Stop ■ para reiniciar la animación

Ajusta la velocidad con el slider (1 = lento, 10 = rápido)

5. Interpretar Colores
🔵 Azul: Arista ya fijada en el MST

🟢 Verde: Arista siendo evaluada (válida)

🟠 Naranja: Arista siendo evaluada (causa ciclo)

🔴 Rojo punteado: Arista descartada permanentemente

⚪ Gris: Arista aún no evaluada

🏗️ Fundamentos Técnicos
Algoritmos Implementados
Componente	Descripción
Detección de Ciclos	Implementado mediante la estructura Union-Find con optimizaciones: compresión de camino y unión por rango (complejidad O(α(n))).
Cálculo de Pesos	Distancia euclidiana: $d = \sqrt{(x_2-x_1)^2 + (y_2-y_1)^2}$ con redondeo a 2 decimales.
Búsqueda de Múltiples MSTs	Algoritmo de Kruskal combinado con un enfoque de retroceso (Backtracking/DFS) para explorar todas las combinaciones de aristas con peso idéntico.
Animación	Uso de javax.swing.Timer para control de pasos con callbacks y patrón State para manejo de estados.
Complejidad Computacional
Ordenamiento de aristas: O(E log E) donde E es el número de aristas

Búsqueda de múltiples MSTs: Exponencial en el peor caso (cuando hay muchas aristas con pesos iguales)

Optimización: Límite configurable de 100 MSTs para mantener rendimiento

🧪 Ejemplos de Uso
Ejemplo 1: Cuadrado (4 vértices)

Vértices en posiciones:
- V1: (200, 200)
- V2: (400, 200)  
- V3: (200, 400)
- V4: (400, 400)

Resultado: 2 MSTs posibles con costo 600.00
Ejemplo 2: Triángulo equilátero

Vértices:
- V1: (300, 200)
- V2: (200, 400)
- V3: (400, 400)

Resultado: 1 único MST con costo calculado
🤝 Contribuciones
Las contribuciones son bienvenidas. Si deseas mejorar el proyecto:

Haz un Fork del repositorio

Crea una rama para tu feature (git checkout -b feature/AmazingFeature)

Commit tus cambios (git commit -m 'Add some AmazingFeature')

Push a la rama (git push origin feature/AmazingFeature)

Abre un Pull Request

Áreas de mejora potencial
Implementar algoritmo de Prim como estrategia alternativa

Agregar persistencia (guardar/cargar grafos en JSON)

Exportar MSTs como imagen PNG

Modo oscuro

Zoom y desplazamiento del canvas

📄 Licencia
Este proyecto está bajo la Licencia MIT - ver el archivo LICENSE para más detalles.

👥 Autor
Orlando Cabrera
