import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * BruteForceCipher
 * Selects the encryption key for the Caesar encryption algorithm
 * by brute force from the provided ALPHABET and the encrypted text.
 *
 */

public class BruteForceCipher {
    private Character[] alphabet;
    private char[] decodedText;
    private String[] plainText;
    private List<Character> alphabetList;
    private Path inputFilePath;

    public BruteForceCipher(Path inputFilePath, String alphabet) {
        //converts Alphabet String to Character array in the upper case format
        this.alphabet = alphabet.toUpperCase().chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        this.alphabetList = Arrays.asList(this.alphabet);
        this.plainText = new String[alphabet.length()];
        this.inputFilePath = inputFilePath;
    }

    //Produces plain text with encryption key by direct matching
    private String[] producePlaintext(String cipherText) {
        //put each letter of the ciphertext in an array of characters in the upper case format
        char[] message = cipherText.toUpperCase().toCharArray();
        //loop through all the keys
        for (int key = 0; key < alphabet.length; key++) {
            //set the value of the decrypted array of characters to be the same as the length of the cipher text
            decodedText = new char[message.length];
            //loop through the characters of the ciphertext
            for (int i = 0; i < message.length; i++) {

                //if character is in the alphabetList
                if (alphabetList.contains(message[i])) {
                    //shift the letters
                    decodedText[i] = alphabet[(alphabetList.indexOf(message[i]) + key) % alphabet.length];
                } else {
                    decodedText[i] = message[i];
                }
            }
            //Decrypted text with one of the keys (key = Array cell number)
            plainText[key] = String.valueOf(decodedText);
        }
        return plainText;
    }

    //Finds a cipher key by direct matching and returns it
    public int findKey() throws IOException {
        //reads encrypted text from the file to string
        byte[] encryptedFileContent = Files.readAllBytes(Paths.get(inputFilePath.toString()));
        String encryptedText = new String(encryptedFileContent, StandardCharsets.UTF_8);
        //run the search and put all calculated variants into in String array
        String[] decryptedText = producePlaintext(encryptedText);
        //The encryption key you are looking for
        int key = 0;
        //calculate the key from the founded variants with natural text criteria
        for (int i = decryptedText.length - 1; i > 0; i--) {
            key = alphabet.length - i;
            //Shows the key and a snippet of decrypted text
            if (decryptedText[i].contains(". ") && decryptedText[i].contains(", ")) {
                System.out.println("Ключ подобран! Ключ = " + key);
                System.out.println("Отрывок расшифрованного текста:\n" + decryptedText[i].substring(0, 64) + "\n");
                break;
            } else {
                System.out.print("Ключ = " + key + " не подходит. " +
                                    "Возможно закодированная информация не является логичным текстом.\r");
            }
        }
        return key;
    }
}
