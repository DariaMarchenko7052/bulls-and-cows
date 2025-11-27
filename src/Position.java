public class Position {
    public final char digit;
    public final int guessIndex;
    public final int secretIndex;


    public Position(char digit, int guessIndex, int secretIndex) {
        this.digit = digit;
        this.guessIndex = guessIndex;
        this.secretIndex = secretIndex;
    }


    @Override
    public String toString() {
        return String.format("digit=%c (guessPos=%d, secretPos=%d)", digit, guessIndex, secretIndex);
    }
}
