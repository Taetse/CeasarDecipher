import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        int[] alphabetIndex = new int[128];
        for (int a = 0; a < alphabetIndex.length; a++) {
            alphabetIndex[a] = -1;
            for (int b = 0; b < alphabet.length; b++) {
                if (alphabet[b] == a)
                    alphabetIndex[a] = b;
            }
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while((line =bufferedReader.readLine())!=null)
                stringBuffer.append(line).append("\n");
            System.out.println(stringBuffer);

            String fileContent = stringBuffer.toString().replace("\n", " ");
            HashSet<String> dictionary = getDictionary("D:\\Documents\\COS 330\\assignments\\COS330Assignment5\\src\\EnglishWordList.txt");

            String bestShift = "";
            double bestShiftPercentage = 0;
            //start shifts
            for (int a = 0; a < alphabet.length; a++) {
                StringBuilder fileContentShifted = new StringBuilder();
                for (int b = 0; b < fileContent.length(); b++) {
                    char ogChar = fileContent.charAt(b);
                    int ogCharIndex = alphabetIndex[ogChar];
                    fileContentShifted.append((ogCharIndex == -1? ogChar : alphabet[(ogCharIndex + a) % alphabet.length]));
                }

                String shiftMessage = fileContentShifted.toString();
                double shiftPercentage = getEnglishDictionaryMatch(dictionary, shiftMessage);
                if (shiftPercentage > bestShiftPercentage) {
                    bestShiftPercentage = shiftPercentage;
                    bestShift = shiftMessage;
                }
            }

            System.out.println("Best message:");
            System.out.println(bestShift);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File " + fileName + " not found");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("File read Exception: " + ioe.getMessage());
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static HashSet<String> getDictionary(String path) {
        HashSet<String> dictionary = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine())!=null)
                dictionary.add(line.toLowerCase());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dictionary;
    }

    public static double getEnglishDictionaryMatch(HashSet<String> dictionary, String sentence) {
        String[] words = sentence.split(" ");
        double total = words.length, matches = 0;
        for (int a = 0; a < words.length; a++) {
            if (dictionary.contains(words[a].toLowerCase()))
                matches++;
        }

        return (matches / total) * 100;
    }
}
