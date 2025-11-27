//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Создаём генератор чисел и оценщик
        NumberGenerator generator = new NumberGenerator();
        Evaluator evaluator = new Evaluator();

        // Создаём игровой движок
        GameEngine engine = new GameEngine(generator, evaluator);

        // Создаём интерфейс консоли и запускаем игру
        ConsoleUI ui = new ConsoleUI(engine);
        ui.run();
    }
}
