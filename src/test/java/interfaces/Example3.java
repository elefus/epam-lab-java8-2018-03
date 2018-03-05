package interfaces;

@SuppressWarnings("unused")
interface First {

    int value = 100;

    int getValueFromFirst();

//    int getValue();

    default int getValue() {
        return value;
    }
}

@SuppressWarnings("unused")
interface Second {

    int value = -100;

    int getValueFromSecond();

//    int getValue();

    default int getValue() {
        return value;
    }
}


class B implements First, Second {

    @Override
    public int getValueFromFirst() {
        return 0;
    }

    @Override
    public int getValue() {
        return First.super.getValue() + Second.super.getValue();
    }

    @Override
    public int getValueFromSecond() {
        return 0;
    }
}

