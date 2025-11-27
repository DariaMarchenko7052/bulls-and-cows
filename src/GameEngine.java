import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private String secret;
    private final NumberGenerator generator;
    private final Evaluator evaluator;
    private final List<String> guesses = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();

    public GameEngine(NumberGenerator generator, Evaluator evaluator) {
        this.generator = generator;
        this.evaluator = evaluator;
    }

    public void startNewGame() {
        secret = generator.generate();
        guesses.clear();
        results.clear();
        // Для дебагу, можна раскоментувати:
        // System.out.println("(DEBUG) Secret = " + secret);
    }

    public Result makeGuess(String guess) {
        if (secret == null) throw new IllegalStateException("Game not started");
        Result r = evaluator.evaluate(secret, guess);
        guesses.add(guess);
        results.add(r);
        return r;
    }

    public boolean isSolved() {
        if (results.isEmpty()) return false;
        return results.get(results.size() - 1).getBulls() == 3;
    }

    public int getAttempts() { return guesses.size(); }

    public List<String> getGuesses() { return new ArrayList<>(guesses); }
    public List<Result> getResults() { return new ArrayList<>(results); }
}
