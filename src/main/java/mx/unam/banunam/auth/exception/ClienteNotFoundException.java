package mx.unam.banunam.auth.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Integer id) {
        super("No se encontró al clientes con id " + id);
    }

    public ClienteNotFoundException(String tdd) {
        super("No se encontró la tarjeta " + tdd);
    }
}
