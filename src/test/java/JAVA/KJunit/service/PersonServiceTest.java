package JAVA.KJunit.service;

import JAVA.KJunit.dominio.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PersonServiceTest {
    private Person adult;
    private Person notAdult;
    private PersonService personService;
    @BeforeEach
    public void setUp() {
        adult = new Person(18);
        notAdult = new Person(15);
        personService = new PersonService();
    }
    @Test
    @DisplayName("a pessoa é menor de idade, pois é tem a idade menor que 18 ")
    void isAdult_retornaFalse_seForMenorQue18() {
        Assertions.assertFalse(personService.isAdult(notAdult));
    }
    @Test
    @DisplayName("a pessoa é maior de idade, pois tem 18 anos ou mais ")
    void isAdult_retornatrue_seFormaiorQue18() {
        Assertions.assertTrue(personService.isAdult(adult));
    }
    @Test
    @DisplayName("retorna uma lista de pessoas adultas")
    void retorna_ListaDePessoas_comIdade_maior_que_18() {
        Person person1 = new Person(17);
        Person person2 = new Person(18);
        Person person3 = new Person(22);
        Person person4 = new Person(19);
        List<Person> personList = List.of(person1, person2, person3, person4);
        Assertions.assertEquals(3, personService.retorna_ListaFiltrada_porIdade(personList).size());
    }
    @Test
    @DisplayName("idade tá sendo passada como nula, e vai lançar um nullpointerException")
    void retorna_nullPointerException_se_idade_for_nula() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> personService.isAdult(null));
    }
}