package streams.part2.example.data;

import lambda.data.Employee;
import lambda.data.Person;

public class PersonEmployerDuration {
    private final Person person;
    private final int duration;
    private String employee;

    public PersonEmployerDuration(Person person, int duration, String employee) {
        this.person = person;
        this.duration = duration;
        this.employee = employee;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmployee() {
        return employee;
    }

    public int getDuration() { return duration; }
}
