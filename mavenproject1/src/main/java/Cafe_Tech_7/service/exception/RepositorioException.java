package Cafe_Tech_7.service.exception;


public class RepositorioException extends Exception {

    public RepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public RepositorioException(String mensagem) {
        super(mensagem);
    }
}
