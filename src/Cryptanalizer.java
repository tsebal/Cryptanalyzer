import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Cryptanalizer
 * Converts text from the ALPHABET by Caesar's cipher.
 *
 * @author Tsebal
 * @since 0.1b
 */

public class Cryptanalizer {
    private static final String INCORRECT_PATH_MSG = "Некорректный путь к файлу, введите корректный путь:";
    public static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в криптоанализатор!\nРабота производится с шифром Цезаря.\n" +
                "Пожалуйста сделайте свой выбор (введите цифру соответствующую пункту меню):\n" +
                "1) Шифровать текст\n" +
                "2) Дешифровать текст\n" +
                "3) Выход");

        Scanner scanner = new Scanner(System.in);
        int menuChoice = scanner.nextInt();
        switch (menuChoice) {
            case 1 -> encrypt();
            case 2 -> decrypt();
            case 3 -> System.exit(0);
            default -> System.out.println("Вы сделали некорректный выбор.");
        }
        scanner.close();
    }

    //method encrypts entered file to entered output file with key
    private static void encrypt() {
        System.out.println("Введите полный путь к файлу для шифрования: ");
        Path inputFilePath = pathResolver("input");

        System.out.println("Введите полный путь к файлу для вывода результатов шифрования: ");
        Path outputFilePath = pathResolver("output");

        int k = getSecretKey();

        System.out.println("Шифруем содержимое исходного файла в \n" + outputFilePath.toString());
        String result = encryptorDecryptor(inputFilePath, outputFilePath, k, 0);
        System.out.println(result);
    }

    //method decrypts entered file to output file with key
    private static void decrypt() {
        System.out.println("Введите полный путь к файлу для расшифровки: ");
        Path inputFilePath = pathResolver("input");

        System.out.println("Введите полный путь к файлу для вывода результатов расшифровки: ");
        Path outputFilePath = pathResolver("output");

        int k = getSecretKey();

        System.out.println("Расшифровываем содержимое исходного файла в \n" + outputFilePath.toString());
        String result = encryptorDecryptor(inputFilePath, outputFilePath, k, 1);
        System.out.println(result);
    }

    //gets secret algorithm key from console
    private static int getSecretKey() {
        System.out.println("Введите ключ шифрования (целое положительное число): ");
        int k = -1;                                             //algorithm key
        while (k == -1) {
            try {
                k = Math.abs(new Scanner(System.in).nextInt()); //enter int key from console
            } catch (Exception e) {
                System.out.println("Введите корректные данные.");
            }
        }
        return k;
    }

    /**
     * Method checks if the entered filePath is correct and creates new output file if not exist
     *
     * @param direction = input if inputFile,
     *                  = output if outputFile
     */
    public static Path pathResolver(String direction) {
        Path filePath = null;
        try {
            Scanner scanner = new Scanner(System.in);
            filePath = Path.of(scanner.nextLine());

            //creates output file on disk if direction is output or checks correct path for input file
            while (Files.notExists(filePath)) {
                if (direction.equals("output")) {
                    Files.createFile(filePath);
                } else {
                    System.out.println(INCORRECT_PATH_MSG);
                    filePath = Path.of(scanner.nextLine());
                }
            }
        } catch (Exception e) {
            System.out.println("Возникла непредвиденная ошибка, перезапустите программу.");
            e.printStackTrace();
            System.exit(-1);
        }
        return filePath;
    }

    /**
     * Encryption/decryption algorithm
     *
     * @param method = 0 for encryption,
     *               = 1 for decryption
     */
    public static String encryptorDecryptor(Path inputFile, Path outputFile, int key, int method) {
        //creates reader/writer for encrypt/decrypt chars with algorithm
        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inputFile.toString()), "UTF-8"));
             Writer writer = new BufferedWriter(
                     new OutputStreamWriter(
                             new FileOutputStream(outputFile.toString()), "UTF-8"))) {
            int charOffset = 0;                             //char at new position with entered key

            //cycle reads one char per round
            while (reader.ready()) {
                char ch = (char) reader.read();             //char to resolve with algorithm key
                if (ALPHABET.indexOf(Character.toLowerCase(ch)) != -1) {
                    //method choice encryption/decryption (ALPHABET offset)
                    if (method == 0) {
                        charOffset = ALPHABET.indexOf(Character.toLowerCase(ch)) + key;
                    } else {
                        charOffset = ALPHABET.indexOf(Character.toLowerCase(ch)) - key;
                    }
                    //encryption way from stream
                    if (charOffset >= ALPHABET.length()) {
                        if (Character.isUpperCase(ch)) {
                            writer.write(Character.toUpperCase(ALPHABET.charAt(charOffset % ALPHABET.length())));
                        } else {
                            writer.write(ALPHABET.charAt(charOffset % ALPHABET.length()));
                        }
                        //decryption way from stream
                    } else if (charOffset < 0) {
                        if (Character.isUpperCase(ch)) {
                            writer.write(Character.toUpperCase(ALPHABET.charAt(ALPHABET.length() - Math.abs(charOffset % ALPHABET.length()))));
                        } else {
                            writer.write(ALPHABET.charAt(ALPHABET.length() - Math.abs(charOffset % ALPHABET.length())));
                        }
                    } else {
                        if (Character.isUpperCase(ch)) {
                            writer.write(Character.toUpperCase(ALPHABET.charAt(charOffset)));
                        } else {
                            writer.write(ALPHABET.charAt(charOffset));
                        }
                    }
                } else {
                    writer.write(ch);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Операция прошла успешно.";
    }
}
