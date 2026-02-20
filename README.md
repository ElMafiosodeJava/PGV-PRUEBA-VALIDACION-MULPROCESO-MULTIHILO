# PGV-PRUEBA-VALIDACION-MULPROCESO-MULTIHILO

Documentación sencilla
Búsqueda de palabra por párrafos
(Multiproceso y Multihilo)

Objetivo general: leer un archivo de texto, dividirlo en párrafos y contar cuántas veces aparece una palabra indicada por el usuario. Se implementan dos versiones: una usando procesos (multiproceso) y otra usando hilos (multihilo).
Archivo de entrada: UD1,2 -Desventajas IA en el aprendizaje.txt (párrafos separados por líneas en blanco).

Mapa rápido de clases
Clase	Paquete	Responsabilidad principal
MainOne	Tema_1	Programa multiproceso: crea un proceso por párrafo y suma los resultados.
ParagraphProcess	Tema_1	Proceso hijo: cuenta la palabra en un párrafo concreto (por índice).
TextUtils	Tema_1	Utilidades: leer párrafos del archivo y contar ocurrencias de una palabra.
MainTwo	Tema_2	Programa multihilo: crea un hilo por párrafo y suma los resultados.
CountThread	Tema_2	Hilo trabajador: cuenta la palabra en el párrafo asignado y guarda el resultado.

Programa 1: Versión multiproceso
Qué hace en general
•	Pide al usuario una palabra.
•	Lee el archivo y lo separa en párrafos (lista).
•	Lanza un proceso Java por cada párrafo (un worker por párrafo).
•	Cada proceso escribe su conteo en un archivo ParagraphX.txt.
•	El proceso principal espera a que terminen todos, lee los archivos y suma el total.
Flujo simplificado
1.	MainOne solicita la palabra y valida que no esté vacía.
2.	MainOne obtiene los párrafos con TextUtils.readParagraphs(...).
3.	Para cada índice i: arranca un proceso que ejecuta Tema_1.ParagraphProcess con (word, i) y redirige su salida a Paragraphi.txt.
4.	MainOne hace waitFor() a todos los procesos.
5.	MainOne lee el primer número de cada Paragraphi.txt, lo convierte a int y acumula el total.
6.	Muestra por consola el total final.

Clase: MainOne (Tema_1)
•	Responsabilidad: coordinar el trabajo. Divide el texto en párrafos y reparte cada párrafo a un proceso.
•	Entrada: palabra a buscar (por consola).
•	Salida: mensaje final con el total de apariciones (por consola).
•	Detalle importante: usa System.getProperty("java.class.path") para pasar el classpath al proceso hijo.
Clase: ParagraphProcess (Tema_1)
•	Responsabilidad: contar la palabra en UN solo párrafo.
•	Recibe parámetros por args: args[0] = palabra, args[1] = índice del párrafo.
•	Lee de nuevo todos los párrafos y selecciona uno con paragraphs.get(index).
•	Calcula el conteo con TextUtils.countWord(...) y lo imprime con System.out.println(count).
•	Como MainOne redirige la salida estándar, ese número termina guardado en Paragraphi.txt.

Programa 2: Versión multihilo
Qué hace en general
•	Pide al usuario una palabra.
•	Lee el archivo y lo separa en párrafos.
•	Crea un hilo (CountThread) por párrafo y lo inicia.
•	Espera a que todos los hilos terminen (join).
•	Suma los resultados de cada hilo y muestra el total.
Flujo simplificado
1.	MainTwo solicita la palabra y valida que no esté vacía.
2.	MainTwo obtiene los párrafos con TextUtils.readParagraphs(...).
3.	Para cada párrafo: crea un CountThread(paragraph, word), le asigna una prioridad aleatoria y lo inicia.
4.	MainTwo hace join() a todos los hilos para asegurar que ya han calculado su resultado.
5.	MainTwo suma countThread.getResult() de cada hilo y lo muestra.

Clase: MainTwo (Tema_2)
•	Responsabilidad: coordinar el trabajo multihilo y acumular el total.
•	Asigna prioridades aleatorias (1-10) a cada hilo. Nota: la prioridad puede influir, pero no garantiza el orden de ejecución.
Clase: CountThread (Tema_2)
•	Responsabilidad: contar la palabra en el párrafo asignado (el texto se guarda en el atributo text).
•	Campos principales: text (párrafo), word (palabra), result (conteo).
•	Método run(): calcula result = TextUtils.countWord(text, word).
•	Método getResult(): permite al programa principal recuperar el conteo.

Utilidades compartidas
Clase: TextUtils (Tema_1)
•	readParagraphs(filePath): lee el archivo línea a línea y construye una lista de párrafos. Un párrafo termina cuando encuentra una línea en blanco.
•	countWord(text, word): convierte a minúsculas, separa en palabras con la expresión regular \W+ (cualquier cosa que no sea letra/dígito/_) y cuenta coincidencias exactas.

¿Cómo se separa cada hilo/proceso con su párrafo?
•	En multihilo (MainTwo): en el bucle for (String phrase : paragraphs), cada hilo se crea con new CountThread(phrase, word). Ese parámetro phrase es el párrafo concreto, y se guarda dentro del hilo en el atributo text. Por eso cada hilo trabaja sobre su párrafo.
•	En multiproceso (MainOne): el programa principal no pasa el texto del párrafo, pasa el índice i (String.valueOf(i)). El proceso hijo (ParagraphProcess) vuelve a leer los párrafos y hace paragraphs.get(index) para obtener el párrafo que le toca.

Diferencias rápidas entre ambas versiones
•	Multiproceso (MainOne): arranca una JVM por párrafo. Es más pesado (más memoria/tiempo de arranque), pero cada proceso está aislado.
•	Multihilo (MainTwo): todo ocurre dentro de la misma JVM. Es más ligero y rápido de arrancar; los hilos comparten memoria.
•	Comunicación de resultados: en multiproceso se usa un archivo por párrafo (Paragraphi.txt). En multihilo, cada hilo guarda el resultado en un atributo (result).

Notas de ejecución
•	En multiproceso, se generan archivos temporales Paragraph0.txt, Paragraph1.txt, ... con el conteo de cada párrafo.
•	Ambas versiones devuelven el mismo resultado final: el total de apariciones de la palabra en todo el archivo.
