import java.io.*;
import java.util.Scanner;

public class TicTacToeCoder {

    public void run() {
        System.out.println("\nTicTacToeCoder is running...");

//        public static void main (String[]args){
            Scanner scanner = new Scanner(System.in);
            int[] board = new int[9];

            // Ввод исходного массива
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Введите 9 чисел от 0 до 3 : ");
                String input = scanner.nextLine().trim();
                // Удаление всех пробелов из строки
                input = input.replaceAll("\\s+", "");

                if (input.length() != 9) {
                    System.out.println("Некорректный ввод. Должно быть ровно 9 чисел.");
                    continue;
                }

                validInput = true;
                for (int i = 0; i < 9; i++) {
                    char digit = input.charAt(i);
                    int number = Character.getNumericValue(digit);
                    if (number < 0 || number > 3) {
                        System.out.println("Некорректный ввод. Числа должны быть в диапазоне [0, 3].");
                        validInput = false;
                        break;
                    }
                    board[i] = number;
                }
            }

            // Кодирование
            int encodedBoard = encodeBoard(board);

            // Запись в файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/output.txt"))) {
                writer.write(String.valueOf(encodedBoard));
                System.out.println("Файл output.txt записан");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Введите команду 'старт' или 'start' для вывода игрового поля : ");
            String command = scanner.nextLine();
            // Чтение из файла
            if ("старт".equalsIgnoreCase(command) || "start".equalsIgnoreCase(command)) {
                int decodedBoard = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader("src/output.txt"))) {
                    decodedBoard = Integer.parseInt(reader.readLine());
                    System.out.println("Файл output.txt прочитан");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Декодирование
                int[] decodedBoardArray = decodeBoard(decodedBoard);
                // Вывод игрового поля
                printBoard(decodedBoardArray);
            } else {
                System.out.println("Неизвестная команда.");
            }
        }

        // Метод для кодирования
        private static int encodeBoard ( int[] board){
            int encoded = 0;
            for (int i = 0; i < 9; i++) {
                encoded = (encoded << 2) | board[i];
            }
            return encoded;
        }

        // Метод для декодирования
        private static int[] decodeBoard ( int encoded){
            int[] board = new int[9];
            for (int i = 8; i >= 0; i--) {
                board[i] = encoded & 3;
                encoded >>= 2;
            }
            return board;
        }

        // Метод для вывода
        private static void printBoard ( int[] board){
            System.out.print("Выводим результат\n");
            for (int i = 0; i < 9; i++) {
                char symbol;
                switch (board[i]) {
                    case 0:
                        symbol = '.';
                        break;
                    case 1:
                        symbol = 'x';
                        break;
                    case 2:
                        symbol = '0';
                        break;
                    case 3:
                        symbol = '?';
                        break;
                    default:
                        symbol = '.';
                        break;
                }
                System.out.print(symbol + " ");
                if ((i + 1) % 3 == 0) {
                    System.out.println();
                }
            }
        }

//    }
}
