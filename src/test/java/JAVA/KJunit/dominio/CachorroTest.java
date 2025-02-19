package JAVA.KJunit.dominio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CachorroTest {
    private Animal animal = new Cachorro("pitico", "carnivoro");
    @Test
    void Intanceof_executaMetodoDa_ClasseFilhoQuando_AnimalIsDoTipoFilho () {
//        if(animal instanceof Cachorro) {
//            String tipo = ((Cachorro) animal).getTipo();
//            Assertions.assertEquals("carnivoro", tipo);
//        }
        if (animal instanceof Cachorro cachorro) {
            Assertions.assertEquals("carnivoro", cachorro.getTipo());
        }
    }
}