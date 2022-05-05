import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Searches the cipher key by the number of repetitions of frequently used letters
 * in the presented alphabet
 */

public class StatisticalAnalyzer {
    private String alphabet;
    private Path inputFilePath;
    private Map<Character, Integer> charStatMap = new HashMap<>();

    public StatisticalAnalyzer(Path inputFilePath, String alphabet) {
        this.alphabet = alphabet.toUpperCase();
        this.inputFilePath = inputFilePath;
    }

    public void analyzeText() throws IOException {
        //reads encrypted text from the file to string
        byte[] encryptedFileContent = Files.readAllBytes(Paths.get(inputFilePath.toString()));
        String encryptedText = new String(encryptedFileContent, StandardCharsets.UTF_8);

        //load alphabet to charStatMap for compare next
        for (int i = 0; i < alphabet.length(); i++) {
            charStatMap.put(alphabet.charAt(i), 0);
        }

        //load statistics for each character of the encrypted text
        for (int i = 0; i < encryptedText.length(); i++) {
            char desiredChar = Character.toUpperCase(encryptedText.charAt(i));
            //if map contains character of encrypted text
            if (charStatMap.containsKey(desiredChar)) {
                //desiredCharValue for increment if char was founded
                int desiredCharVal = charStatMap.get(desiredChar);
                desiredCharVal++;
                charStatMap.put(desiredChar, desiredCharVal);
            }
        }

        //Sorts the map to find the most recurring character in the encrypted text
        SortedSet<Character> keys = new TreeSet<>(charStatMap.keySet());
        int charMaxCount = 0;
        char keyChar = 0;
        for (Character key : keys) {
            int value = charStatMap.get(key);
            if (value > charMaxCount) {
                charMaxCount = value;
                keyChar = key;
            }
        }

        System.out.println(findKey(keyChar, ' '));
        System.out.println(findKey(keyChar, 'А'));
        System.out.println(findKey(keyChar, 'О'));
        System.out.println(findKey(keyChar, 'Е'));
        System.out.println(findKey(keyChar, 'И'));
    }

    /**
     * Searches the key by the number of repetitions of frequently used letters
     * in the presented alphabet.
     * @param keyChar max frequently used letter in encrypted text
     * @param keyRealChar max frequently used letter in the presented alphabet
     */
    private int findKey(char keyChar, char keyRealChar) {
        int cipherKey = 0;
        int keyCharIndex = alphabet.indexOf(keyChar);           //index of encrypted char in alphabet
        int keyRealCharIndex = alphabet.indexOf(keyRealChar);   //index of plain char in alphabet
        //if last character in alphabet is a key
        if (keyRealCharIndex == alphabet.length() - 1) {
            cipherKey = alphabet.length() - keyRealCharIndex + keyCharIndex;
        } else if (keyRealCharIndex >= keyCharIndex) {
            cipherKey = Math.abs(alphabet.length() - (keyRealCharIndex - keyCharIndex) + 1);
            //if first character in alphabet is a key
        } else {
            cipherKey = Math.abs(keyRealCharIndex - keyCharIndex);
        }
        return cipherKey;
    }
}
