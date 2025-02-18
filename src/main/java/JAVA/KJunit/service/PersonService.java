package JAVA.KJunit.service;

import JAVA.KJunit.dominio.Person;

import java.util.Objects;

public class PersonService {
    public boolean isAdult(Person person) {
        Objects.requireNonNull(person, "nao pode ser null");
        return person.getAge() >= 18;
    }
}
