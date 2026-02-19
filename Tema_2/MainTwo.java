package Tema_2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Tema_1.TextUtils;

public class MainTwo {

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

        ArrayList<CountThread> threads = new ArrayList<>();
        Random random = new Random();

        for (String phrase : paragraphs) { 
            CountThread thread = new CountThread(phrase, word);

            int priority = random.nextInt(10) + 1;
            thread.setPriority(priority);

            threads.add(thread);
            thread.start();
        }

        for (CountThread countThread : threads)
            countThread.join();

        int total = 0;

        for (CountThread countThread : threads)
            total += countThread.getResult();

        System.out.println(
            "Multihilo: El total de veces que aparece la palabra "
            + word + " es " + total + " veces"
        );
        scanner.close();
    }
}