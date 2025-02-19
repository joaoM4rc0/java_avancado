package JAVA.KJunit.dominio;

public class Cachorro extends Animal{
    private String tipo;

    public Cachorro(String name, String tipo) {
        super(name);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
