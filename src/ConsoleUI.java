import java.util.Scanner;

public class ConsoleUI {

    private final GameEngine engine;
    private final HintsEngine hints;

    public ConsoleUI(GameEngine engine) {
        this.engine = engine;
        this.hints = new HintsEngine(engine);
    }

    private boolean isValidGuess(String s) {
        if (s == null || s.length() != 3) return false;

        for (char c : s.toCharArray())
            if (!Character.isDigit(c)) return false;

        if (s.charAt(0) == '0') return false;

        return !(s.charAt(0) == s.charAt(1) ||
                s.charAt(0) == s.charAt(2) ||
                s.charAt(1) == s.charAt(2));
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean play = true;

        while (play) {

            engine.startNewGame();
            System.out.println("Загадано число (3 різні цифри). Починаємо!");

            long roundStart = System.currentTimeMillis();
            long roundLimit = 2 * 60 * 1000; // 2 хвилини

            while (true) {

                // Ограничение времени
                long now = System.currentTimeMillis();
                if (now - roundStart > roundLimit) {
                    System.out.println("\n⏳ Час вичерпано! Раунд закінчено.");
                    if (!askToPlayAgain(sc)) {
                        System.out.println("Дякуємо за гру!");
                        sc.close();
                        return;
                    }
                    break; // начинаем новый раунд
                }

                // ввод попытки
                System.out.print("Введіть спробу (наприклад, 371): ");
                String input = sc.nextLine().trim();

                if (!isValidGuess(input)) {
                    System.out.println("Неправильний ввід. Введіть 3 різні цифри, перша не 0.");
                    continue;
                }

                Result r = engine.makeGuess(input);

                // вывод результата
                System.out.println(r);

                if (!r.getBullsDetails().isEmpty()) {
                    System.out.println("Бики: ");
                    r.getBullsDetails().forEach(p -> System.out.println(" " + p));
                }

                if (!r.getCowsDetails().isEmpty()) {
                    System.out.println("Корови: ");
                    r.getCowsDetails().forEach(p -> System.out.println(" " + p));
                }

                // подсказка
                String hint = hints.getHint();
                if (!hint.isEmpty()) {
                    System.out.println("💡 " + hint);
                }

                // победа
                if (engine.isSolved()) {
                    System.out.println("Вітаю! Ви відгадали число у " + engine.getAttempts() + " спроб(и).");
                    break;
                }
            }

            // спросить о новой игре
            if (!askToPlayAgain(sc)) {
                play = false;
                System.out.println("Дякуємо за гру!");
            }
        }

        sc.close();
    }

    private boolean askToPlayAgain(Scanner sc) {
        System.out.print("Хочете зіграти ще раз? (y/n): ");
        return sc.nextLine().trim().equalsIgnoreCase("y");
    }
}
