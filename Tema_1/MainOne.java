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

        String cp = System.getProperty("java.class.path");

        int i = 0;
        
        for (String p : paragraphs) {

            File outFile = new File("Paragraph" + i + ".txt");

            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp", cp,
                    "Tema_1.ParagraphProcess",
                    word,
                    String.valueOf(i)
            );

           
            pb.redirectOutput(outFile);

            processes.add(pb.start());
            outFiles.add(outFile);
            i++;
        }


        for (Process pr : processes) pr.waitFor();

        int total = 0;
        for (File f : outFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
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
