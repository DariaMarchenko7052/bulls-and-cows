public class Evaluator {
    public Result evaluate(String secret, String guess) {
        Result res = new Result();
// bulls
        for (int i = 0; i < 3; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                res.addBull(new Position(guess.charAt(i), i, i));
            }
        }
// cows: для кожної позиції в guess яка не була биком, шукати збіги в секреті на інших позиціях
        for (int i = 0; i < 3; i++) {
            char g = guess.charAt(i);
            boolean isBullHere = (g == secret.charAt(i));
            if (isBullHere) continue;
            for (int j = 0; j < 3; j++) {
                if (j == i) continue; // можна лишити, але логічно
                if (g == secret.charAt(j)) {
                    res.addCow(new Position(g, i, j));
                    break; // важливо: одна цифра в ході не рахується більше одного разу як корова
                }
            }
        }
        return res;
    }
}