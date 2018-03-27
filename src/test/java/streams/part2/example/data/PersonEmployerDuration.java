package streams.part2.example.data;

import lambda.data.Person;

public class PersonEmployerDuration {

    private final Person person;
    private final String employer;
    private final int duration;

    public PersonEmployerDuration(Person person, String employer, int duration) {
        this.person = person;
        this.employer = employer;
        this.duration = duration;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmployer() {
        return employer;
    }

    public int getDuration() {
        return duration;
    }
}