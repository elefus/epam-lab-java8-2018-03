package interfaces;

interface GotString {
    String getVal();
}

abstract class Parent1 implements GotString {

    Parent1() {
        System.out.println(getVal().getBytes());
    }

    public int getValue() {
        return -100;
    }

    public abstract String getVal();
}

class Child1 extends Parent1 {

    private String val;

    public Child1() {
        this.val = "123";
    }

    @Override
    public int getValue() {
        return 42;
    }

    public String getVal() {
        return val;
    }
}

class Child2 extends Child1 implements A {

}

public class Example5 {

    public static void main(String[] args) {
        Child2 child1 = new Child2();
        System.out.println(child1.getValue());
    }
}
