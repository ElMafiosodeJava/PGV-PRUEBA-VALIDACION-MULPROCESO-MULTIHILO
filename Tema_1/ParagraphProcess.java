package Tema_1;


public class ParagraphProcess {
    public static void main(String[] args) throws Exception {
        String word = args[0];
        int index = Integer.parseInt(args[1]);

        var paragraphs = TextUtils.readParagraphs("UD1,2 -Desventajas IA en el aprendizaje.txt");
        String paragraph = paragraphs.get(index);

        int count = TextUtils.countWord(paragraph, word);
        System.out.println(count);
    }
}