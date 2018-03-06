package interfaces;

/**
 * 1. Методы по умолчанию предназначены для расширения существующих интерфейсов новыми возможностями.
 *
 * 2. Методы по умолчанию позволяют избежать создания служебных классов.
 *    Все необходимые методы могут быть представлены в самих интерфейсах.
 *
 * 3. Методы по умолчанию дают свободу классам выбрать метод, который нужно переопределить.
 *
 * 4. Метод по умолчанию не может переопределить метод класса java.lang.Object.
 *
 * @see <a href="https://habrahabr.ru/post/133907>Бинарная совместимость</a>
 */
public class Implementation implements First, Second {

    public static void main(String[] args) {
        Implementation implementation = new Implementation();
        System.out.println(implementation.getValueFromFirst());
        System.out.println(implementation.getValueFromSecond());
        System.out.println(implementation.getValue());
    }

    @Override
    public int getValueFromFirst() {
        return 1;
    }

    @Override
    public int getValueFromSecond() {
        return 2;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
