package JAVA.KJunit.service;

import JAVA.KJunit.dominio.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PersonServiceTest {

    @Test
    @DisplayName("retorna falso sempre que a idade for menor que '0' ")
    void isAdult() {
        Person person = Person.builder().age(15).build();
        PersonService personService = new PersonService();
        Assertions.assertFalse(personService.isAdult(person));
    }
}