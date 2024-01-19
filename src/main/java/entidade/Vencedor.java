package entidade;

public class Vencedor {

    private String email;
    private double valor;

    public Vencedor(String email, double valor) {
        this.email = email;
        this.valor = valor;
    }

    public String getEmail() {
        return email;
    }

    public double getValor() {
        return valor;
    }
}