import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Main {
    public static void main(String[] strings) {
//        if (strings.length < 1) {
//            System.out.println("No file specified");
//            return;
//        }
//        String fileName = strings[0];
        String fileName = "D:\\Documents\\COS 330\\assignments\\COS330Assignment5\\src\\CeasarText.txt";

        char[] alphabet = ("0123456789" + "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".toUpperCase()).toCharArray();
        short[] alphabetIndex = new short[128];
        for (short a = 0; a < alphabetIndex.length; a++) {
            alphabetIndex[a] = -1;
            for (short b = 0; b < alphabet.length; b++) {
                if (alphabet[b] == a)
                    alphabetIndex[a] = b;
            }
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append("\n");
            System.out.println("Original Message:\n" + stringBuilder);
            char[] fileContent = stringBuilder.toString().toCharArray();

            HashSet<String> dictionary = getDictionary("D:\\Documents\\COS 330\\assignments\\COS330Assignment5\\src\\EnglishWordList.txt");

            String bestShift = "Error!";
            double bestShiftPercentage = Double.NEGATIVE_INFINITY;
            //start shifts
            for (short a = 0; a < alphabet.length; a++) {
                StringBuilder fileContentShifted = new StringBuilder();
                for (char ogChar : fileContent) {
                    short ogCharIndex = alphabetIndex[ogChar];
                    fileContentShifted.append((ogCharIndex == -1? ogChar : alphabet[(ogCharIndex + a) % alphabet.length]));
                }

                String shiftMessage = fileContentShifted.toString();
                double shiftPercentage = getEnglishDictionaryMatch(dictionary, shiftMessage);
                if (shiftPercentage > bestShiftPercentage) {
                    bestShiftPercentage = shiftPercentage;
                    bestShift = shiftMessage;
                }
            }

            System.out.println("Best message:\n" + bestShift);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static HashSet<String> getDictionary(String path) {
        HashSet<String> dictionary = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine())!= null)
                dictionary.add(line.toLowerCase());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dictionary;
    }

    private static double getEnglishDictionaryMatch(HashSet<String> dictionary, String sentence) {
        String[] words = sentence.split("[ \n]");
        int total = words.length, matches = 0;
        for (String word : words)
            if (dictionary.contains(word.toLowerCase()))
                ++matches;
        return ((double)matches / total) * 100;
    }
}
