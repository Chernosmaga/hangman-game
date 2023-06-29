import java.util.*;

public class Game {
    private static final Manager manager = new Manager();
    private static final Random generator = new Random();
    private static final List<String> letters = new ArrayList<>();
    private static final HashMap<Integer,String> guessedLetters = new HashMap<>();
    private final Hangman hangman = new Hangman();

    public void guessingALetter(Scanner read) {
        System.out.println("Начнём?\n");
        hangman.printHangmanZeroTry(); // печать пустой виселицы
        System.out.println("\nСлово: ");
        printingAWord();
        boolean found;
        int tries = 6; // пользователю дано шесть попыток
        int counterLetter = 0;

        while (true) {
            found = false;
            System.out.println("\nПодумай и введи букву, которая может быть в слове");
            String letter = read.next(); // считываю букву, которая может быть в слове

            for (int i = 0; i < letters.size(); i++) {
                if (letters.get(i).equals(letter)) { // если есть буква в слове, то заполняется HashMap угаданных букв
                    if (!letters.get(i).equals(guessedLetters.get(i))) {
                        counterLetter++; // инкрементируем счётчик букв в слове
                    }
                    guessedLetters.put(i, letter);
                    found = true;
                }
            }

            for (int i = 0; i < letters.size(); i++) {
                guessedLetters.putIfAbsent(i, "_"); // заполняем HashMap шаблоном пустых букв
            }

            System.out.println(guessedLetters.get(0) + guessedLetters.get(1) + guessedLetters.get(2) +
                    guessedLetters.get(3) + guessedLetters.get(4) + guessedLetters.get(5) +
                    guessedLetters.get(6) + guessedLetters.get(7)); // печать слова с шаблоном

            if (counterLetter == 6) { // проверка на выигрыш, если выиграл, печатается сообщение
                System.out.println("Сегодня вешать некого :)");
                clear();
                break;
            } else {
                if (!found) {// Проверка ошибок. Если ошибся - печатается одна из стадий виселицы
                    tries--;

                    if (tries == 5) {
                        hangman.printHangmanFirstTry();
                    } else if (tries == 4) {
                        hangman.printHangmanSecondTry();
                    } else if (tries == 3) {
                        hangman.printHangmanThirdTry();
                    } else if (tries == 2) {
                        hangman.printHangmanForthTry();
                    } else if (tries == 1) {
                        hangman.printHangmanFifthTry();
                    } else { // проигрыш, игра окончена
                        hangman.printHangmanSixthTry();
                        clear();
                        break;
                    }
                }
            }
        }
    }

    private void clear() {
        letters.clear();
        guessedLetters.clear();
    }

    public void printingAWord() {
        manager.readWordsFile(); // вызываю метод, чтоб при запуске игры считались слова из файла
        List<String> localListOfWords = manager.getWordsList(); // создаю новый лист, чтоб сложить туда слова
        // получаю случайное слово из листа
        String randomWord = localListOfWords.get(generator.nextInt(localListOfWords.size()));

        for (int i = 0; i < randomWord.length(); i++) { // разбиваю случайное слово по буквам
            char letter = randomWord.charAt(i);
            letters.add(String.valueOf(letter));
        }

        int first = generator.nextInt(6); // генерирую рандомные числа
        int second = generator.nextInt(6);

        String firstRandomLetter = letters.get(first); // достаю буквы под этими числами
        String secondRandomLetter = letters.get(second);

        guessedLetters.put(first,letters.get(first));
        guessedLetters.put(second,letters.get(second));

        // печатаю слово для того, чтоб пользователь начинал угадывать буквы
        for (String string : letters) {
            if (string.equals(firstRandomLetter) || string.equals(secondRandomLetter)) {
                System.out.print(string); // если буква рандомная, то она печатается
            } else {
                System.out.print("_"); // иначе на месте буквы печатается знак "_"
            }
        }
    }

}