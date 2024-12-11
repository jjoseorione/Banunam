package mx.unam.banunam.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TipoUsuarioNotFoundException extends Exception {
    public TipoUsuarioNotFoundException(Integer id) {
        super("No se encontró al tipo de customer-care-center con id " + id);
    }
}
