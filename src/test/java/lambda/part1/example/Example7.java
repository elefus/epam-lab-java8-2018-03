package lambda.part1.example;

import org.junit.Test;

@SuppressWarnings("unused")
public class Example7 {

    @FunctionalInterface
    private interface Usable {

        void run();
    }

    private void perform(Runnable runnable) {
        System.out.println("Runnable");
        runnable.run();
    }

    private void perform(Usable usable) {
        System.out.println("Usable");
        usable.run();
    }

    private void doSomething() {
        System.out.println("Не-статический метод doSomething");
    }

    @Test
    public void ambiguousMethodReference() {

        Runnable ref = (Runnable & Usable) () -> System.out.println("hello");

        Runnable runnable = () -> System.out.println("Лямбда для Runnable");
        perform(runnable);

        Usable usable = () -> System.out.println("Лямбда для Usable");
        perform(usable);
        
        Runnable method = this::doSomething;
        perform(method);
        
        perform((Usable)this::doSomething);
        perform((Usable)() -> System.out.println("Лямбда для Usable"));
    }
}
