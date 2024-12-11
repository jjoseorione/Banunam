package mx.unam.banunam.auth.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Integer id) {
        super("No se encontró al customer-care-center con id " + id);
    }

    public UsuarioNotFoundException(String usuario) {
        super("No se encontró al customer-care-center " + usuario);
    }
}
