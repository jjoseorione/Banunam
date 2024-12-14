package mx.unam.banunam.system.exception;

public class ClienteAlreadyExistsException extends RuntimeException{
    public ClienteAlreadyExistsException(String clave, String valor){
        super("Ya existe un usuario con " + clave + " " + valor);
    }
}
