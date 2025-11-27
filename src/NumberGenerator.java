import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class NumberGenerator {
    private final Random rand = new Random();


    // Генерує 3-значне число у вигляді рядка, всі цифри різні, перша != '0'
    public String generate() {
        List<Character> digits = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) digits.add(c);
        Collections.shuffle(digits, rand);


// Переконаємось, що перша цифра не нуль
        char first = '0';
        int idx = 0;
        while (idx < digits.size()) {
            if (digits.get(idx) != '0') { first = digits.get(idx); digits.remove(idx); break; }
            idx++;
        }
        char second = digits.remove(0);
        char third = digits.remove(0);
        return new String(new char[]{first, second, third});
    }
}
