package JAVA.KJunit.service;

import JAVA.KJunit.dominio.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonService {
    public boolean isAdult(Person person) {
//        Objects.requireNonNull(person, "nao pode ser null");
        if(person == null) {
            throw new IllegalArgumentException("a idade passada tá sendo nula, oq nao é permitido");
        }
        return person.getAge() >= 18;
    }
    public List<Person> retorna_ListaFiltrada_porIdade(List<Person> personList) {
        return personList.stream().filter(this::isAdult).collect(Collectors.toList());
    }
}
