import java.util.Scanner;

public class ConsoleUI {
    private final GameEngine engine;

    public ConsoleUI(GameEngine engine) {
        this.engine = engine;
    }

    private boolean isValidGuess(String s) {
        if (s == null || s.length() != 3) return false;
        for (char c : s.toCharArray()) if (!Character.isDigit(c)) return false;
        if (s.charAt(0) == '0') return false; // не дозволяємо 0xx
        // перевірка унікальності цифр
        if (s.charAt(0) == s.charAt(1) || s.charAt(0) == s.charAt(2) || s.charAt(1) == s.charAt(2)) return false;
        return true;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean play = true;

        while (play) {
            engine.startNewGame();
            System.out.println("Загадано число (3 різні цифри). Починаємо!");

            while (true) {
                System.out.print("Введіть спробу (наприклад, 371): ");
                String input = sc.nextLine().trim();

                if (!isValidGuess(input)) {
                    System.out.println("Неприпустимий ввід. Введіть 3-значне число з різними цифрами, перша цифра не 0.");
                    continue;
                }

                Result r = engine.makeGuess(input);
                System.out.println(r.toString());

                if (!r.getBullsDetails().isEmpty()) {
                    System.out.println("Бики: ");
                    r.getBullsDetails().forEach(p -> System.out.println(" " + p));
                }

                if (!r.getCowsDetails().isEmpty()) {
                    System.out.println("Корови: ");
                    r.getCowsDetails().forEach(p -> System.out.println(" " + p));
                }

                if (engine.isSolved()) {
                    System.out.println("Вітаю! Ви відгадали число у " + engine.getAttempts() + " спроб(и). ");
                    break;
                }
            }

            // Спросить, хочет ли игрок сыграть ещё
            System.out.print("Хочете зіграти ще раз? (y/n): ");
            String answer = sc.nextLine().trim().toLowerCase();
            if (!answer.equals("y")) {
                play = false;
                System.out.println("Дякуємо за гру!");
            }
        }

        sc.close();
    }
}


