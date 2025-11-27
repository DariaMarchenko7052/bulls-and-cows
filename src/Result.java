import java.util.ArrayList;
import java.util.List;


public class Result {
    private int bulls;
    private int cows;
    private final List<Position> bullsDetails = new ArrayList<>();
    private final List<Position> cowsDetails = new ArrayList<>();


    public void addBull(Position p) { bulls++; bullsDetails.add(p); }
    public void addCow(Position p) { cows++; cowsDetails.add(p); }


    public int getBulls() { return bulls; }
    public int getCows() { return cows; }
    public List<Position> getBullsDetails() { return bullsDetails; }
    public List<Position> getCowsDetails() { return cowsDetails; }


    @Override
    public String toString() {
        return String.format("Bulls=%d, Cows=%d", bulls, cows);
    }
}