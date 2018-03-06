package interfaces.compatibility;

/**
 * - Изменение реализации существующих методов, конструкторов или блоков инициализации
 * - Добавление новых полей, методов и конструкторов существующим классам и интерфейсам
 * - Удаление приватных полей, методов и конструкторов класса
 * - Перемещение методов наверх по иерархии классов
 * - Добавление новых классов и интерфейсов
 * @see <a href="https://habrahabr.ru/post/133907">Хабр: бинарная совместимость в примерах и не только</a>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Checker {

    public void method(A a) {
        System.out.println("Method with A param");
    }

    public void method(B a) {
        System.out.println("Method with B param");
    }

    public static void main(String[] args) {
        Checker checker = new Checker();
        checker.method(new C());
    }
}
