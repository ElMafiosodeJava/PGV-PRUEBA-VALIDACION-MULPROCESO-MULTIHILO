package Tema_1;

import java.io.*;
import java.util.*;


public class MainOne {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Palabra a buscar: ");
        String word = scanner.nextLine().trim();

        if (word.isEmpty()) {
            System.out.println("Texto vacío no válido");
            scanner.close();
            return;
        }

        ArrayList<String> paragraphs =
                TextUtils.readParagraphs("UD1,2 -Desventajas IA en el aprendizaje.txt");

        List<Process> processes = new ArrayList<>();
        List<File> outFiles = new ArrayList<>();

        String classPath = System.getProperty("java.class.path");

        int i = 0;
        
        for (String p : paragraphs) {

            File outFile = new File("Paragraph" + i + ".txt");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java",
                    "-cp", classPath,
                    "Tema_1.ParagraphProcess",
                    word,
                    String.valueOf(i)
            );

           
            processBuilder.redirectOutput(outFile);

            processes.add(processBuilder.start());
            outFiles.add(outFile);
            i++;
        }


        for (Process process : processes) process.waitFor();

        int total = 0;
        for (File files : outFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(files))) {
                String line = br.readLine();
                if (line != null && !line.isBlank()) {
                    total += Integer.parseInt(line.trim());
                }
            }
        }

        System.out.println(
                "Multiproceso: El total de veces que aparece la palabra "
                        + word + " es " + total + " veces"
        );

        scanner.close();
    }
}
