package streams.part2.example.data;

import lambda.data.Person;

public class PersonEmployerDuration {

    private Person person;
    private String employer;
    private int duration;

    public PersonEmployerDuration(Person person, String employer, int duration) {
        this.person = person;
        this.employer = employer;
        this.duration = duration;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
