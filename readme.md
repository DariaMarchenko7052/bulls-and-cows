## Гра "Бики та Корови"

## 1. Опис гри

Гра полягає в тому, що комп’ютер загадує тризначне число з різними цифрами, а гравець намагається його відгадати.
Після кожної спроби комп’ютер повідомляє:

* **Бики** — цифри, які вгадано правильно і стоять на правильних місцях.
* **Корови** — цифри, які вгадано правильно, але стоять на неправильних місцях.

Гра продовжується до того моменту, поки гравець не відгадає число.
## 2. Вимоги

* Тризначне число, без повторюваних цифр.
* Перша цифра не може бути нулем.
* Після кожної спроби показуються кількість биків і корів та які саме цифри це складають.
* Можливість почати нову гру після завершення попередньої.

## 3. Пояснення класів

| Клас              | Призначення                                                                  |
| ----------------- | ---------------------------------------------------------------------------- |
| `NumberGenerator` | Генерує тризначне число без повторів.                                        |
| `Evaluator`       | Рахує кількість биків та корів для кожної спроби.                            |
| `Position`        | Зберігає позицію цифри та її значення (для деталізації результату).          |
| `Result`          | Містить результати однієї спроби: кількість биків/корів та їх деталі.        |
| `GameEngine`      | Керує станом гри: секрет, спроби, результати; обробляє введення гравця.      |
| `ConsoleUI`       | Взаємодія з користувачем через консоль; вводить спроби, виводить результати. |
| `Main`            | Точка входу в програму, створює об’єкти та запускає гру.                     |





## 4. UML-діаграма (ASCII)

```plantuml
@startuml
class NumberGenerator {
    +generate(): String
}

class Evaluator {
    +evaluate(secret: String, guess: String): Result
}

class Position {
    -digit: int
    -index: int
}

class Result {
    -bulls: int
    -cows: int
    -bullsDetails: List<Position>
    -cowsDetails: List<Position>
    +getBulls(): int
    +getCows(): int
    +getBullsDetails(): List<Position>
    +getCowsDetails(): List<Position>
}

class GameEngine {
    -secret: String
    -guesses: List<String>
    -results: List<Result>
    +startNewGame()
    +makeGuess(guess: String): Result
    +isSolved(): boolean
    +getAttempts(): int
}

class ConsoleUI {
    -engine: GameEngine
    +run(): void
}

class Main {
    +main(args: String[]): void
}

GameEngine --> Result
ConsoleUI --> GameEngine
Main --> ConsoleUI
Result --> Position
NumberGenerator --> GameEngine
Evaluator --> GameEngine
@enduml


