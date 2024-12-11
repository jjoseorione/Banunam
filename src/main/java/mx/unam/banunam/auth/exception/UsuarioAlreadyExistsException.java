package mx.unam.banunam.auth.exception;

public class UsuarioAlreadyExistsException extends RuntimeException{
    public UsuarioAlreadyExistsException(String campo, String valor){
        super("Ya existe el customer-care-center con " + campo + " = " + valor);
    }
}