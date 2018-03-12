package lambda.part1.example;

import org.junit.Test;

@SuppressWarnings("unused")
public class Example7 {

    @FunctionalInterface
    private interface Usable {

        void use();
    }

    private void perform(Runnable runnable) {
        System.out.println("Runnable");
        runnable.run();
    }

    private void perform(Usable usable) {
        System.out.println("Usable");
        usable.use();
    }

    private void doSomething() {
        System.out.println("Не-статический метод doSomething");
    }

    @Test
    public void ambiguousMethodReference() {
        Runnable runnable = () -> System.out.println("Лямбда для Runnable");
        perform(runnable);

        Usable usable = () -> System.out.println("Лямбда для Usable");
        perform(usable);
        
        Runnable method = this::doSomething;
        perform(method);
        
        perform((Usable)this::doSomething);
    }
}
