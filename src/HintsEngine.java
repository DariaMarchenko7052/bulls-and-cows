import java.util.*;

public class HintsEngine {

    private final GameEngine engine;

    public HintsEngine(GameEngine engine) {
        this.engine = engine;
    }

    public String getHint() {
        int attempts = engine.getAttempts();
        List<String> guesses = engine.getGuesses();
        List<Result> results = engine.getResults();

        if (attempts == 0) return "";

        // Базовая
        if (attempts >= 3) {
            String basic = basicHint(guesses.get(attempts - 1));
            if (basic != null) return "Підказка: " + basic;
        }

        // Аналитическая
        if (attempts >= 5) {
            String analytic = analyticHint(guesses, results);
            if (analytic != null) return "Підказка: " + analytic;
        }

        // Стратегическая
        if (attempts >= 7) {
            String strategy = strategyHint(guesses, results);
            if (strategy != null) return "Підказка: " + strategy;
        }

        return "";
    }

    //  BASIC HINT
    private String basicHint(String lastGuess) {
        if (hasDuplicates(lastGuess)) {
            return "Уникай повторюваних цифр — це зменшує кількість можливих комбінацій.";
        }

        return "Спробуй більше комбінувати різні цифри — не прив'язуйся до одного формату.";
    }

    private boolean hasDuplicates(String s) {
        return s.charAt(0) == s.charAt(1) ||
                s.charAt(0) == s.charAt(2) ||
                s.charAt(1) == s.charAt(2);
    }

    // ANALYTIC HINT
    private String analyticHint(List<String> guesses, List<Result> results) {

        Set<Character> definitelyNot = new HashSet<>();
        Set<Character> potentiallyInNumber = new HashSet<>();

        for (int i = 0; i < guesses.size(); i++) {
            String g = guesses.get(i);
            Result r = results.get(i);

            if (r.getBulls() == 0 && r.getCows() == 0) {
                for (char c : g.toCharArray()) definitelyNot.add(c);
            } else {
                for (char c : g.toCharArray()) {
                    if (!definitelyNot.contains(c)) {
                        potentiallyInNumber.add(c);
                    }
                }
            }

            for (Position p : r.getCowsDetails()) {
                potentiallyInNumber.add(p.digit);
            }
            for (Position p : r.getBullsDetails()) {
                potentiallyInNumber.add(p.digit);
            }
        }

        // 1) Подсказка: цифры, которых точно нет
        if (!definitelyNot.isEmpty()) {
            return "Ці цифри точно НЕ входять до секрету: " + definitelyNot;
        }

        // 2) Подсказка: свойство четности
        int evenCount = 0;
        int oddCount = 0;

        for (char c : potentiallyInNumber) {
            if ((c - '0') % 2 == 0) evenCount++; else oddCount++;
        }

        if (evenCount == 2) return "Схоже, що в секретному числі два парні числа.";
        if (evenCount == 3) return "Схоже, що всі три цифри парні.";
        if (oddCount == 3) return "Схоже, що всі цифри непарні.";

        // 3) Подсказка: аналіз діапазонів (цифры меньше 5, больше 5)
        boolean allLessThan5 = true;
        for (char c : potentiallyInNumber)
            if ((c - '0') >= 5) allLessThan5 = false;

        if (allLessThan5) return "Схоже, що всі цифри в загаданому числі < 5.";

        boolean hasBig = false;
        for (char c : potentiallyInNumber)
            if ((c - '0') >= 7) hasBig = true;

        if (hasBig) return "Є принаймні одна велика цифра (7 або більше).";

        return null;
    }

    //  STRATEGIC HINT
    private String strategyHint(List<String> guesses, List<Result> results) {
        int zeroInfo = 0;
        for (Result r : results) {
            if (r.getBulls() == 0 && r.getCows() == 0) zeroInfo++;
        }

        if (zeroInfo >= 2) {
            return "Ти виключив багато цифр — введи число з новими цифрами, яких ще не пробував.";
        }

        for (Result r : results) {
            if (!r.getCowsDetails().isEmpty()) {
                return "Деякі цифри є в числі, але не на своїх місцях — спробуй переставити їх.";
            }
        }

        return "Використай стратегію: вводь числа з максимально різними цифрами для збору інформації.";
    }
}
