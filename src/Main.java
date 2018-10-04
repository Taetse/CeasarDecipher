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

        final char[] alphabet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
                'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        final short[] alphabetIndex = new short[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1 };

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
        HashSet<String> dictionary = new HashSet<>(70000);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine())!= null)
                dictionary.add(line);
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
