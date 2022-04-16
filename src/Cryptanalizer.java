import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Cryptanalizer {
    private static final String INCORRECT_PATH_MSG = "Некорректный путь к файлу, введите корректный путь:";
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";

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

        System.out.println("Введите ключ шифрования (целое положительное число): ");
        int k = -1;                                             //algorithm key
        while (k == -1) {
            try {
                k = Math.abs(new Scanner(System.in).nextInt()); //enter int key from console
            } catch (Exception e) {
                System.out.println("Введите корректные данные.");
            }
        }

        System.out.println("Шифруем содержимое исходного файла в \n" + outputFilePath.toString());
    }

    //method decrypts entered file to output file with key
    private static void decrypt() {
        System.out.println("Тут будем расшифровывать текст.");
    }

    //method checks if the entered filePath is correct
    //String direction = input if inputFile, = output if outputFile
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

    //encryption algorithm
    //int method = 0 for encryption, = 1 for decryption
    public static String encryptorDecryptor(Path inputFile, Path outputFile, int key, int method) {
        return null;
    }
}
