package streams.part2.example.data;

import lambda.data.Person;

public class PersonEmployerPair {

    private Person person;
    private String employer;

    public PersonEmployerPair(Person person, String employer) {
        this.person = person;
        this.employer = employer;
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
}
