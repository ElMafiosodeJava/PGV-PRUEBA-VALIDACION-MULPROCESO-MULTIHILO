package Tema_1;

import java.io.*;
import java.util.*;

public class TextUtils {

    public static ArrayList<String> readParagraphs(String filePath) throws Exception {
        ArrayList<String> paragraphs = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(new FileReader(filePath));

        String line;
        StringBuilder paragraph = new StringBuilder();

        while ((line = buffer.readLine()) != null) {
            if (line.trim().isEmpty()) {
                if (paragraph.length() > 0) {
                    paragraphs.add(paragraph.toString());
                    paragraph.setLength(0);
                }
            } else {
                paragraph.append(line).append(" ");
            }
        }

        if (paragraph.length() > 0)
            paragraphs.add(paragraph.toString());

        buffer.close();
        return paragraphs;
    }

    public static int countWord(String text, String word) {
        String[] phrase = text.toLowerCase().split("\\W+");
        int count = 0;
        for (String p : phrase)
            if (p.equals(word.toLowerCase()))
                count++;
        return count;
    }
}