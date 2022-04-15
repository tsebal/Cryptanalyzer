import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Cryptanalizer {
    private static final String INCORRECT_PATH_MSG = "Некорректный путь к исходному файлу, введите корректный путь:";

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

    //method encrypts entered file
    private static void encrypt() {
        System.out.println("Введите полный путь к файлу для шифрования: ");
        Path inputFilePath = pathResolver();

        System.out.println("Введите полный путь к файлу для вывода результатов шифрования: ");
        Path outputFilePath = pathResolver();

        System.out.println("Шифруем содержимое исходного файла в \n" + outputFilePath.toString());
    }

    private static void decrypt() {
        System.out.println("Тут будем расшифровывать текст.");
    }

    //method checks if the entered filePath is correct
    public static Path pathResolver() {
        Path filePath = null;
        try {
            Scanner scanner = new Scanner(System.in);
            filePath = Path.of(scanner.nextLine());
            while (Files.notExists(filePath)) {
                System.out.println(INCORRECT_PATH_MSG);
                filePath = Path.of(scanner.nextLine());
            }
        } catch (Exception e) {

        }
        return filePath;
    }
}
